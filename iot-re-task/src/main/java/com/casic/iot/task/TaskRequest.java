package com.casic.iot.task;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zouping on 2018.08.24
 */
public class TaskRequest {
	//请求的数据
	private String requestStr;
	//请求的租户ID
	private String tenantID;
	//请求参数
	private Map<String,String> params = new HashMap<>();

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

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
}
