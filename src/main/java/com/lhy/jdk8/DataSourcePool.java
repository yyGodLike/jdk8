package com.lhy.jdk8;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourcePool {

    private ComboPooledDataSource cpds;

    private String user;
    private String password;
    private String jdbcUrl;
    private String unique_name;

    public DataSourcePool(String user, String password, String jdbcUrl, String unique_name) {
        this.user = user;//用户名
        this.password = password;//密码
        this.jdbcUrl = jdbcUrl;//"jdbc:mysql://10.6.123.43:3306/testdb";
        this.unique_name = unique_name; //系统唯一标识
        this.initDataSource();
    }

    // 配置数据源
    public void initDataSource() {
        cpds = new ComboPooledDataSource();
        cpds.setDataSourceName(unique_name);
        cpds.setJdbcUrl(jdbcUrl);//连接url
        try {
            cpds.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        } 
        //数据库驱动
        cpds.setUser(user);                  // 用户名
        cpds.setPassword(password);          // 密码
        cpds.setMaxPoolSize(50);            // 连接池中保留的最大连接数
        cpds.setMinPoolSize(5);              // 连接池中保留的最小连接数
        cpds.setAcquireIncrement(10);        // 一次性创建新连接的数目
        cpds.setInitialPoolSize(15);          // 初始创建
        cpds.setMaxIdleTime(12000);           // 最大空闲时间
        cpds.setBreakAfterAcquireFailure(false); //如果想让数据库和网络故障恢复之后，pool能继续请求正常资源必须把此项配置设为false
        cpds.setAcquireRetryAttempts(30);//pool请求取连接失败后重试的次数和重试的频率
        cpds.setAcquireRetryDelay(100);
        cpds.setPreferredTestQuery("SELECT 1");//采用 DatabaseMetaData.getTables()来测试connection的有效性
        cpds.setIdleConnectionTestPeriod(10);//不进行检测pool内的连接是否正常，
        //log.info("initDataSource_user,{}",user);
        //log.info("initDataSource_password,{}",password);
        //log.info("initDataSource_jdbcUrl,{}",jdbcUrl);
        //log.info("initDataSource_unique_name,{}",unique_name);
    }

    // 从连接池中获得连接对象
    public Connection getConnection() {
        try {
            return cpds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws SQLException {
//        String user = "root";//用户名
//        String password = "123456";//密码
//        String jdbcUrl = "jdbc:mysql://10.6.123.43:3306/testdb";
//        String unique_name = "testdb"; //系统唯一标识
//
//        cpds = new ComboPooledDataSource();
//        cpds.setDataSourceName(unique_name);
//        cpds.setJdbcUrl(jdbcUrl);//连接url
//        try {
//            cpds.setDriverClass("com.mysql.jdbc.Driver");
//        } catch (PropertyVetoException e) {
//            e.printStackTrace();
//            log.error("DataSourcePool_initDataSource()异常:{}", e);
//        } //数据库驱动
//
//        cpds.setUser(user);                         // 用户名
//        cpds.setPassword(password);                 // 密码
//        cpds.setMaxPoolSize(30);                    // 连接池中保留的最大连接数
//        cpds.setMinPoolSize(1);                     // 连接池中保留的最小连接数
//        cpds.setAcquireIncrement(10);               // 一次性创建新连接的数目
//        cpds.setInitialPoolSize(1);                 // 初始创建
//        cpds.setMaxIdleTime(6000);                  // 最大空闲时间
//        cpds.setBreakAfterAcquireFailure(false);
//        cpds.setAcquireRetryAttempts(30);
//        cpds.setAcquireRetryDelay(100);
//
//        for (int i = 0; i < 1000000; i++) {
//            Connection connection = cpds.getConnection();
//            System.out.println(cpds.getMaxIdleTime());// 最大连接数
//            System.out.println(cpds.getMinPoolSize());// 最小连接数
//            System.out.println(cpds.getNumBusyConnections());// 正在使用连接数
//            System.out.println(cpds.getNumIdleConnections());// 空闲连接数
//            System.out.println(cpds.getNumConnections());// 总连接数
//            connection.close();
//        }
    }
}