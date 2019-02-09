package com.casic.iot.job;

import com.casic.iot.core.RuleEngineJobDetail;
import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Map;

/**
 * 租户的MQTT拉取数据任务
 * 给定MQTT的url，topic，username，password，任务会自动拉取数据，分派给@See TaskFilterChain来处理json数据
 */
public class TenantMQTTJobImpl implements JobRunner {

    @Override
    public Result run(JobContext jobContext) throws Throwable {
        String tenantID = jobContext.getJob().getParam(RuleEngineJobDetail.TENANT_ID);
        String jobTask = jobContext.getJob().getParam(RuleEngineJobDetail.JOB_TASK);
        Map<String,String> jobParams = jobContext.getJob().getExtParams();
        String host = jobParams.get(RuleEngineJobDetail.MQ_MQTT_HOST);
        String clientId = RuleEngineJobDetail.MQ_MQTT_CLIENTID;
        int qos = RuleEngineJobDetail.MQ_MQTT_QOS;
        String topic = jobParams.get(RuleEngineJobDetail.MQ_MQTT_TOPIC);
        String userName = jobParams.get(RuleEngineJobDetail.MQ_MQTT_USERNAME);
        String passWord = jobParams.get(RuleEngineJobDetail.MQ_MQTT_PASSWORD);
        try {
            // host为主机名，test为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            MqttClient client = new MqttClient(host, clientId, new MemoryPersistence());
            // MQTT的连接设置
            MqttConnectOptions options = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);
            // 设置连接的用户名
            options.setUserName(userName);
            // 设置连接的密码
            options.setPassword(passWord.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);
            // 设置回调函数
            client.setCallback(new MqttCallback() {

                public void connectionLost(Throwable cause) {
                    System.out.println("connectionLost");
                }

                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    //TODO 开始数据处理任务
                    System.out.println("topic:"+topic);
                    System.out.println("Qos:"+message.getQos());
                    System.out.println("message content:"+new String(message.getPayload()));
                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete---------"+ token.isComplete());
                }

            });
            client.connect(options);
            //订阅消息
            client.subscribe(topic, qos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(Action.EXECUTE_SUCCESS, "任务执行完成！");
    }
}
