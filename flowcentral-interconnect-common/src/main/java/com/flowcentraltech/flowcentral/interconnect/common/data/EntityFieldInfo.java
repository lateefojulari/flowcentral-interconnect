/*
 * Copyright (c) 2019, 2021, FlowCentral Technologies.
 * All rights reserved.
 * 
 * PROPRIETARY AND CONFIDENTIAL. USE IS SUBJECT TO LICENSE TERMS.
 */

package com.flowcentraltech.flowcentral.interconnect.common.data;

import com.flowcentraltech.flowcentral.interconnect.configuration.constants.FieldDataType;

/**
 * Field information.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public class EntityFieldInfo {

    private FieldDataType type;

    private String name;

    private String references;

    private Class<? extends Enum<?>> enumImplClass;

    public EntityFieldInfo(FieldDataType type, String name, String references, Class<? extends Enum<?>> enumImplClass) {
        this.type = type;
        this.name = name;
        this.references = references;
        this.enumImplClass = enumImplClass;
    }
    
    public FieldDataType getType() {
        return type;
    }
    
    public Class<?> getJavaClass() {
        return type.javaClass();
    }

    public String getName() {
        return name;
    }

    public String getReferences() {
        return references;
    }

    public Class<? extends Enum<?>> getEnumImplClass() {
        return enumImplClass;
    }

    public boolean references() {
        return type.references();
    }
    
    public boolean isRef() {
        return type.isRef();
    }
    
    public boolean isEnum() {
        return type.isEnum();
    }
    
    public boolean isListOnly() {
        return type.isListOnly();
    }
    
    public boolean isChild() {
        return type.isChild();
    }
    
    public boolean isChildList() {
        return type.isChildList();
    }

    public boolean isDate() {
        return type.isDate();
    }

    public boolean isTimestamp() {
        return type.isTimestamp();
    }

    public boolean isBoolean() {
        return type.isBoolean();
    }

    public boolean isString() {
        return type.isString();
    }
    
    public boolean isInteger() {
        return type.isInteger();
    }
    
    public boolean isDouble() {
        return type.isDouble();
    }
    
    public boolean isDecimal() {
        return type.isDecimal();
    }

}
