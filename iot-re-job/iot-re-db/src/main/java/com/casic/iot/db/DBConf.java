package com.casic.iot.db;

/**
 * 数据库配置
 */
public class DBConf {
    private String database;
    private String openUrl;
    private String username;
    private String password;

    public DBConf(){

    }

    public DBConf(String database, String openUrl, String username, String password) {
        this.database = database;
        this.openUrl = openUrl;
        this.username = username;
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getOpenUrl() {
        return openUrl;
    }

    public void setOpenUrl(String openUrl) {
        this.openUrl = openUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}