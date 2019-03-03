package com.casic.iot.sql;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * where过滤操作
 * eg. (a>10 and b<20) or (a>30 or b<40)
 */
public class WhereOperation {
    private String where;

    public WhereOperation(String where){
        this.where = where;
    }

    /**
     * 根据where条件返回该条记录是否符合要求
     * @param row 单条记录
     * @return
     */
    public boolean execute(JSONObject row){
        return true;
    }
}
