package com.casic.iot.core.sql;

public class SQLException extends RuntimeException {

    public SQLException() {
        super();
    }

    public SQLException(String s) {
        super(s);
    }

    public SQLException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SQLException(Throwable throwable) {
        super(throwable);
    }
}