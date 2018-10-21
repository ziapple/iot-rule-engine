package com.casic.iot.db.impl.mysql;

import com.casic.iot.db.AbstractDBWriter;
import com.casic.iot.db.DBConf;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.List;

/**
 * MySQl写入器，用于测试，生产环境不会用到mysql数据库
 */
public class MySQLWriter extends AbstractDBWriter {
    //数据库连接池
    private ComboPooledDataSource comboPooledDataSource;

    /**
     * 初始化写入器
     * @param dbConf
     */
    public MySQLWriter(DBConf dbConf){
        createInstance(dbConf);
    }

    /**
     * 创建数据库实例
     */
    public void createInstance(DBConf dbConf){
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            comboPooledDataSource = new ComboPooledDataSource();
            comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
            comboPooledDataSource.setJdbcUrl(dbConf.getOpenUrl());
            comboPooledDataSource.setUser(dbConf.getUsername());
            comboPooledDataSource.setPassword(dbConf.getPassword());
            //下面是设置连接池的一配置
            comboPooledDataSource.setMaxPoolSize(20);
            comboPooledDataSource.setMinPoolSize(5);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean tableExit(String tableName) throws SQLException {
        String sql = "SELECT table_name FROM information_schema.TABLES WHERE table_name =' " + tableName + "'";
        comboPooledDataSource.getConnection()
                .createStatement()
                .executeUpdate(sql);
        return true;
    }

    /**
     * 创建表，默认主键为ID，字段为字符VARCHAR(200)
     * 一个设备一张表
     * create table iot_1(
     *    iot_p_id INT NOT NULL AUTO_INCREMENT,
     *    param_temp VARCHAR(100),
     *    param_kw VARCHAR(100),
     *    PRIMARY KEY ( iot_p_id )
     * );
     * @param tableName
     * @param fields 字段
     */
    @Override
    public void createTable(String tableName, List<String> fields) throws SQLException{
        String sql = "create table " + tableName + "(iot_p_id INT NOT NULL AUTO_INCREMENT," ;
        for(String field : fields){
            sql += field + " VARCHAR(200),";
        }
        sql += "PRIMARY KEY ( iot_p_id )";
        comboPooledDataSource.getConnection()
                .createStatement()
                .executeUpdate(sql);

    }

    @Override
    public void write(String content) throws SQLException {
        //TODO 生成数据库SQL语句
        String sql = "";
        //TODO 执行写入操作
        comboPooledDataSource.getConnection()
                .createStatement()
                .executeUpdate(sql);
    }


}
