/*
 * Copyright (c) 2019, 2021, FlowCentral Technologies.
 * All rights reserved.
 * 
 * PROPRIETARY AND CONFIDENTIAL. USE IS SUBJECT TO LICENSE TERMS.
 */
package com.flowcentraltech.flowcentral.interconnect.configuration.xml;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Interconnect application configuration.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public class InterconnectAppConfig {

    private String configFile;

    public String getConfigFile() {
        return configFile;
    }

    @XmlAttribute(name = "config-file")
    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

}
