package com.example.pojo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRecord {
    private Integer id;
    private Integer empId;
    private LocalDate date;
    private String reason;
    private Short status; // 0: 未审核, 1: 已批准
}
