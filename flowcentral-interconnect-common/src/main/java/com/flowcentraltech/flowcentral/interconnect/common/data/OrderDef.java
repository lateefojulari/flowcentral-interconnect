/*
 * Copyright (c) 2019, 2021, FlowCentral Technologies.
 * All rights reserved.
 * 
 * PROPRIETARY AND CONFIDENTIAL. USE IS SUBJECT TO LICENSE TERMS.
 */
package com.flowcentraltech.flowcentral.interconnect.common.data;

/**
 * Order definition.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public class OrderDef {

    private String fieldName;
    
    private boolean ascending;

    public OrderDef(String fieldName, boolean ascending) {
        this.fieldName = fieldName;
        this.ascending = ascending;
    }

    public String getFieldName() {
        return fieldName;
    }

    public boolean isAscending() {
        return ascending;
    }

}
