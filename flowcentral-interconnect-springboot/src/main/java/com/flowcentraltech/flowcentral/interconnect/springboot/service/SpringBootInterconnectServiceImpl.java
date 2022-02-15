/*
 * Copyright 2021-2022 FlowCentral Technologies Limited.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.flowcentraltech.flowcentral.interconnect.springboot.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowcentraltech.flowcentral.interconnect.common.EntityInstFinder;
import com.flowcentraltech.flowcentral.interconnect.common.constants.RestrictionType;
import com.flowcentraltech.flowcentral.interconnect.common.data.DataSourceRequest;
import com.flowcentraltech.flowcentral.interconnect.common.data.DataSourceResponse;
import com.flowcentraltech.flowcentral.interconnect.common.data.EntityFieldInfo;
import com.flowcentraltech.flowcentral.interconnect.common.data.EntityInfo;
import com.flowcentraltech.flowcentral.interconnect.common.data.FilterRestrictionDef;
import com.flowcentraltech.flowcentral.interconnect.common.data.OrderDef;
import com.flowcentraltech.flowcentral.interconnect.common.data.QueryDef;
import com.flowcentraltech.flowcentral.interconnect.common.data.ResolvedCondition;
import com.flowcentraltech.flowcentral.interconnect.configuration.constants.FieldDataType;
import com.flowcentraltech.flowcentral.interconnect.springboot.SpringBootInterconnect;
import com.tcdng.unify.convert.util.ConverterUtils;

/**
 * Implementation of flow central spring boot interconnect service.
 * 
 * @author FlowCentral Technologies Limited
 * @since 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SpringBootInterconnectServiceImpl implements SpringBootInterconnectService, EntityInstFinder {

    private final SpringBootInterconnect interconnect;

    private final EntityManager em;

    private final Environment env;

    @Autowired
    public SpringBootInterconnectServiceImpl(SpringBootInterconnect interconnect, EntityManager em, Environment env) {
        this.interconnect = interconnect;
        this.em = em;
        this.env = env;
    }

    @PostConstruct
    public void init() throws Exception {
        String interconectConfigFile = env.getProperty("flowcentral.interconnect.configfile");
        interconnect.init(interconectConfigFile, this);
    }

    @Override
    @Transactional
    public <T> T findById(Class<T> entityClass, Object id) throws Exception {
        return em.find(entityClass, id);
    }

    @Override
    @Transactional
    public DataSourceResponse processDataSourceRequest(DataSourceRequest req) throws Exception {
        EntityInfo entityInfo = interconnect.getEntityInfo(req.getEntity());
        Object[] result = null;
        //System.out.println("@Prime: req = " + req);
        switch (req.getOperation()) {
            case COUNT_ALL: {
                CriteriaQuery<Long> cq = createLongQuery(entityInfo.getImplClass(), req);
                Long count = em.createQuery(cq).getSingleResult();
                result = new Object[] { count };
            }
                break;
            case CREATE: {
                Object reqBean = interconnect.getBeanFromJsonPayload(req);
                em.persist(reqBean);
                em.flush();
                Object id = PropertyUtils.getProperty(reqBean, entityInfo.getIdFieldName());
                result = new Object[] { id };
            }
                break;
            case DELETE: {
                Object reqBean = interconnect.getBeanFromJsonPayload(req);
                if (req.version()) {
                    PropertyUtils.setProperty(reqBean, entityInfo.getVersionNoFieldName(), req.getVersionNo());
                }

                em.remove(reqBean);
            }
                break;
            case DELETE_ALL: {
                CriteriaDelete<?> cd = createDeleteQuery(entityInfo.getImplClass(), req);
                int count = em.createQuery(cd).executeUpdate();
                result = new Object[] { count };
            }
                break;
            case FIND:
            case FIND_ALL:
            case FIND_LEAN:
            case LIST:
            case LIST_ALL:
            case LIST_LEAN: {
                CriteriaQuery<?> cq = createQuery(entityInfo.getImplClass(), req);
                TypedQuery<?> query = em.createQuery(cq);
                List<?> results = query.getResultList();
                if (!req.getOperation().isMultipleResult()) {
                    if (results.size() > 1) {
                        throw new RuntimeException(
                                "Mutiple records found on single item operation on entity [" + req.getEntity() + "].");
                    }
                }

                result = results.toArray(new Object[results.size()]);
            }
                break;
            case UPDATE:
            case UPDATE_LEAN: {
                Object reqBean = interconnect.getBeanFromJsonPayload(req);
                Object id = PropertyUtils.getProperty(reqBean, entityInfo.getIdFieldName());
                Object saveBean = em.find(entityInfo.getImplClass(), id);
                Object versionNo = req.version()
                        ? PropertyUtils.getProperty(saveBean, entityInfo.getVersionNoFieldName())
                        : null;
                // References
                interconnect.copy(entityInfo.getRefFieldList(), reqBean, saveBean);
                // Fields
                interconnect.copy(entityInfo.getFieldList(), reqBean, saveBean);

                if (!req.getOperation().isLean()) {
                    // Child
                    interconnect.copy(entityInfo.getChildFieldList(), reqBean, saveBean);
                    // Child list
                    interconnect.copy(entityInfo.getChildListFieldList(), reqBean, saveBean);
                }

                if (req.version()) {
                    PropertyUtils.setProperty(saveBean, entityInfo.getVersionNoFieldName(), versionNo);
                }

                em.merge(saveBean);
                result = new Object[] { 1L };
            }
                break;
            case UPDATE_ALL:
                break;
            case VALUE:
            case VALUE_LIST: {
                CriteriaQuery<Tuple> cq = createTupleQuery(entityInfo.getImplClass(), req);
                List<Tuple> tupleResult = em.createQuery(cq).getResultList();
                if (!req.getOperation().isMultipleResult()) {
                    if (tupleResult.size() > 1) {
                        throw new RuntimeException(
                                "Mutiple records found on single item operation on entity [" + req.getEntity() + "].");
                    }
                }

                result = new Object[tupleResult.size()];
                for (int i = 0; i < result.length; i++) {
                    result[i] = tupleResult.get(i).get(0);
                }
            }
                break;
            default:
                break;

        }

        return interconnect.createDataSourceResponse(result, req);
    }

    private <T> CriteriaQuery<T> createQuery(Class<T> entityClass, DataSourceRequest req) throws Exception {
        EntityInfo entityInfo = interconnect.getEntityInfo(req.getEntity());
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        cq.select(root);
        Predicate restrictions = createRestriction(cb, root, entityInfo, req);
        if (restrictions != null) {
            cq.where(restrictions);
        }

        List<OrderDef> orderDefList = interconnect.getOrderDef(req.getOrder());
        if (!orderDefList.isEmpty()) {
            List<Order> orderList = new ArrayList<Order>();
            for (OrderDef orderDef : orderDefList) {
                if (orderDef.isAscending()) {
                    orderList.add(cb.asc(root.get(orderDef.getFieldName())));
                } else {
                    orderList.add(cb.desc(root.get(orderDef.getFieldName())));
                }
            }
            
            cq.orderBy(orderList);
        }

        return cq;
    }

    private <T> CriteriaQuery<Tuple> createTupleQuery(Class<T> entityClass, DataSourceRequest req) throws Exception {
        EntityInfo entityInfo = interconnect.getEntityInfo(req.getEntity());
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<T> root = cq.from(entityClass);
        cq.multiselect(root.get(req.getFieldName()));
        Predicate restrictions = createRestriction(cb, root, entityInfo, req);
        if (restrictions != null) {
            cq.where(restrictions);
        }

        return cq;
    }

    private <T> CriteriaQuery<Long> createLongQuery(Class<T> entityClass, DataSourceRequest req) throws Exception {
        EntityInfo entityInfo = interconnect.getEntityInfo(req.getEntity());
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> root = cq.from(entityClass);
        cq.select(cb.count(root));
        Predicate restrictions = createRestriction(cb, root, entityInfo, req);
        if (restrictions != null) {
            cq.where(restrictions);
        }

        return cq;
    }

    private <T> CriteriaDelete<T> createDeleteQuery(Class<T> entityClass, DataSourceRequest req) throws Exception {
        EntityInfo entityInfo = interconnect.getEntityInfo(req.getEntity());
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<T> cq = cb.createCriteriaDelete(entityClass);
        Root<T> root = cq.from(entityClass);
        Predicate restrictions = createRestriction(cb, root, entityInfo, req);
        if (restrictions != null) {
            cq.where(restrictions);
        }

        return cq;
    }

    private Predicate createRestriction(CriteriaBuilder cb, Root<?> root, EntityInfo entityInfo, DataSourceRequest req)
            throws Exception {
        Predicate predicate = null;
        if (req.byIdOnly()) {
            predicate = cb.equal(root.get(entityInfo.getIdFieldName()), req.getId());
        } else if (req.byIdVersion()) {
            predicate = cb.and(cb.equal(root.get(entityInfo.getIdFieldName()), req.getId()),
                    cb.equal(root.get(entityInfo.getVersionNoFieldName()), req.getVersionNo()));
        } else if (req.byQuery()) {
            QueryDef queryDef = interconnect.getQueryDef(req.getQuery());
            final int len = queryDef.size();
            if (len > 0) {
                FilterRestrictionDef fo = queryDef.getFilterRestrictionDef(0);
                if (len == 1 && !fo.getType().isCompound()) {
                    predicate = getSimplePredicate(cb, entityInfo, fo, new Index(root), getNow());
                } else {
                    predicate = addCompoundPredicate(cb, entityInfo, queryDef, fo, new Index(root).set(1), getNow());
                }
            }
        }

        if (predicate == null && !req.isIgnoreEmptyCriteria()) {
            throw new RuntimeException("Operation on entity [" + req.getEntity() + "] must have a clause.");
        }

        return predicate;
    }

    private Date getNow() {
        // TODO
        return new Date();
    }

    private Predicate addCompoundPredicate(CriteriaBuilder cb, EntityInfo entityInfo, QueryDef queryDef,
            FilterRestrictionDef fo, Index index, Date now) throws Exception {
        List<Predicate> predicateList = new ArrayList<Predicate>();
        final int len = queryDef.size();
        final int depth = fo.getDepth();
        int i = index.get();
        for (; i < len; i++) {
            FilterRestrictionDef sfo = queryDef.getFilterRestrictionDef(i);
            if (sfo.getDepth() > depth) {
                Predicate predicate = null;
                if (sfo.getType().isCompound()) {
                    predicate = addCompoundPredicate(cb, entityInfo, queryDef, sfo, index.set(i + 1), now);
                    i = index.get() - 1;
                } else {
                    predicate = getSimplePredicate(cb, entityInfo, sfo, index, now);
                }

                predicateList.add(predicate);
            } else {
                break;
            }
        }

        index.set(i);

        Predicate[] restrictions = predicateList.toArray(new Predicate[predicateList.size()]);
        return RestrictionType.AND.equals(fo.getType()) ? cb.and(restrictions) : cb.or(restrictions);
    }

    @SuppressWarnings("unchecked")
    private Predicate getSimplePredicate(CriteriaBuilder cb, EntityInfo entityInfo, FilterRestrictionDef fo,
            Index index, Date now) throws Exception {
        EntityFieldInfo _entityFieldInfo = entityInfo.getEntityFieldInfo(fo.getFieldName());
        RestrictionType type = fo.getType();
        Object paramA = interconnect.resolveSpecialParameter(fo.getParamA());
        Object paramB = interconnect.resolveSpecialParameter(fo.getParamB());
        if (!type.isFieldVal() && !type.isParameterVal()) {
            if (type.isLingual() && _entityFieldInfo.isString()) {
                ResolvedCondition resolved = interconnect.resolveLingualStringCondition(_entityFieldInfo, now, type,
                        paramA, paramB);
                type = resolved.getType();
                paramA = resolved.getParamA();
                paramB = resolved.getParamB();
            } else if (_entityFieldInfo.isDate() || _entityFieldInfo.isTimestamp()) {
                ResolvedCondition resolved = interconnect.resolveDateCondition(_entityFieldInfo, now, type, paramA,
                        paramB);
                type = resolved.getType();
                paramA = resolved.getParamA();
                paramB = resolved.getParamB();
            } else {
                FieldDataType fieldDataType = _entityFieldInfo.getType();
                Class<?> javaClass = fieldDataType.javaClass();
                if (paramA != null) {
                    if (type.isAmongst()) {
                        paramA = ConverterUtils.convert(List.class, javaClass,
                                Arrays.asList(interconnect.breakdownCollectionString((String) paramA)));
                    } else {
                        paramA = ConverterUtils.convert(javaClass, paramA);
                    }
                }

                if (paramB != null) {
                    paramB = ConverterUtils.convert(javaClass, paramB);
                }
            }
        }

        if (_entityFieldInfo.isEnum()) {
            paramA = ConverterUtils.convert(_entityFieldInfo.getEnumImplClass(), paramA);
            paramB = ConverterUtils.convert(_entityFieldInfo.getEnumImplClass(), paramB);
        }
        
        switch (type) {
            case AMONGST:
                return index.getRoot().get(fo.getFieldName()).in((Collection<?>) paramA);
            case AND:
                break;
            case BEGINS_WITH:
                return cb.like(index.getRoot().get(fo.getFieldName()), paramA + "%");
            case BEGINS_WITH_FIELD:
                break;
            case BEGINS_WITH_PARAM:
                break;
            case BETWEEN:
                if (_entityFieldInfo.isDate() || _entityFieldInfo.isTimestamp()) {
                    return cb.between(index.getRoot().get(fo.getFieldName()), (Date) paramA, (Date) paramB);
                }

                if (_entityFieldInfo.isInteger()) {
                    return cb.between(index.getRoot().get(fo.getFieldName()),
                            ConverterUtils.convert(Long.class, paramA), ConverterUtils.convert(Long.class, paramB));
                }

                if (_entityFieldInfo.isDouble()) {
                    return cb.between(index.getRoot().get(fo.getFieldName()),
                            ConverterUtils.convert(Double.class, paramA), ConverterUtils.convert(Double.class, paramB));
                }

                if (_entityFieldInfo.isDecimal()) {
                    return cb.between(index.getRoot().get(fo.getFieldName()), (BigDecimal) paramA, (BigDecimal) paramB);
                }
                break;
            case BETWEEN_FIELD:
                break;
            case BETWEEN_PARAM:
                break;
            case ENDS_WITH:
                return cb.like(index.getRoot().get(fo.getFieldName()), "%" + paramA);
            case ENDS_WITH_FIELD:
                break;
            case ENDS_WITH_PARAM:
                break;
            case EQUALS:
                return cb.equal(index.getRoot().get(fo.getFieldName()), paramA);
            case EQUALS_COLLECTION:
                break;
            case EQUALS_FIELD:
                break;
            case EQUALS_LINGUAL:
                return cb.equal(index.getRoot().get(fo.getFieldName()), paramA);
            case EQUALS_PARAM:
                break;
            case GREATER_OR_EQUAL:
                if (_entityFieldInfo.isDate() || _entityFieldInfo.isTimestamp()) {
                    return cb.greaterThanOrEqualTo(index.getRoot().get(fo.getFieldName()), (Date) paramA);
                }

                if (_entityFieldInfo.isInteger()) {
                    return cb.greaterThanOrEqualTo(index.getRoot().get(fo.getFieldName()),
                            ConverterUtils.convert(Long.class, paramA));
                }

                if (_entityFieldInfo.isDouble()) {
                    return cb.greaterThanOrEqualTo(index.getRoot().get(fo.getFieldName()),
                            ConverterUtils.convert(Double.class, paramA));
                }

                if (_entityFieldInfo.isDecimal()) {
                    return cb.greaterThanOrEqualTo(index.getRoot().get(fo.getFieldName()), (BigDecimal) paramA);
                }

                break;
            case GREATER_OR_EQUAL_COLLECTION:
                break;
            case GREATER_OR_EQUAL_FIELD:
                break;
            case GREATER_OR_EQUAL_LINGUAL:
                break;
            case GREATER_OR_EQUAL_PARAM:
                break;
            case GREATER_THAN:
                if (_entityFieldInfo.isDate() || _entityFieldInfo.isTimestamp()) {
                    return cb.greaterThan(index.getRoot().get(fo.getFieldName()), (Date) paramA);
                }

                if (_entityFieldInfo.isInteger()) {
                    return cb.greaterThan(index.getRoot().get(fo.getFieldName()),
                            ConverterUtils.convert(Long.class, paramA));
                }

                if (_entityFieldInfo.isDouble()) {
                    return cb.greaterThan(index.getRoot().get(fo.getFieldName()),
                            ConverterUtils.convert(Double.class, paramA));
                }

                if (_entityFieldInfo.isDecimal()) {
                    return cb.greaterThan(index.getRoot().get(fo.getFieldName()), (BigDecimal) paramA);
                }

                break;
            case GREATER_THAN_COLLECTION:
                break;
            case GREATER_THAN_FIELD:
                break;
            case GREATER_THAN_LINGUAL:
                break;
            case GREATER_THAN_PARAM:
                break;
            case IBEGINS_WITH:
                paramA = ((String) paramA).toLowerCase();
                return cb.like(cb.lower(index.getRoot().get(fo.getFieldName())), paramA + "%");
            case IBEGINS_WITH_FIELD:
                break;
            case IBEGINS_WITH_PARAM:
                break;
            case IENDS_WITH:
                paramA = ((String) paramA).toLowerCase();
                return cb.like(cb.lower(index.getRoot().get(fo.getFieldName())), "%" + paramA);
            case IENDS_WITH_FIELD:
                break;
            case IENDS_WITH_PARAM:
                break;
            case IEQUALS:
                paramA = ((String) paramA).toLowerCase();
                return cb.equal(cb.lower(index.getRoot().get(fo.getFieldName())), paramA);
            case ILIKE:
                paramA = ((String) paramA).toLowerCase();
                return cb.like(cb.lower(index.getRoot().get(fo.getFieldName())), "%" + paramA + "%");
            case ILIKE_FIELD:
                break;
            case ILIKE_PARAM:
                break;
            case INOT_EQUALS:
                paramA = ((String) paramA).toLowerCase();
                return cb.equal(cb.lower(index.getRoot().get(fo.getFieldName())), paramA).not();
            case IS_NOT_NULL:
                return cb.isNotNull(index.getRoot().get(fo.getFieldName()));
            case IS_NULL:
                return cb.isNull(index.getRoot().get(fo.getFieldName()));
            case LESS_OR_EQUAL:
                if (_entityFieldInfo.isDate() || _entityFieldInfo.isTimestamp()) {
                    return cb.lessThanOrEqualTo(index.getRoot().get(fo.getFieldName()), (Date) paramA);
                }

                if (_entityFieldInfo.isInteger()) {
                    return cb.lessThanOrEqualTo(index.getRoot().get(fo.getFieldName()),
                            ConverterUtils.convert(Long.class, paramA));
                }

                if (_entityFieldInfo.isDouble()) {
                    return cb.lessThanOrEqualTo(index.getRoot().get(fo.getFieldName()),
                            ConverterUtils.convert(Double.class, paramA));
                }

                if (_entityFieldInfo.isDecimal()) {
                    return cb.lessThanOrEqualTo(index.getRoot().get(fo.getFieldName()), (BigDecimal) paramA);
                }

                break;
            case LESS_OR_EQUAL_COLLECTION:
                break;
            case LESS_OR_EQUAL_FIELD:
                break;
            case LESS_OR_EQUAL_LINGUAL:
                break;
            case LESS_OR_EQUAL_PARAM:
                break;
            case LESS_THAN:
                if (_entityFieldInfo.isDate() || _entityFieldInfo.isTimestamp()) {
                    return cb.lessThan(index.getRoot().get(fo.getFieldName()), (Date) paramA);
                }

                if (_entityFieldInfo.isInteger()) {
                    return cb.lessThan(index.getRoot().get(fo.getFieldName()),
                            ConverterUtils.convert(Long.class, paramA));
                }

                if (_entityFieldInfo.isDouble()) {
                    return cb.lessThan(index.getRoot().get(fo.getFieldName()),
                            ConverterUtils.convert(Double.class, paramA));
                }

                if (_entityFieldInfo.isDecimal()) {
                    return cb.lessThan(index.getRoot().get(fo.getFieldName()), (BigDecimal) paramA);
                }

                break;
            case LESS_THAN_COLLECTION:
                break;
            case LESS_THAN_FIELD:
                break;
            case LESS_THAN_LINGUAL:
                break;
            case LESS_THAN_PARAM:
                break;
            case LIKE:
                return cb.like(index.getRoot().get(fo.getFieldName()), "%" + paramA + "%");
            case LIKE_FIELD:
                break;
            case LIKE_PARAM:
                break;
            case NOT_AMONGST:
                return index.getRoot().get(fo.getFieldName()).in((Collection<?>) paramA).not();
            case NOT_BEGIN_WITH:
                return cb.like(index.getRoot().get(fo.getFieldName()), paramA + "%").not();
            case NOT_BEGIN_WITH_FIELD:
                break;
            case NOT_BEGIN_WITH_PARAM:
                break;
            case NOT_BETWEEN:
                if (_entityFieldInfo.isDate() || _entityFieldInfo.isTimestamp()) {
                    return cb.between(index.getRoot().get(fo.getFieldName()), (Date) paramA, (Date) paramB).not();
                }

                if (_entityFieldInfo.isInteger()) {
                    return cb.between(index.getRoot().get(fo.getFieldName()),
                            ConverterUtils.convert(Long.class, paramA), ConverterUtils.convert(Long.class, paramB))
                            .not();
                }

                if (_entityFieldInfo.isDouble()) {
                    return cb.between(index.getRoot().get(fo.getFieldName()),
                            ConverterUtils.convert(Double.class, paramA), ConverterUtils.convert(Double.class, paramB))
                            .not();
                }

                if (_entityFieldInfo.isDecimal()) {
                    return cb.between(index.getRoot().get(fo.getFieldName()), (BigDecimal) paramA, (BigDecimal) paramB)
                            .not();
                }
                break;
            case NOT_BETWEEN_FIELD:
                break;
            case NOT_BETWEEN_PARAM:
                break;
            case NOT_END_WITH:
                return cb.like(index.getRoot().get(fo.getFieldName()), "%" + paramA).not();
            case NOT_END_WITH_FIELD:
                break;
            case NOT_END_WITH_PARAM:
                break;
            case NOT_EQUALS:
                return cb.equal(index.getRoot().get(fo.getFieldName()), paramA).not();
            case NOT_EQUALS_COLLECTION:
                break;
            case NOT_EQUALS_FIELD:
                break;
            case NOT_EQUALS_LINGUAL:
                break;
            case NOT_EQUALS_PARAM:
                break;
            case NOT_LIKE:
                return cb.like(index.getRoot().get(fo.getFieldName()), "%" + paramA + "%").not();
            case NOT_LIKE_FIELD:
                break;
            case NOT_LIKE_PARAM:
                break;
            case OR:
                break;
            default:
                break;

        }

        return null;
    }

    private class Index {

        private final Root<?> root;

        private int i;

        public Index(Root<?> root) {
            this.root = root;
        }

        public Root<?> getRoot() {
            return root;
        }

        public int get() {
            return i;
        }

        public Index set(int i) {
            this.i = i;
            return this;
        }
    }
}
