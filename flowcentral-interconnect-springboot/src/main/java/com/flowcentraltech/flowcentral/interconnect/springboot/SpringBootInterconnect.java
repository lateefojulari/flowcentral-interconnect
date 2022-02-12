/*
 * Copyright (c) 2019, 2021, FlowCentral Technologies.
 * All rights reserved.
 * 
 * PROPRIETARY AND CONFIDENTIAL. USE IS SUBJECT TO LICENSE TERMS.
 */

package com.flowcentraltech.flowcentral.interconnect.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.flowcentraltech.flowcentral.interconnect.common.Interconnect;

/**
 * Flowcentral spring boot interconnect.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
@Component
public class SpringBootInterconnect extends Interconnect {

    @Autowired
    public SpringBootInterconnect() {
        super(RefType.OBJECT);
    }
}
