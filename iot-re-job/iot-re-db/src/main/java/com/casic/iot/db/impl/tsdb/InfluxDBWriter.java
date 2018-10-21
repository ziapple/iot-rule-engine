package com.casic.iot.db.impl.tsdb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.casic.iot.db.AbstractDBWriter;
import com.casic.iot.db.DBConf;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * 时序数据库写入器
 * @see https://docs.influxdata.com/influxdb/v1.6/write_protocols/line_protocol_tutorial/
 * eg. INSERT weather,location=us-midwest temperature=82 1465839830100400200
 * ##################################################
 * influxdb相关名词
 * database：数据库；
 * measurement：数据库中的表；
 * points：表里面的一行数据
 * ##################################################
 * Point由时间戳（time）、数据（field）和标签（tags）组成。
 * time：每条数据记录的时间，也是数据库自动生成的主索引；
 * fields：各种记录的值；
 * tags：各种有索引的属性。
 * ##################################################
 */
public class InfluxDBWriter extends AbstractDBWriter {
    private InfluxDBConnect influxDBConnect;

    public InfluxDBConnect getInfluxDBConnect() {
        return influxDBConnect;
    }

    public InfluxDBWriter(DBConf dbConf){
        createInstance(dbConf);
    }

    /**
     * 创建数据库实例
     */
    public void createInstance(DBConf dbConf){
        //创建 连接
        influxDBConnect = new InfluxDBConnect(dbConf.getUsername(), dbConf.getPassword(), dbConf.getOpenUrl(), dbConf.getDatabase());
        influxDBConnect.influxDbBuild();
        influxDBConnect.createRetentionPolicy();
    }

    /**
     * 写入数据
     * @param content
     * {measurement:10024,tags:{tag_1:tag_1_value,tag_2:tag_2_value},fields:{f_1:f_1_value,f_2:f_2_value}}
     */
    public void write(String content){
        Serial sl = parseJson(content);
        influxDBConnect.insert(sl.getMeasurement(), sl.getTags(), sl.getFields());
    }

    /**
     * 解析Json格式
     * {measurement:10024,tags:{tag_1:tag_1_value,tag_2:tag_2_value},fields:{f_1:f_1_value,f_2:f_2_value}}
     * @return Serial
     */
    public Serial parseJson(String json){
        JSONObject obj = JSON.parseObject(json);
        Serial sl = new Serial();

        Assert.notNull(obj.getString("measurement"),"measurement为空");
        sl.setMeasurement(obj.getString("measurement"));

        Map<String,Object> tagsObj = obj.getJSONObject("tags");
        Assert.notEmpty(tagsObj,"tags不能为空");
        Map<String,String> tags = new HashMap<>();
        for(Map.Entry<String,Object> entry : tagsObj.entrySet()){
            tags.put(entry.getKey(), entry.getValue().toString());
        }
        sl.setTags(tags);

        Map<String,Object> fields = obj.getJSONObject("fields");
        Assert.notEmpty(fields, "fields不能为空");
        sl.setFields(fields);
        return sl;
    }

    public class Serial{
        String measurement;
        Map<String, String> tags;
        Map<String, Object> fields;

        public String getMeasurement() {
            return measurement;
        }

        public void setMeasurement(String measurement) {
            this.measurement = measurement;
        }

        public Map<String, String> getTags() {
            return tags;
        }

        public void setTags(Map<String, String> tags) {
            this.tags = tags;
        }

        public Map<String, Object> getFields() {
            return fields;
        }

        public void setFields(Map<String, Object> fields) {
            this.fields = fields;
        }
    }
}
