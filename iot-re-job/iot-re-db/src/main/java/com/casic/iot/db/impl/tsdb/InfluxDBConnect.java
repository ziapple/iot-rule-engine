package com.casic.iot.db.impl.tsdb;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.Map;

/**
 * 时序数据库 InfluxDB 连接
 * @author Zou Ping
 *
 */
public class InfluxDBConnect {
	
	private String username;//用户名
	private String password;//密码
	private String openurl;//连接地址
	private String database;//数据库
	
	private InfluxDB influxDB;
	private int i = 0;
	private BatchPoints batchPoints;
	private static int batch_size= 100;

	public InfluxDBConnect(String username, String password, String openurl, String database){
		this.username = username;
		this.password = password;
		this.openurl = openurl;
		this.database = database;
	}
	
	/**连接时序数据库；获得InfluxDB**/
	public InfluxDB  influxDbBuild(){
		if(influxDB == null){
			influxDB = InfluxDBFactory.connect(openurl, username, password);
		}
		batchPoints = BatchPoints
				.database(database)
				.consistency(InfluxDB.ConsistencyLevel.ALL)
				.build();
		return influxDB;
	}
	
	/**
	 * 设置数据保存策略
	 * defalut 策略名 /database 数据库名/ 30d 数据保存时限30天/ 1  副本个数为1/ 结尾DEFAULT 表示 设为默认的策略
	 */
	public void createRetentionPolicy(){
		String command = String.format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s DEFAULT",
                "defalut", database, "30d", 1);
		this.query(command);
	}
	
	/**
	 * 查询
	 * @param command 查询语句
	 * @return
	 */
	public QueryResult query(String command){
		return influxDB.query(new Query(command, database));
	}
	
	/**
	 * 插入
	 * @param measurement 表
	 * @param tags 标签
	 * @param fields 字段
	 */
	public void insert(String measurement, Map<String, String> tags, Map<String, Object> fields){
		Point.Builder builder = Point.measurement(measurement);
		builder.tag(tags);
		builder.fields(fields);
		
		influxDB.write(database, "", builder.build());
	}

	/**
	 * 批量插入
	 * @param measurement 表
	 * @param tags 标签
	 * @param fields 字段
	 */
	public void batchInsert(String measurement, Map<String, String> tags, Map<String, Object> fields){
		if(i++ > batch_size){
			influxDB.write(batchPoints);
			i = 0;
		}else{
			Point.Builder builder = Point.measurement(measurement);
			builder.tag(tags);
			builder.fields(fields);
			batchPoints.point(builder.build());
		}
	}
	
	/**
	 * 删除
	 * @param command 删除语句
	 * @return 返回错误信息
	 */
	public String deleteMeasurementData(String command){
		QueryResult result = influxDB.query(new Query(command, database));
		return result.getError();
	}
	
	/**
	 * 创建数据库
	 * @param dbName
	 */
	public void createDB(String dbName){
		influxDB.createDatabase(dbName);
	}
	
	/**
	 * 删除数据库
	 * @param dbName
	 */
	public void deleteDB(String dbName){
		influxDB.deleteDatabase(dbName);
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
 
	public String getOpenurl() {
		return openurl;
	}
 
	public void setOpenurl(String openurl) {
		this.openurl = openurl;
	}
 
	public void setDatabase(String database) {
		this.database = database;
	}
}