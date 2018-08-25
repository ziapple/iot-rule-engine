package com.casic.iot;

import com.casic.iot.task.impl.DataFilterImpl;
import com.casic.iot.task.TaskFilterChain;
import com.casic.iot.task.TaskRequest;
import com.casic.iot.task.TaskResponse;

public class TaskFilterChainTest {
	public static void main(String[] args) {
		String message = "敏感词汇，重庆，<script> 躲猫猫 :)";
		TaskRequest request = new TaskRequest();
		request.setRequestStr(message);
		TaskResponse response = new TaskResponse();
		response.setResponseStr("response");
		TaskFilterChain fc = new TaskFilterChain();
		fc.addFilter(new DataFilterImpl());
		fc.doFilter(request, response, fc);
		System.out.println("request = " + request.getRequestStr());
		System.out.println("response = " + response.getResponseStr());
	}
 
}
