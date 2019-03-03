package com.casic.iot.sql;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Sql语法分块解析,目前只支持select和where
 */
public class SQLParser {
    /** select 正则表达式 **/
    public static String selectPattern = "(select)(.+)(where)";
    /** alias 正则表达式 **/
    private static String aliasPattern = "(.+)as(.+)";
    /** where 正则表达式 **/
    private static String wherePattern = "(where)(.+)";
    /**
     * func 正则表达式
     * eg. substr(name,0,1),index(name,'temp'),lowercase(name),uppercase(name)
     */
    private static String funcPattern = "(substr|index|lowercase|uppercase)(.+)";

    /** select 语句 **/
    private String select;

    /** select解析后的field字段 **/
    private List<SQLField> fields;

    /** where 语句 **/
    private String where;

    /**
     * 解析sql全句
     * @param sql
     */
    public void parse(String sql){
        this.fields = new ArrayList<>();
        this.fields = parseSelect(sql);
        this.where = parseWhere(sql);
    }

    /**
     * 解析select语句
     * @param sql
     * @return
     */
    public List<SQLField> parseSelect(String sql){
        Pattern p = Pattern.compile(selectPattern);
        Matcher m = p.matcher(sql);
        if(m.find()) {
            // 获取匹配body
            this.select = m.group(2);
            // 解析field
            String[] pieces = this.select.split(",");
            for(String piece : pieces){
                piece = piece.trim();
                this.fields.add(parseField(piece));
            }
        }
        return fields;
    }

    /**
     * 解析field字段
     * @param piece
     * @return
     */
    public SQLField parseField(String piece){
        SQLField field = new SQLField(piece);
        //判断as
        Pattern p = Pattern.compile(aliasPattern);
        Matcher m = p.matcher(piece);
        if(m.find()) {
            parseFunc(field, m.group(1));
            field.setAlias(m.group(2));
        }else{
            parseFunc(field, piece);
        }
        return field;
    }

    /**
     * 解析函数
     * @param func
     * @return
     */
    public void parseFunc(SQLField field, String func){
        Pattern p = Pattern.compile(funcPattern);
        Matcher m = p.matcher(func);
        if(m.find()) {
            field.setFunc(m.group(1));
            field.setName(m.group(2));
        }else{
            field.setName(func);
        }
    }

    /**
     * 解析where语句
     * @param sql
     * @return
     */
    public String parseWhere(String sql){
        //判断as
        Pattern p = Pattern.compile(wherePattern);
        Matcher m = p.matcher(sql);
        if(m.find()) {
            return m.group(2);
        }
        return "";
    }

    public String getSelect() {
        return select;
    }

    public String getWhere() {
        return where;
    }

    public List<SQLField> getFields() {
        return fields;
    }
}
