package com.lhy.jdk8;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 测试数据库连接工厂
 * 
 * @author admin
 *
 */
public class TestDateSourceFactory {

	public static void main(String[] args) throws SQLException {

		DataSourceFactory dataSourceFactory = new DataSourceFactory();
		
		ResultSet rs2 = new DBUtils(dataSourceFactory.get("uat")
				.getConnection()).executeQuery("select id as carId,name as carName from car", null);
		while (rs2.next()) {
			System.out.println(rs2.getString("carId") + " "
					+ rs2.getString("carName"));
		}

		/*ResultSet rs1 = new DBUtils(dataSourceFactory.get("test")
				.getConnection()).executeQuery("select * from t", null);
		while (rs1.next()) {
			System.out.println(rs1.getString("a") + " " + rs1.getString("b"));
		}
		

		ResultSet rs3 = new DBUtils(dataSourceFactory.get("uat")
				.getConnection()).executeQuery("select * from car", null);
		while (rs3.next()) {
			System.out.println(rs3.getString("id") + " "
					+ rs3.getString("name"));
		}*/
		
	}

}
