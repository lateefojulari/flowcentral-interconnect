/*
 * Copyright (c) 2019, 2021, FlowCentral Technologies.
 * All rights reserved.
 * 
 * PROPRIETARY AND CONFIDENTIAL. USE IS SUBJECT TO LICENSE TERMS.
 */

package com.flowcentraltech.flowcentral.interconnect.configuration.xml.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.flowcentraltech.flowcentral.interconnect.configuration.constants.FieldDataType;

/**
 * Interconnect field data type XML adapter.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public class FieldDataTypeXmlAdapter extends XmlAdapter<String, FieldDataType> {

    @Override
    public FieldDataType unmarshal(String str) throws Exception {
        return str != null ? FieldDataType.valueOf(str.toUpperCase()) : null;
    }

    @Override
    public String marshal(FieldDataType type) throws Exception {
        return type != null ? type.name() : null;
    }

}
