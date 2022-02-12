/*
 * Copyright (c) 2019, 2021, FlowCentral Technologies.
 * All rights reserved.
 * 
 * PROPRIETARY AND CONFIDENTIAL. USE IS SUBJECT TO LICENSE TERMS.
 */

package com.flowcentraltech.flowcentral.interconnect.springboot.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowcentraltech.flowcentral.interconnect.common.constants.DataSourceErrorCodeConstants;
import com.flowcentraltech.flowcentral.interconnect.common.data.DataSourceRequest;
import com.flowcentraltech.flowcentral.interconnect.common.data.DataSourceResponse;
import com.flowcentraltech.flowcentral.interconnect.springboot.service.SpringBootInterconnectService;

/**
 * Flow central spring boot interconnect controller.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
@RestController
@RequestMapping("/flowcentral/interconnect")
public class SpringBootInterconnectController {

    private static final Logger LOGGER = Logger.getLogger(SpringBootInterconnectController.class.getName());

    private final SpringBootInterconnectService springBootInterconnectService;

    @Autowired
    public SpringBootInterconnectController(SpringBootInterconnectService springBootInterconnectService) {
        this.springBootInterconnectService = springBootInterconnectService;
    }

    @PostMapping(path = "/datasource")
    public DataSourceResponse createPostingConfiguration(@RequestBody DataSourceRequest req) {
        DataSourceResponse resp = null;
        try {
            return springBootInterconnectService.processDataSourceRequest(req);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            LOGGER.log(Level.SEVERE, errorMessage, e);
            resp = new DataSourceResponse();
            resp.setErrorCode(DataSourceErrorCodeConstants.PROVIDER_SERVICE_EXCEPTION);
            resp.setErrorMsg(errorMessage);
        }

        return resp;
    }
}
