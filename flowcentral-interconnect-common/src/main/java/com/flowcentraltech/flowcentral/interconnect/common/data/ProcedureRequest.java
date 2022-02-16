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
package com.flowcentraltech.flowcentral.interconnect.common.data;

import java.util.Arrays;

/**
 * Procedure request.
 * 
 * @author FlowCentral Technologies Limited
 * @since 1.0
 */
public class ProcedureRequest extends BaseRequest {

    private String operation;
    
    private Long id;
    
    private Long versionNo;
    
    private boolean readOnly;
    
    public ProcedureRequest(String operation, Long id, Long versionNo) {
        this.operation = operation;
        this.id = id;
        this.versionNo = versionNo;
    }

    public ProcedureRequest(String operation) {
        this.operation = operation;
    }

    public ProcedureRequest() {
        
    }

    public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	@Override
	public String toString() {
		return "ProcedureRequest [operation=" + operation + ", entity=" + getEntity() + ", id=" + id + ", versionNo="
				+ versionNo + ", payload=" + Arrays.toString(getPayload()) + ", readOnly=" + readOnly + "]";
	}

}
