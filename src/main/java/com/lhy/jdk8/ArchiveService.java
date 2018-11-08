/*package com.system.db.service;

import com.system.db.db.DBUtils;
import com.system.db.db.DataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

*//**
 * 归档任务
 *//*
@Service
@Slf4j
public class ArchiveService {

    @Autowired
    @Qualifier("dataSource_t")
    private DataSource dataSource_t;

    @Autowired
    private DataSourceFactory dataSourceFactory;

    // 通过开始时间和结束时间，获取归档任务
    public ResultSet taskListByTime() throws SQLException {
        String sql = "select * from t_archive where recursion_status='1' order by id";
        //log.info("ArchiveService_taskListByTime,{}",sql);
        return new DBUtils(dataSource_t.getConnection()).executeQuery(sql, null);
    }

    // 需要归档任务的最大值、最小值
    public ResultSet taskList(List<String> list1) throws SQLException {
        //获取需要归档表主键的最大值最小值
        String sql = "select min(" + list1.get(0) + ") as min_id,max(" + list1.get(0) + ") as max_id from " + list1.get(5)
                + "." + list1.get(1) + " where " + list1.get(2) + ">'" + list1.get(3) + "' and "
                + list1.get(2) + "<='" + list1.get(4) + "'";
        String project_name = list1.get(6);
        log.info("ArchiveService_taskList:{}", sql);
        //ResultSet rs1=new DBUtils(dataSourceFactory.get(project_name).getConnection()).executeQuery(sql, null);
        //return new DBUtils(dataSource_s.getConnection()).executeQuery(sql, null);
        return new DBUtils(dataSourceFactory.get(project_name).getConnection()).executeQuery(sql, null);
    }

    public ResultSet task(List<String> list1) throws SQLException {
        //根据主键的最大值和最小值、时间的最大值和最小值，进行查询获取归档表的记录数，
        String sql = "select count(" + list1.get(0) + ") as cnt_id  from " + list1.get(7)
                + "." + list1.get(1) + " where " + list1.get(2) + ">'" + list1.get(3) + "' and "
                + list1.get(2) + "<='" + list1.get(4) + "' and " + list1.get(0)
                + ">=" + list1.get(5) + " and " + list1.get(0) + "<=" + list1.get(6);
        log.info("ArchiveService_task，:{} ",sql);
        String project_name = list1.get(8);
        return new DBUtils(dataSourceFactory.get(project_name).getConnection()).executeQuery(sql, null);
    }

    public int UpdateArchive(List<String> list1) throws SQLException {
        //归档成功，更新t_archive表的归档记录数字段、归档成功标识、归档开始时间、归档结束时间
        String sql = "update t_archive set  recursion_status='" + list1.get(6) + "' , arch_num=" + list1.get(0)
                + ",min_id=" + list1.get(1) + ",max_id=" + list1.get(2)
                + ",del_begintime='" + list1.get(3) + "',del_endtime='" + list1.get(4) + "'"
                + ",verify_begintime='" + list1.get(3) + "',verify_endtime='" + list1.get(4) + "'"
                + " , exec_time='" + list1.get(5) + "' , recursion_remark='" + list1.get(7) + "' where id=" + list1.get(8);
        log.info("ArchiveService_UpdateArchive，:{} ",sql);
        return new DBUtils(dataSource_t.getConnection()).executeUpdate(sql);
    }

    //归档完成，直接更新状态为F
    public int UpdateArchiveSucc(List<String> list1) throws SQLException {
        //归档成功

        String sql = "update t_archive t set t.arch_num=(select sum(del_arch_num) from t_archive_his a where a.archive_id=t.id and a.recursion_status='2'),t.recursion_status='" + list1.get(0) + "',t.recursion_remark='" + list1.get(1) + "' where t.id=" + list1.get(2);
        log.info("ArchiveService_UpdateArchiveSucc:{} " , sql);

        return new DBUtils(dataSource_t.getConnection()).executeUpdate(sql);
    }

    public int UpdateArchiveFail1(List<String> list1) throws SQLException {

        //归档失败，记录归档信息
        String sql = "update t_archive set recursion_status='" + list1.get(0) + "' , recursion_remark='" + list1.get(2) + "' where id=" + list1.get(1);
        log.info("ArchiveService_UpdateArchiveFail1:{} ", sql);
        return new DBUtils(dataSource_t.getConnection()).executeUpdate(sql);
    }

    public int UpdateArchiveFail2(List<String> list1) throws SQLException {
        //归档失败，记录归档信息

        String sql = "update t_archive set  recursion_status='" + list1.get(4) + "' , arch_num=" + list1.get(0)
                + " ,  exec_time='" + list1.get(3) + "',del_begintime='" + list1.get(1)
                + "',del_endtime='" + list1.get(2) + "', recursion_remark='" + list1.get(8) + "记录数小于等于0，无归档数据。归档起始时间:"
                + list1.get(1) + "；归档结束时间:" + list1.get(2) + ";主键最大值:" + list1.get(6) + ";主键最小值:" + list1.get(7)
                + "' where id=" + list1.get(5);
        log.info("ArchiveService_UpdateArchiveFail2: {}", sql);

        return new DBUtils(dataSource_t.getConnection()).executeUpdate(sql);
    }

    public int InsertArchiveHis(List<String> list1) throws SQLException {
        //归档成功，记录归档历史记录

        String sql = "insert into t_archive_his(archive_id,s_db,s_tab,recursion_status,del_arch_num,arch_begintime,arch_endtime,max_id,min_id,recursion_remark,t_tab)values("
                + list1.get(0) + ",'" + list1.get(1) + "','" + list1.get(2) + "','" + list1.get(3) + "',"
                + list1.get(4) + ",'" + list1.get(5) + "','" + list1.get(6) + "'," + list1.get(7) + "," + list1.get(8) + ",'" + list1.get(9) + "','" + list1.get(10) + "')";
        log.info("ArchiveService_InsertArchiveHis: {}" ,sql);

        return new DBUtils(dataSource_t.getConnection()).executeUpdate(sql);
    }


}
*/