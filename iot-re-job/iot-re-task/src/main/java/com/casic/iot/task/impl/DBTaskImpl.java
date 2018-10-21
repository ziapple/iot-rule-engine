package com.casic.iot.task.impl;

import com.casic.iot.task.TaskFilter;
import com.casic.iot.task.TaskFilterChain;
import com.casic.iot.task.TaskRequest;
import com.casic.iot.task.TaskResponse;

/**
 * 数据过滤器
 * 将数据写入到数据库
 * @author zouping on 2018.08.23
 */
public class DBTaskImpl implements TaskFilter {
 
	@Override
	public void doFilter(TaskRequest request, TaskResponse response, TaskFilterChain chain) {
		//TODO 调用数据写入接口
		request.getParams();

		chain.doFilter(request, response, chain);
	}
}