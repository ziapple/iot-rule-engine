package com.casic.iot.job;

import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;

public class KafkaJobImpl implements JobRunner {

    @Override
    public Result run(JobContext jobContext) throws Throwable {
        return null;
    }

    /**
     * 开启数据来源为Kafka数据处理任务
     * TODO Kafak任务处理，平台Kafak的过滤任务跟MQTT有很大不同
     * 1. MQTT是一个租户对应一个MQTT实例，每个实例主题不同,Kafka所有租户对应一个Kafak集群，一个主体主题
     * 2. Kafka只有一个固定线程的消费者，动态添加过滤任务不是添加消费者，而是添加数据过滤条件
     * 3. 一个中心节点来接收Kafka数据，分发给不同的处理节点，考虑用Duboo分布式任务架构来完成，此框架不适用
     */
    public void startKafkaJobRunner(){

    }
}
