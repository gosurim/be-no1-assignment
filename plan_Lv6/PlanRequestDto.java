package com.example.plan.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PlanRequestDto {
    @NotNull // Lv6
    @Size(max=200) // Lv6
    private String name;
    @NotNull // Lv6
    private String pw;
    private String todo;
    private String date;
    private String modi;
    private Long userId; // Lv3
}
