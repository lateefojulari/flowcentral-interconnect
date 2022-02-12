/*
 * Copyright (c) 2019, 2021, FlowCentral Technologies.
 * All rights reserved.
 * 
 * PROPRIETARY AND CONFIDENTIAL. USE IS SUBJECT TO LICENSE TERMS.
 */

package com.flowcentraltech.flowcentral.interconnect.common.constants;

/**
 * Data source operation.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public enum DataSourceOperation {
    CREATE(false),
    FIND(true),
    FIND_LEAN(true),
    FIND_ALL(true),
    LIST(true),
    LIST_LEAN(true),
    LIST_ALL(true),
    UPDATE(false),
    UPDATE_LEAN(false),
    UPDATE_ALL(false),
    DELETE(false),
    DELETE_ALL(false),
    COUNT_ALL(false),
    VALUE(false),
    VALUE_LIST(false);

    private final boolean entityResult;
    
    private DataSourceOperation(boolean entityResult) {
        this.entityResult = entityResult;
    }
    
    public boolean isFind() {
        return this.equals(FIND) || this.equals(FIND_LEAN) || this.equals(FIND_ALL);
    }

    public boolean isList() {
        return this.equals(LIST) || this.equals(LIST_LEAN) || this.equals(LIST_ALL);
    }

    public boolean isLean() {
        return this.equals(FIND_LEAN) || this.equals(LIST_LEAN) || this.equals(FIND_ALL) || this.equals(LIST_ALL);
    }

    public boolean isMultipleResult() {
        return this.equals(FIND_ALL) || this.equals(LIST_ALL) || this.equals(VALUE_LIST);
    }

    public boolean isSingleResult() {
        return this.equals(FIND) || this.equals(FIND_LEAN) || this.equals(LIST) || this.equals(LIST_LEAN);
    }
    
    public boolean entityResult() {
        return entityResult;
    }
    
    public String toJson() {
       return this.name();
    }
    
    public static DataSourceOperation of(String val) {
        return val != null ? DataSourceOperation.of(val): null;
    }
}
