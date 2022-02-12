/*
 * Copyright (c) 2019, 2021, FlowCentral Technologies.
 * All rights reserved.
 * 
 * PROPRIETARY AND CONFIDENTIAL. USE IS SUBJECT TO LICENSE TERMS.
 */

package com.flowcentraltech.flowcentral.interconnect.springboot.service;

import com.flowcentraltech.flowcentral.interconnect.common.data.DataSourceRequest;
import com.flowcentraltech.flowcentral.interconnect.common.data.DataSourceResponse;

/**
 * Flow central spring boot interconnect service.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public interface SpringBootInterconnectService {

    /**
     * Processes a data source request.
     * 
     * @param req
     *                the request to process
     * @return the data source response
     * @throws Exception
     *                   if an error occurs
     */
    DataSourceResponse processDataSourceRequest(DataSourceRequest req) throws Exception;
}
