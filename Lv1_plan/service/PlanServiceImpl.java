package com.example.plan.service;

import com.example.plan.dto.PlanRequestDto;
import com.example.plan.dto.PlanResponseDto;
import com.example.plan.entity.Plan;
import com.example.plan.entity.TimeSet;
import com.example.plan.repository.PlanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PlanServiceImpl implements PlanService{
    private final PlanRepository planRepository;
    public PlanServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    // 단건 조회
    @Override
    public PlanResponseDto findPlanById(Long id) {
        Plan plan = planRepository.findMemoByIdOrElseThrow(id);
        return new PlanResponseDto(plan);
    }


    @Override
    public PlanResponseDto savePlan(PlanRequestDto dto) {
        Plan plan = new Plan(dto.getName(), dto.getPw(), dto.getTodo(), TimeSet.Current());
        return planRepository.savePlan(plan);
    }

    @Override
    public List<PlanResponseDto> findAllPlans(String name, String date) {
        Plan plan = new Plan(name, date);
        return planRepository.findAllPlans(plan);
    }
}
