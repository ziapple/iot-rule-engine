package com.casic.iot.sql;

public class SQLException extends RuntimeException {

    public SQLException() {
        super("Sql语句解析错误");
    }

    public SQLException(String s) {
        super( "Sql解析错误:" + s);
    }

    public SQLException(String s, Throwable throwable) {
        super("Sql解析错误:" + s, throwable);
    }

    public SQLException(Throwable throwable) {
        super(throwable);
    }
}