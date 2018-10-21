package com.casic.iot.db;

import com.casic.iot.db.impl.mysql.MySQLWriter;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据库写入器工厂
 * @author zouping 2018.10.04
 */
public class DBWriterFactory {
    //数据库类型
    private String dbType;
    //MYSQL数据库类型
    public final static String DB_MYSQL="MYSQL";
    //数据库实例映射表Map<InstanceID,DBWriter>
    private static Map<String, DBWriter> dbWriterMap = new HashMap<>();

    public DBWriterFactory(String dbType){
        this.dbType = dbType;
    }

    /**
     * 获取一个DBWriter=DBInstance(Tenant,DBTYPE,DBNAME)
     * 如果实例不存在则创建一个数据库连接
     * @param instanceId 实例ID
     * @param dbConf 数据库连接配置
     * @return DBWriter 数据库写入器
     */
    public DBWriter getDBWriter(String instanceId, DBConf dbConf){
        DBWriter dbWriter = null;
        if(!dbWriterMap.containsKey(instanceId)){
            if(dbType.equals(DBWriterFactory.DB_MYSQL)){//mysql
                dbWriter = new MySQLWriter(dbConf);
            }

            dbWriterMap.put(instanceId, dbWriter);
        }

        return dbWriterMap.get(instanceId);
    }
}
