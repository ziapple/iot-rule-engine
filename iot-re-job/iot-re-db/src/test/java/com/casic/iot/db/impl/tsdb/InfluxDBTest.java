package com.casic.iot.db.impl.tsdb;

import com.casic.iot.db.DBConf;
import org.influxdb.dto.QueryResult;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class InfluxDBTest {

    private InfluxDBWriter influxDB;
    private String username = "admin";//用户名
    private String password = "admin";//密码
    private String openurl = "http://hdp0:8083";//连接地址
    private String database = "test_db";//数据库
    private String measurement = "sys_code";

    @Before
    public void setUp() {
        //创建 连接
        DBConf dbConf = new DBConf(database, openurl, username, password);
        influxDB = new InfluxDBWriter(dbConf);
    }

    @Test
    public void testInsert() throws InterruptedException {//测试数据插入
        while (true) {
            Thread.sleep(1000);
            String json = "{measurement:\"iot_10024\"," +
                    "\"tags\":{\"tag_1\":\"tag_1_value\",\"tag_2\":\"tag_2_value\"}," +
                    "\"fields\":{\"f_1\":\"f_1_value\",\"f_2:f_2_value\"}}";
            influxDB.write(json);
        }
    }


    public void testQuery() {//测试数据查询
        String command = "select * from sys_code";
        QueryResult results = influxDB.getInfluxDBConnect().query(command);

        if (results.getResults() == null) {
            return;
        }
        for (QueryResult.Result result : results.getResults()) {

            List<QueryResult.Series> series = result.getSeries();
            for (QueryResult.Series serie : series) {
//				Map<String, String> tags = serie.getTags();
                List<List<Object>> values = serie.getValues();
                List<String> columns = serie.getColumns();
            }
        }
    }


    public void testQueryWhere() {//tag 列名 区分大小写
        String command = "select * from sys_code where TAG_CODE='ABC'";
        QueryResult results = influxDB.getInfluxDBConnect().query(command);

        if (results.getResults() == null) {
            return;
        }
        for (QueryResult.Result result : results.getResults()) {

            List<QueryResult.Series> series = result.getSeries();
            for (QueryResult.Series serie : series) {
                List<List<Object>> values = serie.getValues();
                List<String> columns = serie.getColumns();
            }
        }
    }
}