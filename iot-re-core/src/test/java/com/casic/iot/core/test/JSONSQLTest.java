package com.casic.iot.core.test;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.casic.iot.core.sql.SQLCondition;
import com.casic.iot.core.sql.SQLParser;
import com.casic.iot.core.sql.filter.*;
import com.casic.iot.core.sql.filter.SQLITFilter;

public class JSONSQLTest {
    static String jsonStr = "[{tmp:56,kw:120,color:'white'},{tmp:78,kw:46,color:'red'},{tmp:104,kw:234,color:'white'}]";
    static String sql = "select tmp,kw from t where tmp<100 and kw>56 and color!='white'";

    public static void main(String[] args){
        SQLParser sqlParser = new SQLParser(sql);
        sqlParser.parse();

        testWhereJson(sqlParser);
    }

    public static void testSQLParser(){
        SQLParser sqlParser = new SQLParser(sql);
        sqlParser.parse();
        for(SQLCondition sQLCondition: sqlParser.getSqlConditions()){
            System.out.println(sQLCondition);
        }
    }

    public static void testWhereJson(SQLParser sqlParser){
        JSONArray arr = JSON.parseArray(jsonStr);

        SQLRequest request = new SQLRequest(arr);
        SQLFilterChain chain = new SQLFilterChain();
        for(SQLCondition sqlCondition:sqlParser.getSqlConditions()){
            //>过滤
            if(sqlCondition.getJoinCondition().getOperator().equals(SQLCondition.SQL_CONDITION_IT)){
                SQLITFilter sqlFilter = new SQLITFilter();
                sqlFilter.setSQLCondition(sqlCondition);
                chain.addFilter(sqlFilter);
            }else if(sqlCondition.getJoinCondition().getOperator().equals(SQLCondition.SQL_CONDITION_EQ)){
                SQLEQFilter sqlFilter = new SQLEQFilter();
                sqlFilter.setSQLCondition(sqlCondition);
                chain.addFilter(sqlFilter);
            }else if(sqlCondition.getJoinCondition().getOperator().equals(SQLCondition.SQL_CONDITION_ST)){
                SQLSTFilter sqlFilter = new SQLSTFilter();
                sqlFilter.setSQLCondition(sqlCondition);
                chain.addFilter(sqlFilter);
            }else if(sqlCondition.getJoinCondition().getOperator().equals(SQLCondition.SQL_CONDITION_NE)){
                SQLNEFilter sqlFilter = new SQLNEFilter();
                sqlFilter.setSQLCondition(sqlCondition);
                chain.addFilter(sqlFilter);
            }
        }

        chain.doFilter(request, chain);
    }
}
