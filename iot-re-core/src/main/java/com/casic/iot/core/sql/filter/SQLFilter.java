package com.casic.iot.core.sql.filter;

/**
 * @author zouping on 2018.08.31
 */
public interface SQLFilter {
	public void doFilter(SQLRequest request, SQLFilterChain chain);
}
