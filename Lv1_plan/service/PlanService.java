package com.example.plan.service;

import com.example.plan.dto.PlanRequestDto;
import com.example.plan.dto.PlanResponseDto;

import java.util.List;

public interface PlanService {
    PlanResponseDto savePlan(PlanRequestDto dto);

    List<PlanResponseDto> findAllPlans(String name, String date);

    PlanResponseDto findPlanById(Long id);
}
