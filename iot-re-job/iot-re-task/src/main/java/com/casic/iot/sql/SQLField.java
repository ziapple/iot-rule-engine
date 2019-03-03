package com.casic.iot.sql;

/**
 * select的查询字段
 * eg. a as metrics,t as timestamp,b as value
 */
public class SQLField {
    /** 原语句 **/
    private String source;

    /** 字段名 **/
    private String name;

    /** 别名 **/
    private String alias;

    /** 函数 **/
    private String func;

    public SQLField(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.alias = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }
}
