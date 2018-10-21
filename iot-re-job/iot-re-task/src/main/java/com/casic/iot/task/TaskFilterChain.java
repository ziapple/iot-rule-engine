package com.casic.iot.task;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zouping on 2018.08.24
 */
public class TaskFilterChain implements TaskFilter {
	List<TaskFilter> filters = new ArrayList<TaskFilter>();
	int index = 0;

	public TaskFilterChain addFilter(TaskFilter f) {
		this.filters.add(f);
		return this;
	}

	@Override
	public void doFilter(TaskRequest request, TaskResponse response, TaskFilterChain chain) {
		if (index == filters.size())
			return;
		TaskFilter f = filters.get(index);
		index++;
		f.doFilter(request, response, chain);
	}
}
