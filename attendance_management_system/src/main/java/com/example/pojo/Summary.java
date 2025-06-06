package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Summary {
    private Integer empId;
    private String name;
    private Integer workDaysTotal;
    private Integer normalDays;
    private Integer lateDays;
    private Integer earlyDays;
    private Integer lateEarlyDays;
    private Integer incompleteDays;
    private Integer absentDays;
    private Integer leaveDays;
}
