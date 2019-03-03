package com.casic.iot.sql;

import com.alibaba.fastjson.JSONArray;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Sql语句申明
 */
public class Statement {
    /*** sql语句 **/
    private String sql;
    /** Sql 解析器 **/
    private SQLParser sqlParser;

    /**
     * 创建Sql语句
     * @param sql
     */
    public void createStatement(String sql){
        sql = sql.trim().toLowerCase();
        Pattern p = Pattern.compile(SQLParser.selectPattern);
        Matcher m = p.matcher(sql);
        //判断是否符合sql语法
        if(!m.find()) {
            throw new SQLException(sql);
        }
        this.sql = sql;
        sqlParser = new SQLParser();
        sqlParser.parse(sql);
    }

    /**
     * 根据Sql解析数据
     * @param origin 原始数据
     * @return
     */
    public JSONArray execute(JSONArray origin){
        QueryOperation queryOperation = new QueryOperation(sqlParser);
        return queryOperation.execute(origin);
    }

}
