package com.casic.iot.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 从用户那将任务转化成RuleEngineJob
 * 规则引擎的全部参数设置
 */
public class RuleEngineJobDetail {
    //租户ID，必填
    public static final String TENANT_ID = "TENANT_ID";

    //===============================数据来源的配置参数===============================//
    //数据来源标识，只能有一个数据来源，JOB_MQ = "MQ_KAFKA|MQ_MQTT"
    public static final String JOB_MQ = "JOB_MQ";

    //平台KAFKA标识,数据来自平台Kafka，二选一
    public static final String MQ_KAFKA = "MQ_KAFKA";
    //租户MQTT标识,数据来自租户的MQTT，二选一
    public static final String MQ_MQTT = "MQ_MQTT";

    //平台Kafka的配置信息
    public static final String MQ_KAFKA_ZKCONN = "MQ_KAFKA_ZK_CONN";
    public static final String MQ_KAFKA_TOPIC = "MQ_KAFKA_TOPIC";

    //租户MQTT的配置信息
    public static final String MQ_MQTT_HOST = "MQ_MQTT_HOST";
    public static final String MQ_MQTT_TOPIC = "MQ_MQTT_TOPIC";
    public static final String MQ_MQTT_CLIENTID = "MQ_MQTT_CLIENTID";
    public static final int MQ_MQTT_QOS = 1;
    public static final String MQ_MQTT_USERNAME = "MQ_MQTT_USERNAME";
    public static final String MQ_MQTT_PASSWORD = "MQ_MQTT_PASSWORD";

    //===============================数据处理任务的配置参数===========================//
    //数据处理任务标识，可以有多个数据处理任务，JOB_RULE = "TASK_DATA,TASK_TSDB,TASK_KAFKA"
    public static final String JOB_TASK = "JOB_TASK";

    //数据过滤任务标识，可选
    public static final String TASK_DATA = "TASK_DATA";
    //过滤规则，采用类SQL方式，"temp>50 and kw<100"
    public static final String DATA_FILTER = "DATA_FILTER";

    //TDSB任务标识，可选
    public static final String TASK_TSDB = "TASK_TSDB";
    //TSDB配置参数
    public static final String TSDB_CONN = "TSDB_HOST";
    public static final String TSDB_USERNAME = "TSDB_USERNAME";
    public static final String TSDB_PASSWORD = "TSDB_PASSWORD";

    //Kafka任务标识，可选
    public static final String TASK_KAFKA = "TASK_KAFKA";
    //Kafka配置参数
    public static final String KAFKA_ZKCONN = "KAFKA_ZKCONN";

    //租户ID
    private String tenantID;

    //数据来源标识
    private String jobMQ;

    //数据处理任务标识
    private String jobTask;

    //mqtt,datafilter,tsdb,kafka等参数配置
    private Map<String, String> params = new HashMap<>();


    public String getTenantID() {
        return tenantID;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public String getJobMQ() {
        return jobMQ;
    }

    public void setJobMQ(String jobMQ) {
        this.jobMQ = jobMQ;
    }

    public String getJobTask() {
        return jobTask;
    }

    public void setJobTask(String jobTask) {
        this.jobTask = jobTask;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "RuleEngineJobDetail{" +
                "tenantID='" + tenantID + '\'' +
                ", jobMQ='" + jobMQ + '\'' +
                ", jobTask='" + jobTask + '\'' +
                ", params=" + params +
                '}';
    }
}
