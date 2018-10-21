package com.casic.iot.job;

import com.casic.iot.core.RuleEngineJobDetail;
import com.casic.iot.task.TaskFilter;
import com.casic.iot.task.TaskFilterChain;
import com.casic.iot.task.TaskRequest;
import com.casic.iot.task.TaskResponse;
import com.casic.iot.task.impl.DBTaskImpl;
import com.casic.iot.task.impl.DataFilterTaskImpl;
import com.casic.iot.task.impl.KafkaTaskImpl;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;

import java.util.Date;
import java.util.Map;

public class MockMQTTJobImpl implements JobRunner {

    @Override
    public Result run(JobContext jobContext) throws Throwable {
        String tenantID = jobContext.getJob().getParam(RuleEngineJobDetail.TENANT_ID);
        String jobTask = jobContext.getJob().getParam(RuleEngineJobDetail.JOB_TASK);
        Map<String, String> jobParams = jobContext.getJob().getExtParams();

        //TODO 获取Mqtt任务å
        while (true) {
            //模拟数据，1秒钟发一次
            Thread.currentThread().sleep(1000);
            String requestData = "{equipmentId:10000,data:[" +
                    "{temp:'35.6', kw: '52.5'}," +
                    "{temp:'42.6', kw: '100.5'}" +
                    "]}";

            System.out.println("taskID:" + jobContext.getJob().getTaskId() + ",拉取MQTT数据：" + new Date() + requestData);
            TaskRequest request = new TaskRequest();
            request.setTenantID(tenantID);
            request.setRequestStr(requestData);
            request.setParams(jobParams);

            TaskResponse response = new TaskResponse();
            TaskFilterChain filterChain = new TaskFilterChain();
            String[] tasks = jobTask.split(",");
            for (String task : tasks) {
                if (task.equals(RuleEngineJobDetail.TASK_DATA)) {
                    TaskFilter dataFilter = new DataFilterTaskImpl();
                    filterChain.addFilter(dataFilter);
                } else if (task.equals(RuleEngineJobDetail.TASK_TSDB)) {
                    TaskFilter dbFilter = new DBTaskImpl();
                    filterChain.addFilter(dbFilter);
                } else if (task.equals(RuleEngineJobDetail.TASK_KAFKA)) {
                    TaskFilter kafkaFilter = new KafkaTaskImpl();
                    filterChain.addFilter(kafkaFilter);
                }
            }
            filterChain.doFilter(request, response, filterChain);
        }
    }
}
