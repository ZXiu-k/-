package com.example.service.impl;

import java.beans.Transient;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mapper.AdminMapper;
import com.example.pojo.CheckRecord;
import com.example.pojo.Emp;
import com.example.pojo.LeaveRecord;
import com.example.pojo.PageBean;
import com.example.pojo.Summary;
import com.example.service.AdminService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public PageBean getEmps(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<Emp> list = adminMapper.getEmps();
        Page<Emp> p = (Page<Emp>) list;
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        p.close();
        return pageBean;
    }

    @Override
    public PageBean getLeaveRecords(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<LeaveRecord> list = adminMapper.getLeaveRecords();
        Page<LeaveRecord> p = (Page<LeaveRecord>) list;
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        p.close();
        return pageBean;
    }

    @Override
    public void addEmp(Emp emp) {
        adminMapper.addEmp(emp);
    }

    @Override
    @Transient
    public void deleteEmp(Emp emp) {
        adminMapper.deleteEmpById(emp.getId());
        adminMapper.deleteLeaveRecordByEmpId(emp.getId());
        adminMapper.deleteCheckRecordByEmpId(emp.getId());
    }

    @Override
    public Emp getEmpByName(String name) {
        return adminMapper.getEmpByName(name);
    }

    @Override
    public PageBean getRecords(String name, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<CheckRecord> list = adminMapper.getRecords(name);
        Page<CheckRecord> p = (Page<CheckRecord>) list;
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        p.close();
        return pageBean;
    }

    @Override
    public PageBean getSummary(YearMonth yearMonth, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<Summary> list = adminMapper.getSummary(yearMonth.atDay(1),yearMonth.atEndOfMonth());
        Page<Summary> p = (Page<Summary>) list;
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        p.close();
        return pageBean;
    }

    @Override
    public void approveLeave(LeaveRecord leaveRecord) {
        adminMapper.patchLeaveRecord(leaveRecord.getEmpId(), leaveRecord.getDate());
    }

    @Override
    public void rejectLeave(LeaveRecord leaveRecord) {
        adminMapper.deleteLeaveRecordByEmpIdAndDate(leaveRecord.getEmpId(), leaveRecord.getDate());
    }

    @Override
    public void updateEmp(Emp emp) {
        adminMapper.updateEmp(emp);
    }
    

}