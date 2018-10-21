package com.casic.iot;

import com.casic.iot.db.DBConf;
import com.casic.iot.db.DBWriter;
import com.casic.iot.db.DBWriterFactory;

import java.sql.SQLException;

/**
 * 数据库写入测试类
 * 相当于client
 */
public class DBWriterTest {
	public static void main(String[] args) {

	}

	/**
	 * 测试mysql写入
	 */
	public void testMySQLWriter() throws SQLException {
		String requestData = "{equipmentId:10000,data:[" +
				"{temp:'35.6', kw: '52.5'}," +
				"{temp:'42.6', kw: '100.5'}" +
				"]}";
		String url = "jdbc://localhost:3306/test";
		String username = "root";
		String password = "123456";

		DBConf dbConf = new DBConf();
		dbConf.setOpenUrl(url);
		dbConf.setUsername(username);
		dbConf.setPassword(password);
		//获取数据库实例
		DBWriterFactory dbWriterFactory = new DBWriterFactory(DBWriterFactory.DB_MYSQL);
		DBWriter dbWriter = dbWriterFactory.getDBWriter("1", dbConf);
		//写入数据
		dbWriter.write(requestData);
	}
}
