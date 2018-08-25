package com.casic.iot.task;

/**
 * @author zouping on 2018.08.24
 */
public class TaskRequest {
	//请求的数据
	public String requestStr;
	//请求的租户ID
	public String tenantID;

	public TaskRequest(){

	}

	public String getRequestStr() {
		return requestStr;
	}
 
	public void setRequestStr(String requestStr) {
		this.requestStr = requestStr;
	}

	public String getTenantID() {
		return tenantID;
	}

	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}
}
