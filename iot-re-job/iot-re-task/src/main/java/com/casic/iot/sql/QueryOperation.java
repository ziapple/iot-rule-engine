package com.casic.iot.sql;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Query查询操作,目前只支持select和where
 * eg. select a,b,c where a>10 and b<30
 */
public class QueryOperation {
    /** Sql 语法分块 **/
    private SQLParser sqlParser;

    public QueryOperation(SQLParser sqlParser) {
        this.sqlParser = sqlParser;
    }

    /**
     * 按Sql过滤数据
     * @param rows 原始数据
     * @return
     */
    public JSONArray execute(JSONArray rows){
        WhereOperation whereOperation = new WhereOperation(sqlParser.getWhere());
        SelectOperation selectOperation = new SelectOperation(sqlParser.getFields());
        JSONArray result = new JSONArray();
        for(int i=0; i<rows.size(); i++){
            JSONObject row = rows.getJSONObject(i);
            //先查询满足条件的记录
            if(whereOperation.execute(row)){
                //筛选field字段
                result.add(selectOperation.execute(row));
            }
        }
        return result;
    }
}
