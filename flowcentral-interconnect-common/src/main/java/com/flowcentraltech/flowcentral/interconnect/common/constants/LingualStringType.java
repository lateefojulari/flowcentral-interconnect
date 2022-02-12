/*
 * Copyright (c) 2019, 2021, FlowCentral Technologies.
 * All rights reserved.
 * 
 * PROPRIETARY AND CONFIDENTIAL. USE IS SUBJECT TO LICENSE TERMS.
 */

package com.flowcentraltech.flowcentral.interconnect.common.constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Lingual string type constants.
 * 
 * @author Lateef Ojulari
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
