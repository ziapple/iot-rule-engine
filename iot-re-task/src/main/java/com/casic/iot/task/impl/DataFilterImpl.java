package com.casic.iot.task.impl;

import com.casic.iot.task.TaskFilter;
import com.casic.iot.task.TaskFilterChain;
import com.casic.iot.task.TaskRequest;
import com.casic.iot.task.TaskResponse;
import com.github.ltsopensource.core.json.JSON;
import com.github.ltsopensource.core.json.JSONArray;
import com.github.ltsopensource.core.json.JSONObject;

/**
 * json数据过滤器
 * @author zouping on 2018.08.24
 */
public class DataFilterImpl implements TaskFilter {
 
	@Override
	public void doFilter(TaskRequest request, TaskResponse response, TaskFilterChain chain) {
		//TODO 将json转化成JSONObject
		JSONObject jsonObject = JSON.parseObject(request.getRequestStr());
		JSONArray data = jsonObject.getJSONArray("data");
		
		chain.doFilter(request, response, chain);
	}
}