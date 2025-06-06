package com.example.pojo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckRecord {
    private Integer id;
    private Integer empId;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
}
