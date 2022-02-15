/*
 * Copyright 2021-2022 FlowCentral Technologies Limited.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
 * @author FlowCentral Technologies Limited
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
