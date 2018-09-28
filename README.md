# iot-rule-engine
* 基于任务负载的高可用IOT规则引擎，动态启动、停止任务和传参，实时监控任务状态
* 实现数据过滤、数据分发功能，为了接收不同租户任务调度请求，采用高可用的TaskScheduler来实现。
## 1 基于高可用的TaskScheduler任务调度的框架
### 任务场景
<p text-align="center">
<img src="https://github.com/ziapple/iot-rule-engine/blob/master/iot-docs/3-1.png" alt="任务场景"  width="550" height="240">
</p>

* 租户选择数据规则（选择数据过滤或分发到目的地）Task1
* 点击启动，客户端JobClient向任务调度中心JobTracker发送任务调度Task1
* 调度中心JobTracker接受任务并分配任务给TaskTracker节点，TaskTracker负责执行任务，执行完反馈给JobTacker
* 对于流式任务（数据流），除非人为向JobTrakcer发送关闭命令停止TaskTracker任务，否则TaskTracker会一直执行
###（2）LTS调度框架
选择LTS作为规则引擎的数据调度框架，是因为LTS(light-task-scheduler)用于解决分布式任务调度问题，支持实时任务，定时任务和Cron任务，有较好的伸缩性，扩展性，
健壮稳定性。具体网址：https://gitee.com/hugui/light-task-scheduler

## 2 基于TaskScheduler租户MQTT调度模型
<p text-align="center">
<img src="https://github.com/ziapple/iot-rule-engine/blob/master/iot-docs/3-2.png" alt="规则引擎"  width="550" height="340">
</p>

* 结合实际规则引擎具体任务，任务场景如下：<br>
* 设备数据通过MQTT协议上传至云平台；<br>
* 用户在页面选择MQTT发送地址和过滤规则，封装后调用客户端发送请求；<br>
* 客户端提交任务给JobTracker<br>
* JobTracker分发任务给其中一个节点的TaskTracker<br>
* TaskTracker接受任务后，将任务分配给MQTTJob，MQTTJob负责从MQTT的Topic里面拉取数据；<br>
* 拉取后的数据经过RuleEngineTask规则引擎任务进行处理；<br>
## 3 基于责任链的RuleEngineTask设计模式
  RuleEngineTask是整个规则引擎数据处理的核心类，前面主要负责设备数据如何经过LTB的处理交给不同的节点去处理，最后都是交给每个节点的RuleEngineTask来完成任务，
  每个节点上的RuleEngineTask都一样。
<p text-align="center">
<img src="https://github.com/ziapple/iot-rule-engine/blob/master/iot-docs/3-3.png" alt="规则设计模式"  width="650" height="120">
</p>

(1)RuleEnineTask是一个标准的责任链模式，同样的数据从一条链上穿过，交给不同的数据过滤器去处理；<br>
(2)ShuffleFilter是典型的数据过滤器，对Json格式的数据进行处理，支持类SQL语句，例如：select f1,f2,f3 from t where tmp>50 and kw<100<br>
(3)DBFilter<br>
数据库过滤器，将数据分发到不同的数据库，例如：TSDBFilter，分发给时序数据库，MongoFilter，分发给MongoDB数据库。DBFilter不支持用户自定义转发规则，Json数据会按照设备分表，每个Json字段对应数据库的表字段。一个设备对应一张表，一个Json对应一个表子段。
## 4 项目结构
<p text-align="center">
<img src="https://github.com/ziapple/iot-rule-engine/blob/master/iot-docs/3-4.png" alt="数据过滤链条"  width="300" height="240">
</p>

### iot-re-api
iot-ruleengine项目对外暴露的api，client的所有调用的api操作都在这个工程，相当于LTS的Client。<br>
  接口<br>
* 启动任务api/1.0/Start<br>
* 停止任务api/1.0/Stop<br>
* 重启任务api/1.0/Restart<br>

### iot-re-core
项目核心包，一些SQL过滤器、DB过滤器、工具类会放在这个工程。<br>
### iot-re-task
	相当于LTS的TaskTracker。<br>

## 5	启动工程
（1）	配置lts数据库和zookeeper地址，详见lts文档<br>
（2）	启动Zookeeper<br>
（3）	启动Client，iot-re-api，run IOTReApplication<br>
（4）	启动JobTracker，进入light-task-scheduler/dist/bin目录，./lts-jobtracker.sh lts start<br>
（5）	启动lts-admin，检测lts，dist/bin/lts-admin.sh start<br>
（6）	启动TaskTracker，iot-re-task，run IOTReTaskApplication，<br>
## 6	基于Dubbo的平台Kafka动态数据分发总体架构<br>
如果是从平台的Kafka直接拉取数据，需要从全网设备数据中取筛选租户所需要的数据，数据处理量极大，架构上选择Dubbo分布式调度来处理，<br>
将全网数据负载到不同的节点上进行处理。<br>

<p text-align="center">
<img src="https://github.com/ziapple/iot-rule-engine/blob/master/iot-docs/3-5.png" alt="Dubbo分发模式"  width="550" height="340">
</p>


