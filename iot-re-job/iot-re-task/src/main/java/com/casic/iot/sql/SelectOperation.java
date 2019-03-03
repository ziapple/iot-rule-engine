package com.casic.iot.sql;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import java.util.List;

/**
 * select过滤操作
 */
public class SelectOperation {
    private List<SQLField> fields;

    public SelectOperation(List<SQLField> fields){
        this.fields = fields;
    }

    /**
     * 根据where条件返回该条记录是否符合要求
     * @param row 单条记录
     * @return
     */
    public JSONObject execute(JSONObject row){
        return row;
    }
}
