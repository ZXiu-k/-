package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.pojo.CheckRecord;
import com.example.pojo.Emp;
import com.example.pojo.LeaveRecord;

@Mapper
public interface EmpMapper {

    @Select("select * from emp where name=#{name} and password=#{password}")
    Emp getByNameAndPassword(Emp emp);

    @Select("select id, name, gender, age from emp where id=#{id}")
    Emp getEmpById(Integer id); // 根据id获取员工信息

    @Select("select * from check_record where emp_id = #{id}")
    List<CheckRecord> getRecords(Integer id); // 获取考勤记录

    @Insert("insert into check_record (emp_id) values(#{empId})")
    void addCheck(CheckRecord record); // 在签到记录表中增加记录

    @Update("update check_record set check_in_time = #{checkInTime} where emp_id = #{empId} and check_in_time is null")
    void checkIn(CheckRecord record); // 签到打卡

    @Select("select count(*) from check_record where emp_id = #{empId} and check_in_time >= CURDATE() and check_in_time < CURDATE() + INTERVAL 1 DAY")
    Integer checkedIn(CheckRecord record); // 检查是否已经签到

    @Select("select count(*) from check_record where emp_id = #{empId} and check_out_time >= CURDATE() and check_out_time < CURDATE() + INTERVAL 1 DAY")
    Integer checkedOut(CheckRecord record); // 检查是否已经签退

    @Insert("update check_record set check_out_time = #{checkOutTime} where emp_id = #{empId} and check_out_time is null")
    void checkOut(CheckRecord record); // 签退打卡

    @Insert("insert into leave_record (emp_id, date, reason) values (#{empId}, #{date}, #{reason})")
    int insertLeaveRecord(LeaveRecord leave);

    @Select("select * from leave_record where emp_id = #{id} order by date desc")
    List<LeaveRecord> getLeave(Integer id); // 获取请假记录
}
