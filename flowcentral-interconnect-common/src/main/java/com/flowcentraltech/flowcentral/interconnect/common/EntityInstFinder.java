/*
 * Copyright (c) 2019, 2021, FlowCentral Technologies.
 * All rights reserved.
 * 
 * PROPRIETARY AND CONFIDENTIAL. USE IS SUBJECT TO LICENSE TERMS.
 */

package com.flowcentraltech.flowcentral.interconnect.common;

/**
 * Entity instance finder.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public interface EntityInstFinder {

    /**
     * Finds an entity instance by ID.
     * 
     * @param entityClass
     *                    the entity class
     * @param id
     *                    the entity ID
     * @return the entity instance if found otherwise null;
     * @throws Exception
     *                   if an error occurs
     */
    <T> T findById(Class<T> entityClass, Object id) throws Exception;
}
