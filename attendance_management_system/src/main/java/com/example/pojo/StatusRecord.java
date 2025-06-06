package com.example.pojo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusRecord {
    private Integer id;
    private Integer empId;
    private LocalDate date;
    private Short status;
}
