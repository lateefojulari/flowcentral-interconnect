/*
 * Copyright (c) 2019, 2021, FlowCentral Technologies.
 * All rights reserved.
 * 
 * PROPRIETARY AND CONFIDENTIAL. USE IS SUBJECT TO LICENSE TERMS.
 */

package com.flowcentraltech.flowcentral.interconnect.common.data;

import java.util.Arrays;

import com.flowcentraltech.flowcentral.interconnect.common.constants.DataSourceOperation;

/**
 * Data source request.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public class DataSourceRequest {

    private DataSourceOperation operation;
    
    private String entity;
    
    private Long id;
    
    private String fieldName;
    
    private String update;
    
    private String query;
    
    private String order;
    
    private Long versionNo;
    
    private String[] payload;
    
    private boolean ignoreEmptyCriteria;
    
    private int offset;
    
    private int limit;
    
    public DataSourceRequest(DataSourceOperation operation, Long id, Long versionNo) {
        this.operation = operation;
        this.id = id;
        this.versionNo = versionNo;
    }

    public DataSourceRequest(DataSourceOperation operation) {
        this.operation = operation;
    }

    public DataSourceRequest() {
        
    }
    
    public DataSourceOperation getOperation() {
        return operation;
    }

    public void setOperation(DataSourceOperation operation) {
        this.operation = operation;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Long getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    public boolean isIgnoreEmptyCriteria() {
        return ignoreEmptyCriteria;
    }

    public void setIgnoreEmptyCriteria(boolean ignoreEmptyCriteria) {
        this.ignoreEmptyCriteria = ignoreEmptyCriteria;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String[] getPayload() {
        return payload;
    }

    public void setPayload(String[] payload) {
        this.payload = payload;
    }
    
    public boolean clause() {
        return id != null || versionNo != null || query != null;
    }
    
    public boolean byIdOnly() {
        return id != null && versionNo == null;
    }
    
    public boolean byIdVersion() {
        return id != null && versionNo != null;
    }
    
    public boolean byQuery() {
        return query != null;
    }
    
    public boolean version() {
        return versionNo != null;
    }
    
    public boolean value() {
        return fieldName != null;
    }
    
    public boolean count() {
        return DataSourceOperation.COUNT_ALL.equals(operation);
    }
    
    public boolean delete() {
        return DataSourceOperation.DELETE_ALL.equals(operation);
    }

    @Override
    public String toString() {
        return "DataSourceRequest [operation=" + operation + ", entity=" + entity + ", id=" + id + ", fieldName="
                + fieldName + ", update=" + update + ", query=" + query + ", versionNo=" + versionNo + ", payload="
                + Arrays.toString(payload) + ", ignoreEmptyCriteria=" + ignoreEmptyCriteria + ", offset=" + offset
                + ", limit=" + limit + "]";
    }
}
