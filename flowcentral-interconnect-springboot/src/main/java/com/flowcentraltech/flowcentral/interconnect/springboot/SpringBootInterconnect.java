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
package com.flowcentraltech.flowcentral.interconnect.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.flowcentraltech.flowcentral.interconnect.common.Interconnect;

/**
 * Flowcentral spring boot interconnect.
 * 
 * @author FlowCentral Technologies Limited
 * @since 1.0
 */
@Component
public class SpringBootInterconnect extends Interconnect {

    @Autowired
    public SpringBootInterconnect() {
        super(RefType.OBJECT);
    }
}
