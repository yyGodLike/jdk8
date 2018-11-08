package com.lhy.jdk8;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestConn {
	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		String URL = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&amp;characterEncoding=utf-8";
		String USER = "root";
		String PASSWORD = "admin123";
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
		
		System.out.println(conn.toString());
		
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from t");
		while (rs.next()) {
			System.out.println(rs.getString("a") + " "
					+ rs.getString("b"));
		}
		// 关闭资源
		rs.close();
		st.close();
		conn.close();
	}
}
