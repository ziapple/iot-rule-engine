package com.casic.iot.web;

import com.casic.iot.job.JobClientReferenceBean;
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
public class IotReApiController {
    @Autowired
    JobClientReferenceBean jobClientBean;

    @RequestMapping(value = "/api/1.0/task/start", method = RequestMethod.GET)
    public Result startTask() {
        /* 提交实时任务 */
        jobClientBean.submitRealtimeJob();

        return new Result(Action.EXECUTE_SUCCESS, "任务启动成功");
    }
}
