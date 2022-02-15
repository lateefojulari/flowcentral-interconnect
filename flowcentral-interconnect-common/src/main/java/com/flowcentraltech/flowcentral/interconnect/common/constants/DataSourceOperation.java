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

/**
 * Data source operation.
 * 
 * @author FlowCentral Technologies Limited
 * @since 1.0
 */
public enum DataSourceOperation {
    CREATE(false),
    FIND(true),
    FIND_LEAN(true),
    FIND_ALL(true),
    LIST(true),
    LIST_LEAN(true),
    LIST_ALL(true),
    UPDATE(false),
    UPDATE_LEAN(false),
    UPDATE_ALL(false),
    DELETE(false),
    DELETE_ALL(false),
    COUNT_ALL(false),
    VALUE(false),
    VALUE_LIST(false);

    private final boolean entityResult;
    
    private DataSourceOperation(boolean entityResult) {
        this.entityResult = entityResult;
    }
    
    public boolean isFind() {
        return this.equals(FIND) || this.equals(FIND_LEAN) || this.equals(FIND_ALL);
    }

    public boolean isList() {
        return this.equals(LIST) || this.equals(LIST_LEAN) || this.equals(LIST_ALL);
    }

    public boolean isLean() {
        return this.equals(FIND_LEAN) || this.equals(LIST_LEAN) || this.equals(FIND_ALL) || this.equals(LIST_ALL);
    }

    public boolean isMultipleResult() {
        return this.equals(FIND_ALL) || this.equals(LIST_ALL) || this.equals(VALUE_LIST);
    }

    public boolean isSingleResult() {
        return this.equals(FIND) || this.equals(FIND_LEAN) || this.equals(LIST) || this.equals(LIST_LEAN);
    }
    
    public boolean entityResult() {
        return entityResult;
    }
    
    public String toJson() {
       return this.name();
    }
    
    public static DataSourceOperation of(String val) {
        return val != null ? DataSourceOperation.of(val): null;
    }
}
