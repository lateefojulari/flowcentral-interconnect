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
package com.flowcentraltech.flowcentral.interconnect.common.constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Lingual string type constants.
 * 
 * @author FlowCentral Technologies Limited
 * @since 1.0
 */
public enum LingualStringType {

    GENERATED(
            "GEN", "<generated>");

    private final String code;

    private final String value;

    private static final Map<String, LingualStringType> typesByCode;
    static {
        Map<String, LingualStringType> map = new HashMap<String, LingualStringType>();
        for (LingualStringType type: LingualStringType.values()) {
            map.put(type.code, type);
        }
        
        typesByCode = Collections.unmodifiableMap(map);
    }

    private LingualStringType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String code() {
        return this.code;
    }

    public String value() {
        return this.value;
    }

    public static LingualStringType fromCode(String code) {
        return code != null? typesByCode.get(code) : null;
    }

    public static LingualStringType fromName(String name) {
        return name != null? LingualStringType.valueOf(name.toUpperCase()) : null;
    }

}
