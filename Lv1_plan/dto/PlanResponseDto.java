package com.example.plan.dto;

import com.example.plan.entity.Plan;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlanResponseDto {
    private Long id;
    private String name;
    private String todo;
    private String date;

    public PlanResponseDto(Plan plan) {
        this.id = plan.getId();
        this.name = plan.getName();
        this.todo = plan.getTodo();
        this.date = plan.getDate();
    }
}
