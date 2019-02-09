package com.casic.iot.core.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SQL解析器，解析select,where等条件，目前只支持小写，只支持where条件过滤
 * zouping on 2018.08.31
 */
public class SQLParser {
    private String sql;
    private static String wherePattern = "where.*['group','order']?";
    private static String filterPattern = "(and|or)\\s+(\\w+)\\s*(<|>|=|!=)\\s*(\\S+)";
    private List<SQLCondition> sqlConditions = new ArrayList<>();

    public SQLParser(String sql){
        this.sql = sql;
    }

    public List<SQLCondition> getSqlConditions() {
        return sqlConditions;
    }

    public void setSqlConditions(List<SQLCondition> getSqlConditions) {
        this.sqlConditions = getSqlConditions;
    }

    public void parse(){
        //TODO 解析select字段

        //解析where条件
        parseSQLFilter();
    }

    /**
     * 解析where条件
     */
    private void parseSQLFilter(){
        Pattern p = Pattern.compile(wherePattern);
        Matcher m = p.matcher(sql);
        if(m.find()){
            String where = m.group();
            where = where.substring(where.indexOf("where") + 5);
            //最前面强制加上and字符
            where = "and" + where;
            p = Pattern.compile(filterPattern);
            m = p.matcher(where);
            while(m.find()){
                SQLCondition sqlCondition = new  SQLCondition();
                sqlCondition.setJoin(m.group(1));
                sqlCondition.setJoinCondition(m.group(2), m.group(3), m.group(4));
                sqlConditions.add(sqlCondition);
            }
        }
    }
}
