package com.example.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pojo.CheckRecord;
import com.example.pojo.LeaveRecord;
import com.example.pojo.Result;
import com.example.service.EmpService;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping("/getInfo")
    public Result getInfo(@RequestAttribute Integer id) {
        return Result.success(empService.getEmpById(id));
    }

    @GetMapping("/getRecords")
    public Result getRecords(@RequestAttribute Integer id, @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer rows) {
        return Result.success(empService.getRecords(id, page, rows));
    }

    @PostMapping("/checkIn")
    public Result checkIn(@RequestAttribute Integer id) {
        CheckRecord check = new CheckRecord();
        check.setEmpId(id);
        check.setCheckInTime(LocalDateTime.now());
        if (empService.checkedIn(check)) {
            return Result.error("今日已签到");
        }
        empService.checkIn(check);
        return Result.success("签到打卡成功！");
    }

    @PostMapping("/checkOut")
    public Result checkOut(@RequestAttribute Integer id) {
        CheckRecord check = new CheckRecord();
        check.setEmpId(id);
        check.setCheckOutTime(LocalDateTime.now());
        if (empService.checkedIn(check)) {
            if (empService.checkedOut(check)) {
                return Result.error("今日已签退");
            }
            empService.checkOut(check);
            return Result.success("签退打卡成功！");
        }
        return Result.error("今日未签到，请先签到");
    }

    @PostMapping("/askForLeave")
    public Result askForLeave(@RequestAttribute Integer id, @RequestBody LeaveRecord leave) {
        leave.setEmpId(id);
        empService.askForLeave(leave);
        return Result.success("请假提交成功！");
    }

    @GetMapping("/getLeave")
    public Result getleave(@RequestAttribute Integer id, @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer rows) {
        return Result.success(empService.getLeave(id , page, rows));
    }
    
}
