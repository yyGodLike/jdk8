package com.lhy.jdk8;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class DataSourceFactory {

	private ConcurrentHashMap<String, DataSourcePool> pools = new ConcurrentHashMap<>();

	// 查询数据源
	public DataSourcePool get(String project_name) throws SQLException {
		DataSourcePool dataSourcePool = null;

		if (pools.get(project_name) == null) {
			synchronized (project_name) {
				if (pools.get(project_name) == null) {
					String s_username = "root";
					String s_pwd = "admin123";
					String dbcUrl = "jdbc:mysql://127.0.0.1:3306/"
							+ project_name
							+ "?useUnicode=true&amp;characterEncoding=utf-8";
					dataSourcePool = new DataSourcePool(s_username, s_pwd,
							dbcUrl, project_name);
					pools.put(project_name, dataSourcePool);
				}
			}
		}
		return pools.get(project_name);
	}

	// 添加数据源
	public void add(String project_name, DataSourcePool pool) {
		this.pools.put(project_name, pool);
	}

	// 移除指定数据源
	public void remove(String project_name) {
		this.pools.remove(project_name);
	}
}