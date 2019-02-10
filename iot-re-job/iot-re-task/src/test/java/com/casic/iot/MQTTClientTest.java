package com.casic.iot;

import com.casic.iot.task.TaskFilterChain;
import com.casic.iot.task.TaskRequest;
import com.casic.iot.task.TaskResponse;
import com.casic.iot.task.impl.DataFilterTaskImpl;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * 测试MQTT连接
 */
public class MQTTClientTest {
	static String host = "tcp://106.13.63.214:1883";
	static String clientId = "10000";
    static String userName = "admin";
    static String passWord = "public";
    static String topic = "mqtt/test";
    static int qos = 0;

	public static void main(String[] args) {
		testMQTT();
	}

	public static void testMQTT(){
		try {
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
            options.setKeepAliveInterval(1);
            // 设置回调函数
            client.setCallback(new MqttCallback() {

                public void connectionLost(Throwable cause) {
                    System.out.println("connectionLost");
                }

                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    //TODO 开始数据处理任务
                    System.out.println("topic:"+topic + "message content:"+new String(message.getPayload()));
                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete---------"+ token.isComplete());
                }

            });
            client.connect(options);
            //订阅消息
            client.subscribe(topic, qos);
        }catch (MqttException e){
		    e.printStackTrace();
        }
	}
}
