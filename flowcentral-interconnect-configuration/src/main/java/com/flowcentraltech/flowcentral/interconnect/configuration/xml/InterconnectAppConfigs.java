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
 * Interconnect application configuration.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public class InterconnectAppConfigs {

    private List<InterconnectAppConfig> appConfigList;

    public List<InterconnectAppConfig> getAppConfigList() {
        return appConfigList;
    }

    @XmlElement(name = "interconnect-application")
    public void setAppConfigList(List<InterconnectAppConfig> appConfigList) {
        this.appConfigList = appConfigList;
    }

}
