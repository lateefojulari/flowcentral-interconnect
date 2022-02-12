/*
 * Copyright (c) 2019, 2021, FlowCentral Technologies.
 * All rights reserved.
 * 
 * PROPRIETARY AND CONFIDENTIAL. USE IS SUBJECT TO LICENSE TERMS.
 */
package com.flowcentraltech.flowcentral.interconnect.common.data;

import com.flowcentraltech.flowcentral.interconnect.common.constants.RestrictionType;

/**
 * Filter restriction definition.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public class FilterRestrictionDef {

    private RestrictionType type;

    private String fieldName;

    private String paramA;

    private String paramB;

    private int depth;

    public FilterRestrictionDef(RestrictionType type, String fieldName, String paramA, String paramB, int depth) {
        this.type = type;
        this.fieldName = fieldName;
        this.paramA = paramA;
        this.paramB = paramB;
        this.depth = depth;
    }

    public FilterRestrictionDef(RestrictionType type, int depth) {
        this.type = type;
        this.depth = depth;
    }

    public RestrictionType getType() {
        return type;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getParamA() {
        return paramA;
    }

    public String getParamB() {
        return paramB;
    }

    public int getDepth() {
        return depth;
    }

    public boolean isCompound() {
        return type.isCompound();
    }

}
