package com.casic.iot.core.sql;

/**
 * where条件
 */
public class SQLCondition {
    public static String SQL_JOIN_AND = "and";
    public static String SQL_JOIN_OR = "or";

    public static String SQL_CONDITION_IT = ">";
    public static String SQL_CONDITION_ST = "<";
    public static String SQL_CONDITION_EQ = "=";
    public static String SQL_CONDITION_NE = "!=";

    //and,or
    private String join;

    //tmp>50
    private JoinCondition joinCondition;

    public class JoinCondition{
        private String key;
        private String operator;
        private Object value;

        public JoinCondition(String key,String operator,String value){
            this.key = key;
            this.operator = operator;
            this.value = value;
        }
        public String getKey(){
            return this.key;
        }
        public void setKey(String key){
            this.key = key;
        }
        public String getOperator(){
            return this.operator;
        }
        public void setOperator(String operator){
            this.operator = operator;
        }
        public Object getValue(){
            return this.value;
        }
        public void setValue(Object value){
            this.value = value;
        }

        @Override
        public String toString() {
            return "JoinCondition{" +
                    "key='" + key + '\'' +
                    ", operator='" + operator + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    public String getJoin() {
        return join;
    }

    public void setJoin(String join) {
        this.join = join;
    }

    public JoinCondition getJoinCondition() {
        return joinCondition;
    }

    public void setJoinCondition(String key, String operator, String value) {
        joinCondition = new JoinCondition(key, operator, value);
    }

    @Override
    public String toString() {
        return "SQLCondition{" +
                "join='" + join + '\'' +
                ", joinCondition=" + joinCondition +
                '}';
    }
}