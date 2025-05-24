package com.example.plan.repository;

import com.example.plan.dto.PlanResponseDto;
import com.example.plan.entity.Plan;

import java.util.List;

public interface PlanRepository {
    PlanResponseDto savePlan(Plan plan);

    List<PlanResponseDto> findAllPlans(Plan plan);

    Plan findMemoByIdOrElseThrow(Long id);

    int updatePlan(Long id, String pw, String name, String todo, String current);

    int deletePlan(Long id, String pw);
}
