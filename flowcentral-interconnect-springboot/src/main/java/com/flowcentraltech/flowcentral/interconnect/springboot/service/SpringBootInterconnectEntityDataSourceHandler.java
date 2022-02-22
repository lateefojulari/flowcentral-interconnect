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
package com.flowcentraltech.flowcentral.interconnect.springboot.service;

import com.flowcentraltech.flowcentral.interconnect.common.data.DataSourceRequest;

/**
 * Flow central spring boot interconnect datasource entity handler.
 * 
 * @author FlowCentral Technologies Limited
 * @since 1.0
 */
public interface SpringBootInterconnectEntityDataSourceHandler {

	/**
	 * Processes a data source request for an entity
	 * 
	 * @param implClass the entity type
	 * @param req       the data source request
	 * @return the processing result
	 */
	Object[] process(Class<?> implClass, DataSourceRequest req);
}
