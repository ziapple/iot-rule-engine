package com.casic.iot.task.impl;

import com.casic.iot.task.TaskFilter;
import com.casic.iot.task.TaskFilterChain;
import com.casic.iot.task.TaskRequest;
import com.casic.iot.task.TaskResponse;

/**
 * 时序数据过滤器
 * 将数据写入到时序数据库ƒ
 * @author zouping on 2018.08.23
 */
public class TSDBTaskImpl implements TaskFilter {
 
	@Override
	public void doFilter(TaskRequest request, TaskResponse response, TaskFilterChain chain) {
		response.responseStr = request.getRequestStr().replace("<", "[")
				.replace(">", "]");
		chain.doFilter(request, response, chain);
	}
}