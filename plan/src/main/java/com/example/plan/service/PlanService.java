package com.example.plan.service;

import com.example.plan.dto.PlanRequestDto;
import com.example.plan.dto.PlanResponseDto;

import java.util.List;

public interface PlanService {
    PlanResponseDto savePlan(PlanRequestDto dto);

    List<PlanResponseDto> findAllPlans(Long userId);

    PlanResponseDto findPlanById(Long id);

    PlanResponseDto updatePlan(Long id, String pw, String name, String todo);

    void deletePlan(Long id, String pw);
}
