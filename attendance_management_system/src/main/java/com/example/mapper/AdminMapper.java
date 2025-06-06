package com.example.mapper;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.pojo.CheckRecord;
import com.example.pojo.Emp;
import com.example.pojo.LeaveRecord;
import com.example.pojo.Summary;

@Mapper
public interface AdminMapper {

    @Select("select id, name, gender, age from emp where role = 0")
    List<Emp> getEmps();

    @Select("select * from leave_record order by date desc")
    List<LeaveRecord> getLeaveRecords();

    @Insert("insert into emp (name, gender, age, password) values (#{name}, #{gender}, #{age}, #{password})")
    void addEmp(Emp emp);

    @Delete("delete from emp where name = #{name}")
    void deleteEmp(Emp emp);

    // @Select("select * from check_record")
    List<CheckRecord> getRecords(String name);

    @Select("select * from emp where name = #{name}")
    Emp getEmpByName(String name);

    @Delete("delete from emp where id = #{id}")
    void deleteEmpById(Integer id);

    @Delete("delete from leave_record where emp_id = #{id}")
    void deleteLeaveRecordByEmpId(Integer id);
    
    @Delete("delete from check_record where emp_id = #{id}")
    void deleteCheckRecordByEmpId(Integer id);
    
    List<Summary> getSummary(LocalDate firstDay, LocalDate lastDay);

    @Delete("delete from leave_record where emp_id = #{empId} and date = #{date}")
    void deleteLeaveRecordByEmpIdAndDate(Integer empId , LocalDate date);

    @Update("update leave_record set status = 1 where emp_id = #{empId} and date = #{date}")
    void patchLeaveRecord(Integer empId, LocalDate date);
    
    void updateEmp(Emp emp); // 更新员工信息
}
