package com.casic.iot.core.sql.filter;

import com.alibaba.fastjson.JSONArray;
import com.casic.iot.core.sql.SQLCondition;

public class SQLRequest {
    //原始数据
    JSONArray origin;
    //目标数据
    JSONArray target;

    public SQLRequest(JSONArray origin) {
        this.origin = origin;
        this.target = origin;
    }

    public JSONArray getOrigin() {
        return origin;
    }

    public void setOrigin(JSONArray origin) {
        this.origin = origin;
    }

    public JSONArray getTarget() {
        return target;
    }

    public void setTarget(JSONArray target) {
        this.target = target;
    }
}
