package com.example.plan.repository;

import com.example.plan.dto.PlanResponseDto;
import com.example.plan.entity.Plan;

import java.util.List;
import java.util.Optional;

public interface PlanRepository {
    PlanResponseDto savePlan(Plan plan);

    List<PlanResponseDto> findAllPlans(Plan plan);

    Plan findMemoByIdOrElseThrow(Long id);
}
