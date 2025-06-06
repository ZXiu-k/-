package com.example.service;

import java.time.YearMonth;

import com.example.pojo.Emp;
import com.example.pojo.LeaveRecord;
import com.example.pojo.PageBean;

public interface AdminService {

    PageBean getEmps(Integer page, Integer rows);

    PageBean getLeaveRecords(Integer page, Integer rows);

    void addEmp(Emp emp);

    void deleteEmp(Emp emp);

    Emp getEmpByName(String name);

    PageBean getRecords(String name, Integer page, Integer rows);

    PageBean getSummary(YearMonth yearMonth, Integer page, Integer rows);

    void approveLeave(LeaveRecord leaveRecord);
    void rejectLeave(LeaveRecord leaveRecord);

    void updateEmp(Emp emp);
}
