package com.casic.iot.task.impl;

import com.casic.iot.core.sql.SQLParser;
import com.casic.iot.task.TaskFilter;
import com.casic.iot.task.TaskFilterChain;
import com.casic.iot.task.TaskRequest;
import com.casic.iot.task.TaskResponse;

/**
 * task实现类，第一步json数据过滤器，实现基于SQL语句的数据过滤，转换
 * @author zouping on 2018.08.24
 */
public class DataFilterTaskImpl implements TaskFilter {
	static String sql = "select tmp,kw from t where tmp<100 and kw>56 and color!='white'";

	@Override
	public void doFilter(TaskRequest request, TaskResponse response, TaskFilterChain chain) {
		String json = response.getResponseStr()==null?request.getRequestStr():response.getResponseStr();
		String convertedStr = parseJson(json);
		response.setResponseStr(convertedStr);
		//执行下一个过滤器
		chain.doFilter(request, response, chain);
	}

	/**
     * 常用语法
	 * 输入消息	SELECT子句	结果消息	备注
     * {"a":"b", "c":7}	*	{"a":"b", "c":7}	选择所有的字段
     * {"a":"b", "c":7}	c	{"c":7}	选择一个字段
     * {"c": { "d": 9 }}	c.d	{"c": {"d": 9}}	选择嵌套字段
     * {"a":"b", "c":7}	a AS a1	{"a1":"b"}	重命名一个字段
     * {"c": { "d": 9 }}	c.d AS e.f	{"e": { "f": 9 }}	选择嵌套字段，并且存入另外一个嵌套字段
     * {}	'abc' AS d	{"d":"abc"}	插入一个字符常量字段
     * {}	'abc' AS d.e	{"d": { "e": "abc" }}	插入一个字符常量到一个嵌套字段
     * {"a":"b", "c":7}	* AS d	{"d":{"a":"b", "c":7}}	将所有字段嵌套到名为d的对象下面
     *
     * 常用函数
     * 下面是常用函数，及其用途和示例
     *
     * 函数	用途	示例
     * CURRENT_TIMESTAMP	取系统当前时间	CURRENT_TIMESTAMP
     * extract	提取时间戳各字段	extract(epoch FROM CURRENT_TIMESTAMP)
     * substring	取字符串子串	substring(str, 1, 10)
     * position	取子串在字符串中的位置	position('ab' IN '111ab')
     * CHAR_LENGTH	求字符串长度	CHAR_LENGTH(deviceid)
     * ||	字符串串联	'devices/' || deviceid
     * abs	求绝对值	abs(data)
     * floor	向下取整	floor(temperature)
     * ceil	向上取整	ceil(temperature)
     * power	乘幂	power(a, 2)
     * exp	对e取幂	exp(3)
     * sqrt	求平方根	sqrt(a)
     * mod	取模	mod(10, 8)
	 * @param json
	 * @return
	 */
	public String parseJson(String json){
		//用策略模式来实现，一个算法一个类，TODO
		SQLParser sqlParser = new SQLParser(sql);
		sqlParser.parse();

		return json;
	}
}