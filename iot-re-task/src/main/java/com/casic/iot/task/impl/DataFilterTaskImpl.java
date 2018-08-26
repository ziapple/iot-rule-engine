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
public class DataFilterTaskImpl implements TaskFilter {
 
	@Override
	public void doFilter(TaskRequest request, TaskResponse response, TaskFilterChain chain) {
		//将json转化成JSONObject
		JSONObject jsonObject = JSON.parseObject(request.getRequestStr());
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		for(Object row : jsonArray.toArray()){
			JSONObject rowObject = (JSONObject) row;
			System.out.println("处理数据：" + rowObject);
		}

		//TODO 将过滤后的数据变成字符串
		request.setRequestStr(jsonObject.toJSONString());
		chain.doFilter(request, response, chain);
	}
}