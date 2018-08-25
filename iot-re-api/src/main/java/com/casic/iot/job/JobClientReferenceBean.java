package com.casic.iot.job;

import com.github.ltsopensource.core.commons.utils.StringUtils;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.jobclient.JobClient;
import com.github.ltsopensource.jobclient.domain.Response;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zouping on 2018.08.24.
 */
@Component
public class JobClientReferenceBean implements InitializingBean {

    /**
     * 自己的业务类,就可以这样引用了
     */
    @Autowired
    private JobClient jobClient;

    @Override
    public void afterPropertiesSet() throws Exception {
        submitRealtimeJob();
    }

    /**
     * 提交实时任务
     * TaskTrackerNodeGroup：任务作业集群名称
     * @param jobClient
     */
    public void submitRealtimeJob() {
        Job job = new Job();
        job.setTaskId(StringUtils.generateUUID());
        job.setParam("shopId", "1122222221");
        job.setTaskTrackerNodeGroup("test_trade_TaskTracker");
        job.setNeedFeedback(true);
        job.setReplaceOnExist(true);        // 当任务队列中存在这个任务的时候，是否替换更新
        Response response = jobClient.submitJob(job);
        System.out.println(response);
    }
}
