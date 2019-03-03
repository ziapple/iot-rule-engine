package com.casic.iot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.casic.iot.sql.Statement;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SQLStatementTest {
    @Test
    public void testSql(){
        String sql = "select a,b,c where a>10 and b<30";
        JSONArray origin = (JSONArray) JSON.parse("[{a:10,b:20},{a:30,b:50}]");
        Statement statement = new Statement();
        statement.createStatement(sql);
        JSONArray result = statement.execute(origin);
        System.out.println(result);
    }

}
