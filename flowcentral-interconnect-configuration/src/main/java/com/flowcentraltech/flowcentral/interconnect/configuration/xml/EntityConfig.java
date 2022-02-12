/*
 * Copyright (c) 2019, 2021, FlowCentral Technologies.
 * All rights reserved.
 * 
 * PROPRIETARY AND CONFIDENTIAL. USE IS SUBJECT TO LICENSE TERMS.
 */
package com.flowcentraltech.flowcentral.interconnect.configuration.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Entity configuration.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public class EntityConfig {

    private String name;

    private String description;

    private String implementation;

    private String idFieldName;

    private String versionNoFieldName;

    private List<EntityFieldConfig> entityFieldList;

    public String getName() {
        return name;
    }

    @XmlAttribute(required = true)
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    @XmlAttribute(required = true)
    public void setDescription(String description) {
        this.description = description;
    }

    public String getImplementation() {
        return implementation;
    }

    @XmlAttribute(name = "impl", required = true)
    public void setImplementation(String implementation) {
        this.implementation = implementation;
    }

    public String getIdFieldName() {
        return idFieldName;
    }

    @XmlAttribute(name = "id-field")
    public void setIdFieldName(String idFieldName) {
        this.idFieldName = idFieldName;
    }

    public String getVersionNoFieldName() {
        return versionNoFieldName;
    }

    @XmlAttribute(name = "version-field")
    public void setVersionNoFieldName(String versionNoFieldName) {
        this.versionNoFieldName = versionNoFieldName;
    }

    public List<EntityFieldConfig> getEntityFieldList() {
        return entityFieldList;
    }

    @XmlElement(name = "field")
    public void setEntityFieldList(List<EntityFieldConfig> entityFieldList) {
        this.entityFieldList = entityFieldList;
    }

}
