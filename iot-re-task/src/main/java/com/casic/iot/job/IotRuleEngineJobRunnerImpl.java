package com.casic.iot.job;

import com.casic.iot.task.TaskRequest;
import com.casic.iot.task.TaskResponse;
import com.casic.iot.task.impl.DataFilterImpl;
import com.casic.iot.task.TaskFilterChain;
import com.casic.iot.task.impl.TSDBFilterImpl;
import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.core.logger.Logger;
import com.github.ltsopensource.core.logger.LoggerFactory;
import com.github.ltsopensource.spring.boot.annotation.JobRunner4TaskTracker;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.logger.BizLogger;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;
import org.springframework.util.Assert;

/**
 * @author zouping on 2018.08.24
 */
@JobRunner4TaskTracker
public class IotRuleEngineJobRunnerImpl implements JobRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(IotRuleEngineJobRunnerImpl.class);
    private static final String FILTER_DATA = "FILTER_DATA";
    private static final String FILTER_TSDB = "FILTER_TSDB";

    //租户ID
    private String tenantID;
    //mqtt地址
    private String mqttAddr;
    //过滤器
    private String ruleFilters;

    /**
     * 启动一个租户的规则引擎任务
     * params:{taskID:租户ID,mqttAddr:mqtt地址,ruleFilters:过滤器}
     * ruleFilters:
     */
    @Override
    public Result run(JobContext jobContext) throws Throwable {
        try {
            BizLogger bizLogger = jobContext.getBizLogger();
            tenantID = jobContext.getJob().getParam("tenantID");
            Assert.hasText(tenantID, "租户ID未设置");
            mqttAddr = jobContext.getJob().getParam("mqttAddr");
            Assert.hasText(mqttAddr, "MQTT地址未设置");
            ruleFilters = jobContext.getJob().getParam("ruleFilters");
            Assert.hasText(ruleFilters, "过滤器未设置");
            LOGGER.info("执行租户{}的规则过滤任务,mqtt:{},过滤器:{}", tenantID, mqttAddr, ruleFilters);
            // 会发送到 LTS (JobTracker上)
            bizLogger.info("执行租户:" + tenantID + "的规则过滤任务,mqtt:" + mqttAddr + ",过滤器:" + ruleFilters);

            //TODO 过滤器设置
            TaskFilterChain filterChain = new TaskFilterChain();
            String[] rules = ruleFilters.split(",");
            for(String rule : rules){
                if(rule.equals(FILTER_DATA))
                    filterChain.addFilter(new DataFilterImpl());
                else if(rule.equals(FILTER_TSDB))
                    filterChain.addFilter(new TSDBFilterImpl());
            }

            startIotRuleEngineJobRunner(filterChain);
        } catch (Exception e) {
            LOGGER.info("Run job failed!", e);
            return new Result(Action.EXECUTE_FAILED, e.getMessage());
        }
        return new Result(Action.EXECUTE_SUCCESS, "执行成功!");
    }

    /**
     * 开启过滤链任务
     * @param filterChain
     */
    public void startIotRuleEngineJobRunner(TaskFilterChain filterChain){
        //获取数据
        String requestData = "{equipmentId:10000,data[" +
                "{temp:'35.6', kw: '52.5'}," +
                "{temp:'42.6', kw: '100.5'}," +
                "]}";
        TaskRequest request = new TaskRequest();
        request.setTenantID(tenantID);
        request.setRequestStr(requestData);

        TaskResponse response = new TaskResponse();
        filterChain.doFilter(request, response, filterChain);
    }
}
