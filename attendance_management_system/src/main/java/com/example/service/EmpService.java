package com.example.service;

import com.example.pojo.CheckRecord;
import com.example.pojo.Emp;
import com.example.pojo.LeaveRecord;
import com.example.pojo.PageBean;

public interface EmpService {

    Emp login(Emp emp);

    Emp getEmpById(Integer id); // 根据id获取员工信息

    boolean checkedIn(CheckRecord check); // 检查是否已经签到打卡

    void checkIn(CheckRecord check); // 签到打卡

    boolean checkedOut(CheckRecord check); // 检查是否已经签退打卡

    void checkOut(CheckRecord check); // 签退打卡

    PageBean getRecords(Integer id, Integer page, Integer rows); // 获取考勤记录

    void askForLeave(LeaveRecord leave);

    PageBean getLeave(Integer id, Integer page, Integer rows); // 获取请假记录
}
