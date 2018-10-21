package com.casic.iot.job;

import com.casic.iot.core.RuleEngineJobDetail;
import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.core.logger.Logger;
import com.github.ltsopensource.core.logger.LoggerFactory;
import com.github.ltsopensource.spring.boot.annotation.JobRunner4TaskTracker;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.logger.BizLogger;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @author zouping on 2018.08.24
 */
@JobRunner4TaskTracker
public class IOTRuleEngineMainJob implements JobRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(IOTRuleEngineMainJob.class);

    /**
     * 启动一个租户的规则引擎任务
     * params:{taskID:租户ID,mqttAddr:mqtt地址,ruleFilters:过滤器}
     * ruleFilters:
     */
    @Override
    public Result run(JobContext jobContext) throws Throwable {
        try {
            BizLogger bizLogger = jobContext.getBizLogger();
            String tenantID = jobContext.getJob().getParam(RuleEngineJobDetail.TENANT_ID);
            String jobMQ = jobContext.getJob().getParam(RuleEngineJobDetail.JOB_MQ);
            String jobTask = jobContext.getJob().getParam(RuleEngineJobDetail.JOB_TASK);
            Map<String,String> jobParams = jobContext.getJob().getExtParams();

            //校验必填参数
            Assert.hasText(tenantID, "租户ID未设置");
            Assert.hasText(jobMQ, "任务的数据来源未设置！");
            Assert.hasText(jobTask, "要执行任务未设置！");

            LOGGER.info("执行租户{}的规则过滤任务:{}", tenantID, jobParams);
            //会发送到 LTS (JobTracker上)
            bizLogger.info("执行租户:" + tenantID + "的规则过滤任务");

            if(jobMQ.equals(RuleEngineJobDetail.MQ_MQTT)){//数据来源于MQTT
                JobRunner job = new MockMQTTJobImpl();
                return job.run(jobContext);
            }else if(jobMQ.equals(RuleEngineJobDetail.MQ_KAFKA)){//数据来源于Kafka
                JobRunner job = new PlatformKafkaJobImpl();
                return job.run(jobContext);
            }else{
                LOGGER.info("Run job failed!");
                return new Result(Action.EXECUTE_FAILED, "数据来源设置不正确！");
            }
        } catch (Exception e) {
            LOGGER.info("Run job failed!", e);
            return new Result(Action.EXECUTE_FAILED, e.getMessage());
        }
    }
}
