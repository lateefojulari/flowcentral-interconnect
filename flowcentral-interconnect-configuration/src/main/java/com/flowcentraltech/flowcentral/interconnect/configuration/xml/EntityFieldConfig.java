/*
 * Copyright (c) 2019, 2021, FlowCentral Technologies.
 * All rights reserved.
 * 
 * PROPRIETARY AND CONFIDENTIAL. USE IS SUBJECT TO LICENSE TERMS.
 */
package com.flowcentraltech.flowcentral.interconnect.configuration.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.flowcentraltech.flowcentral.interconnect.configuration.constants.FieldDataType;
import com.flowcentraltech.flowcentral.interconnect.configuration.xml.adapter.FieldDataTypeXmlAdapter;

/**
 * Entity field configuration.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public class EntityFieldConfig {

    private FieldDataType type;

    private String name;

    private String references;

    private String enumImplClass;

    public FieldDataType getType() {
        return type;
    }

    @XmlJavaTypeAdapter(FieldDataTypeXmlAdapter.class)
    @XmlAttribute(required = true)
    public void setType(FieldDataType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    @XmlAttribute(required = true)
    public void setName(String name) {
        this.name = name;
    }

    public String getReferences() {
        return references;
    }

    @XmlAttribute
    public void setReferences(String references) {
        this.references = references;
    }

    public String getEnumImplClass() {
        return enumImplClass;
    }

    @XmlAttribute(name = "enum-impl")
    public void setEnumImplClass(String enumImplClass) {
        this.enumImplClass = enumImplClass;
    }

}
