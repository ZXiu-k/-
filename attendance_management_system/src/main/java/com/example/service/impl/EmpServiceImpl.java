package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mapper.EmpMapper;
import com.example.pojo.CheckRecord;
import com.example.pojo.Emp;
import com.example.pojo.LeaveRecord;
import com.example.pojo.PageBean;
import com.example.service.EmpService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Override
    public Emp login(Emp emp) {
        return empMapper.getByNameAndPassword(emp);
    }

    @Override
    public Emp getEmpById(Integer id) {
        return empMapper.getEmpById(id);
    }

    @Override
    public boolean checkedIn(CheckRecord check) {
        return empMapper.checkedIn(check) != 0;
    }

    @Override
    public void checkIn(CheckRecord check) {
        empMapper.addCheck(check);
        empMapper.checkIn(check);
    }

    @Override
    public boolean checkedOut(CheckRecord check) {
        return empMapper.checkedOut(check) != 0;
    }

    @Override
    public void checkOut(CheckRecord check) {
        empMapper.checkOut(check);
    }

    @Override
    public PageBean getRecords(Integer id, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<CheckRecord> list = empMapper.getRecords(id);
        Page<CheckRecord> p = (Page<CheckRecord>) list;
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        p.close();
        return pageBean;
    }

    @Override
    public void askForLeave(LeaveRecord leave) {
        empMapper.insertLeaveRecord(leave);
    }   

    @Override
    public PageBean getLeave(Integer id, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<LeaveRecord> list = empMapper.getLeave(id);
        Page<LeaveRecord> p = (Page<LeaveRecord>) list;
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        p.close();
        return pageBean;
    }

}
