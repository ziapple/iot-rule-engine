package com.casic.iot.job;

import com.casic.iot.core.RuleEngineJobDetail;
import com.github.ltsopensource.core.commons.utils.StringUtils;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.core.logger.Logger;
import com.github.ltsopensource.core.logger.LoggerFactory;
import com.github.ltsopensource.jobclient.JobClient;
import com.github.ltsopensource.jobclient.domain.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zouping on 2018.08.24.
 * 客户端的任务发起者
 */
@Component
public class JobClientManager{
    private static final Logger LOGGER = LoggerFactory.getLogger(JobClientManager.class);

    @Autowired
    private JobClient jobClient;

    @Value("${lts.tasktracker.node-group}")
    private String taskGroup;

    /**
     * 提交实时任务
     * TaskTrackerNodeGroup：任务作业集群名称
     */
    public void submitRealtimeJob(RuleEngineJobDetail ruleEngineJob) {
        Job job = new Job();
        job.setTaskId(StringUtils.generateUUID());

        job.setParam(RuleEngineJobDetail.TENANT_ID, ruleEngineJob.getTenantID());

        //设置任务的数据来源和处理任务类型
        job.setParam(RuleEngineJobDetail.JOB_MQ, ruleEngineJob.getJobMQ());
        job.setParam(RuleEngineJobDetail.JOB_TASK, ruleEngineJob.getJobTask());
        //任务参数设置
        job.getExtParams().putAll(ruleEngineJob.getParams());

        job.setTaskTrackerNodeGroup(taskGroup);
        job.setNeedFeedback(true);
        // 当任务队列中存在这个任务的时候，是否替换更新
        job.setReplaceOnExist(true);

        LOGGER.info("任务提交{}", ruleEngineJob.toString());
        Response response = jobClient.submitJob(job);
        System.out.println(response);
    }
}
