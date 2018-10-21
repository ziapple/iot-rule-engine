package com.casic.iot.task;

/**
 * @author zouping on 2018.08.24
 */
public interface TaskFilter {
	public void doFilter(TaskRequest request, TaskResponse response, TaskFilterChain chain);
}
