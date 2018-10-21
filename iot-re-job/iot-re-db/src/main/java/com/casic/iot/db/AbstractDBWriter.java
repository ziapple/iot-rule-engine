package com.casic.iot.db;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 写入数据库抽象类
 * 已经创建了数据库连接的写入器
 */
public class AbstractDBWriter implements DBWriter{

    /**
     * 默认一个设备一张表
     * @param deviceId 设备名称
     * @param datetime 日期 yyyyMMdd
     * @return
     */
    @Override
    public String getTableName(String deviceId, String datetime) {
        return deviceId;
    }

    /**
     * 判断表是否存在，默认不存在
     * @param tableName
     * @return
     * @throws SQLException
     */
    @Override
    public boolean tableExit(String tableName) throws SQLException {
        return false;
    }

    /**
     * 创建表，该方法必须由子类改写
     * @param tableName
     * @param field
     * @throws SQLException
     */
    @Override
    public void createTable(String tableName, List<String> fields) throws SQLException {

    }

    /**
     *
     * @param content
     * {iot:"100235",equipment:"10245",data:[{power:"1.3",t:11103344},{temp:21.3,t:22455533}]}
     * @throws SQLException
     */
    @Override
    public void write(String content) throws SQLException {
        SerialData sd = parseContent(content);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String tableName = getTableName(sd.getEquipment(), df.format(new Date()));
        if(!tableExit(tableName)){
            createTable(tableName, new ArrayList<>());
        }
        //TODO 解析数据子段映射

        //TODO 生成数据库SQL语句

        //TODO 执行写入操作
    }

    /**
     * 解析Json格式
     * @param content
     * @return
     */
    public SerialData parseContent(String content){
        SerialData sd = new SerialData();
        JSONObject jsonObject = JSON.parseObject(content);
        sd.setIot(jsonObject.getString("iot"));
        sd.setEquipment(jsonObject.getString("equipment"));
        List arr = jsonObject.getJSONArray("data");
        sd.setData(arr);

        return sd;
    }

    /**
     * 时序数据
     */
    public class SerialData{
        private String iot;
        private String equipment;
        private List data;

        public String getIot() {
            return iot;
        }

        public void setIot(String iot) {
            this.iot = iot;
        }

        public String getEquipment() {
            return equipment;
        }

        public void setEquipment(String equipment) {
            this.equipment = equipment;
        }

        public List getData() {
            return data;
        }

        public void setData(List data) {
            this.data = data;
        }
    }
}
