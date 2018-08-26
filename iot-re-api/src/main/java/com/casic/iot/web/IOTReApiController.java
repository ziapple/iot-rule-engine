package com.casic.iot.web;

import com.casic.iot.core.RuleEngineJobDetail;
import com.casic.iot.job.JobClientManager;
import com.github.ltsopensource.core.domain.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.github.ltsopensource.tasktracker.Result;

/**
 * 所有api开放接口
 */
@RestController
public class IOTReApiController {
    @Autowired
    JobClientManager jobClientBean;

    @RequestMapping(value = "/api/1.0/task/start", method = RequestMethod.GET)
    public Result startTask() {
        //TODO 从数据库里面读取用户规则转化成任务参数,模拟用户参数
        RuleEngineJobDetail ruleEngineJob = new RuleEngineJobDetail();
        ruleEngineJob.setTenantID("10000");
        //将数据来源设置成MQTT
        ruleEngineJob.setJobMQ(RuleEngineJobDetail.JOB_MQ);
        //MQTT设置
        ruleEngineJob.getParams().put(RuleEngineJobDetail.MQ_MQTT_HOST, "127.0.0.1");
        ruleEngineJob.getParams().put(RuleEngineJobDetail.MQ_MQTT_TOPIC, "topic");
        ruleEngineJob.getParams().put(RuleEngineJobDetail.MQ_MQTT_USERNAME, "admin");
        ruleEngineJob.getParams().put(RuleEngineJobDetail.MQ_MQTT_PASSWORD, "admin");
        //将任务处理设置成TASK_DATA和TASK_TSDB
        ruleEngineJob.setJobTask(RuleEngineJobDetail.TASK_DATA + "," + RuleEngineJobDetail.TASK_TSDB);
        //数据过滤规则设置
        ruleEngineJob.getParams().put(RuleEngineJobDetail.DATA_FILTER, "temp>50 and kw<100");
        //TSDB设置
        ruleEngineJob.getParams().put(RuleEngineJobDetail.TSDB_CONN, "jdbc://127.0.0.1:8223/tsdb");

        jobClientBean.submitRealtimeJob(ruleEngineJob);

        return new Result(Action.EXECUTE_SUCCESS, "任务启动成功");
    }
}
