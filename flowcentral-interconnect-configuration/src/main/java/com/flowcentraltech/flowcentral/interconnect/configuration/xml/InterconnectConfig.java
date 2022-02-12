/*
 * Copyright (c) 2019, 2021, FlowCentral Technologies.
 * All rights reserved.
 * 
 * PROPRIETARY AND CONFIDENTIAL. USE IS SUBJECT TO LICENSE TERMS.
 */
package com.flowcentraltech.flowcentral.interconnect.configuration.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Interconnect configuration.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
@XmlRootElement(name = "interconnect")
public class InterconnectConfig {

    private String name;

    private String description;

    private InterconnectAppConfigs interconnectAppConfigs;

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

    public InterconnectAppConfigs getInterconnectAppConfigs() {
        return interconnectAppConfigs;
    }

    @XmlElement(name = "interconnect-applications")
    public void setInterconnectAppConfigs(InterconnectAppConfigs interconnectAppConfigs) {
        this.interconnectAppConfigs = interconnectAppConfigs;
    }

}
