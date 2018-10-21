package com.casic.iot.task.impl;

import com.casic.iot.task.TaskFilter;
import com.casic.iot.task.TaskFilterChain;
import com.casic.iot.task.TaskRequest;
import com.casic.iot.task.TaskResponse;
import com.github.ltsopensource.core.json.JSON;
import com.github.ltsopensource.core.json.JSONArray;
import com.github.ltsopensource.core.json.JSONObject;

/**
 * task实现类，第一步json数据过滤器，实现基于SQL语句的数据过滤，转换
 * @author zouping on 2018.08.24
 */
public class DataFilterTaskImpl implements TaskFilter {
 
	@Override
	public void doFilter(TaskRequest request, TaskResponse response, TaskFilterChain chain) {
		String originStr = request.getRequestStr();
		String convertedStr = "";
		response.setResponseStr(convertedStr);
		//执行下一个过滤器
		chain.doFilter(request, response, chain);
	}
}