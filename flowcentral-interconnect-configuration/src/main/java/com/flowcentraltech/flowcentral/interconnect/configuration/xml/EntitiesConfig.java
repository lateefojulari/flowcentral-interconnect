/*
 * Copyright (c) 2019, 2021, FlowCentral Technologies.
 * All rights reserved.
 * 
 * PROPRIETARY AND CONFIDENTIAL. USE IS SUBJECT TO LICENSE TERMS.
 */
package com.flowcentraltech.flowcentral.interconnect.configuration.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * Entities configuration.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public class EntitiesConfig {

    private List<EntityConfig> entityList;

    public List<EntityConfig> getEntityList() {
        return entityList;
    }

    @XmlElement(name = "entity")
    public void setEntityList(List<EntityConfig> entityList) {
        this.entityList = entityList;
    }

}
