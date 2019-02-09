package com.casic.iot.core.sql.filter;

import com.casic.iot.core.sql.SQLCondition;

/**
 * zouping,2018.09.01
 */
public class AbstractSQLFilter implements SQLFilter {
    private SQLCondition sqlCondition;

    @Override
    public void doFilter(SQLRequest request, SQLFilterChain chain) {

    }

    public SQLCondition getSQLCondition() {
        return sqlCondition;
    }

    public void setSQLCondition(SQLCondition sqlCondition) {
        this.sqlCondition = sqlCondition;
    }
}
