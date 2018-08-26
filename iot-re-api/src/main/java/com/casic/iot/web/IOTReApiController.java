package com.casic.iot.web;

import com.casic.iot.core.RuleEngineJobDetail;
import com.casic.iot.job.JobClientManager;
import com.github.ltsopensource.cmd.DefaultHttpCmd;
import com.github.ltsopensource.cmd.HttpCmd;
import com.github.ltsopensource.cmd.HttpCmdClient;
import com.github.ltsopensource.cmd.HttpCmdResponse;
import com.github.ltsopensource.core.cmd.HttpCmdNames;
import com.github.ltsopensource.core.commons.utils.StringUtils;
import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.jobclient.domain.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.ltsopensource.tasktracker.Result;

import java.io.IOException;

/**
 * 所有api开放接口
 */
@RestController
public class IOTReApiController {
    @Autowired
    JobClientManager jobClientManager;

    @RequestMapping(value = "/api/1.0/task/start", method = RequestMethod.GET)
    public Result startTask() {
        //TODO 从数据库里面读取用户规则转化成任务参数,模拟用户参数
        RuleEngineJobDetail ruleEngineJob = new RuleEngineJobDetail();
        ruleEngineJob.setTaskId(StringUtils.generateUUID());
        ruleEngineJob.setTenantID("10000");
        //将数据来源设置成MQTT
        ruleEngineJob.setJobMQ(RuleEngineJobDetail.MQ_MQTT);
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

        Response reponse = jobClientManager.submitRealtimeJob(ruleEngineJob);
        if(reponse.isSuccess()) {
            return new Result(Action.EXECUTE_SUCCESS, "任务启动成功！");
        }else{
            return new Result(Action.EXECUTE_FAILED, reponse.getMsg());
        }
    }

    @RequestMapping(value = "/api/1.0/task/stop", method = RequestMethod.GET)
    public Result cancelTask(@RequestParam String taskId) {
        Response response = null;
        try {
            response = jobClientManager.cancelJob(taskId);

            if(response.isSuccess()) {
                return new Result(Action.EXECUTE_SUCCESS, "任务取消成功！");
            }else{
                return new Result(Action.EXECUTE_FAILED, response.getMsg());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(Action.EXECUTE_FAILED, e.getMessage());
        }
    }
}
