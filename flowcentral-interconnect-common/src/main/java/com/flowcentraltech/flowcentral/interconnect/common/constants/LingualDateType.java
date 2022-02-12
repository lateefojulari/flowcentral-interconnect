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
 * Lingual date type constants.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public enum LingualDateType {

    TODAY(
            "TDY"),
    YESTERDAY(
            "YST"),
    TOMORROW(
            "TMR"),
    THIS_WEEK(
            "TWK"),
    LAST_WEEK(
            "LWK"),
    NEXT_WEEK(
            "NWK"),
    THIS_MONTH(
            "TMN"),
    LAST_MONTH(
            "LMN"),
    NEXT_MONTH(
            "NMN"),
    LAST_3MONTHS(
            "L3M"),
    NEXT_3MONTHS(
            "N3M"),
    LAST_6MONTHS(
            "L6M"),
    NEXT_6MONTHS(
            "N6M"),
    LAST_9MONTHS(
            "L9M"),
    NEXT_9MONTHS(
            "N9M"),
    LAST_12MONTHS(
            "L1M"),
    NEXT_12MONTHS(
            "N1M"),
    THIS_YEAR(
            "TYR"),
    LAST_YEAR(
            "LYR"),
    NEXT_YEAR(
            "NYR");

    private final String code;

    private static final Map<String, LingualDateType> typesByCode;
    static {
        Map<String, LingualDateType> map = new HashMap<String, LingualDateType>();
        for (LingualDateType type: LingualDateType.values()) {
            map.put(type.code, type);
        }
        
        typesByCode = Collections.unmodifiableMap(map);
    }

    private LingualDateType(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }

    public static LingualDateType fromCode(String code) {
        return code != null? typesByCode.get(code) : null;
    }

    public static LingualDateType fromName(String name) {
        return name != null? LingualDateType.valueOf(name.toUpperCase()) : null;
    }
}
