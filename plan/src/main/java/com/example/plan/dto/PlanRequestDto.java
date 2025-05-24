package com.example.plan.dto;

import lombok.Getter;

@Getter
public class PlanRequestDto {
    private String name;
    private String pw;
    private String todo;
    private String date;
    private String modi;
    private Long userId; // Lv3
}
