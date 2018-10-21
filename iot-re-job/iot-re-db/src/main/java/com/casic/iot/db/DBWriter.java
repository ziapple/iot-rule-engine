package com.casic.iot.db;

import java.sql.SQLException;
import java.util.List;

/**
 * 写入数据库接口，子类需要自己创建数据库连接实例，最核心的方法是write
 */
public interface DBWriter {
    /**
     * 表生成规则
     * @param deviceId 设备名称
     * @param datetime 日期 yyyyMMdd
     * @return
     */
    String getTableName(String deviceId, String datetime);

    /**
     * 判断表是否存在
     * @param tableName
     */
    boolean tableExit(String tableName) throws SQLException;

    /**
     * 创建表
     * @param tableName 表名
     * @param fields 数据字段
     */
    void createTable(String tableName, List<String> fields) throws SQLException;

    /**
     * 写入表数据
     * @param content json字符串
     */
    void write(String content) throws SQLException;
}
