package com.example.controller;

import java.time.LocalDateTime;
import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pojo.Emp;
import com.example.pojo.LeaveRecord;
import com.example.pojo.Result;
import com.example.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/getAllEmps")
    public Result getEmps(@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer rows) {
        return Result.success(adminService.getEmps(page, rows));
    }

    @GetMapping("/getAllRecords")
    public Result getRecord(String name, @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer rows) {
        return Result.success(adminService.getRecords(name, page, rows));
    }

    @GetMapping("/getLeaveRecords")
    public Result getLeaveRecords(@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer rows) {
        return Result.success(adminService.getLeaveRecords(page, rows));
    }

    @PostMapping("/addEmp")
    public Result addEmp(@RequestBody Emp emp) {
        if (adminService.getEmpByName(emp.getName()) != null) {
            return Result.error("员工已存在");
        }
        adminService.addEmp(emp);
        return Result.success("员工添加成功");
    }

    @DeleteMapping("/deleteEmp")
    public Result deleteEmp(@RequestBody Emp emp) {
        adminService.deleteEmp(adminService.getEmpByName(emp.getName()));
        return Result.success("员工删除成功");
    }

    @PatchMapping("/reviewLeave")
    public Result approveLeave(@RequestBody LeaveRecord leaveRecord) {
        if (leaveRecord.getId() == null) {
            return Result.error("缺少必要参数");
        }
        adminService.approveLeave(leaveRecord);
        return Result.success("审核成功");
    }

    @PostMapping("/rejectLeave")
    public Result rejectLeave(@RequestBody LeaveRecord leaveRecord) {
        if (leaveRecord.getId() == null) {
            return Result.error("缺少必要参数");
        }
        adminService.rejectLeave(leaveRecord);
        return Result.success("审核成功");
    }

    @GetMapping("/getSummary")
    public Result getMethodName(@RequestParam YearMonth yearMonth,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer rows) {
        if (yearMonth == null) {
            yearMonth = YearMonth.from(LocalDateTime.now());
        }
        return Result.success(adminService.getSummary(yearMonth, page, rows));
    }

    @PostMapping("/updateEmp")
    public Result updateEmp(@RequestBody Emp emp) {
        System.out.println(emp.toString());
        adminService.updateEmp(emp);
        return Result.success("员工信息更新成功");
    }
    

}
