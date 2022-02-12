/*
 * Copyright (c) 2019, 2021, FlowCentral Technologies.
 * All rights reserved.
 * 
 * PROPRIETARY AND CONFIDENTIAL. USE IS SUBJECT TO LICENSE TERMS.
 */
package com.flowcentraltech.flowcentral.interconnect.common.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.flowcentraltech.flowcentral.interconnect.common.constants.RestrictionType;

/**
 * Query definition.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public class QueryDef {

    private List<FilterRestrictionDef> filterRestrictionDefList;

    public QueryDef(List<FilterRestrictionDef> filterRestrictionDefList) {
        this.filterRestrictionDefList = filterRestrictionDefList;
    }

    public FilterRestrictionDef getFilterRestrictionDef(int index) {
        return filterRestrictionDefList.get(index);
    }

    public boolean isBlank() {
        return filterRestrictionDefList == null || filterRestrictionDefList.isEmpty();
    }

    public int size() {
        return filterRestrictionDefList.size();
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private List<FilterRestrictionDef> filterRestrictionDefList;

        public Builder() {
            this.filterRestrictionDefList = new ArrayList<FilterRestrictionDef>();
        }
        
        public Builder addRestrictionDef(RestrictionType type, String fieldName, String paramA, String paramB,
                int depth) {
            filterRestrictionDefList.add(new FilterRestrictionDef(type, fieldName, paramA, paramB, depth));
            return this;
        }

        public QueryDef build() {
            return new QueryDef(Collections.unmodifiableList(filterRestrictionDefList));
        }
    }

}
