package com.lhy.jdk8;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.rowset.CachedRowSetImpl;

/**
 * DBUtils
 */
public class DBUtils {
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private PreparedStatement ps;

    public DBUtils(Connection connection) {
        this.conn = connection;
    }

    /**
     * 执行查询操作
     *
     * @param sql 数据库操作语句
     * @return 返回一个ResultSet类型的对象
     */
    public ResultSet executeQuery(String sql, String[] parameters) throws SQLException {
        CachedRowSetImpl rowset = new CachedRowSetImpl();
        try {
           /* DatabaseMetaData metaData = conn.getMetaData();
            String driverName = metaData.getDriverName();
            System.out.println("executeQuery,获取连接的sql,conn,driverName信息:"+sql+";"+conn+";"+driverName);*/

            ps = conn.prepareStatement(sql);


            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    ps.setString(i + 1, parameters[i]);
                }
            }
            rs = ps.executeQuery();
            rowset.populate(rs);
        } catch (Exception e) {
            throw e;
        } finally {
        	conn.close();
        }

        return rowset;
    }

    /**
     * 修改或者删除数据库操作
     *
     * @param sql 要执行的数据库操作语句
     * @return 返回-1为不成功
     */
    public int executeUpdate(String sql) throws SQLException {
        int ret = -1;
        try {
           /* DatabaseMetaData metaData = conn.getMetaData();
            String driverName = metaData.getDriverName();
            System.out.println("executeQuery,获取连接的sql,conn,driverName信息:"+sql+";"+conn+";"+driverName);*/

            stmt = conn.createStatement();
            ret = stmt.executeUpdate(sql);
        } catch (Exception e) {
            throw e;

        } finally {
            this.close();
        }
        return ret;
    }

    /**
     * 关闭数据库
     */
    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}