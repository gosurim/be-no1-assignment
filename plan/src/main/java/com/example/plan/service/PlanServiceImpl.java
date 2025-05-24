package com.example.plan.service;

import com.example.plan.dto.PlanRequestDto;
import com.example.plan.dto.PlanResponseDto;
import com.example.plan.entity.Plan;
import com.example.plan.entity.TimeSet;
import com.example.plan.repository.PlanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PlanServiceImpl implements PlanService{
    private final PlanRepository planRepository;
    public PlanServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public PlanResponseDto savePlan(PlanRequestDto dto) {
        Plan plan = new Plan(dto.getName(), dto.getPw(), dto.getTodo(), TimeSet.Current(), TimeSet.Current(), dto.getUserId());
        return planRepository.savePlan(plan);
    }

    @Override
    public List<PlanResponseDto> findAllPlans(Long userId) {
        Plan plan = new Plan(userId);
        return planRepository.findAllPlans(plan);
    }

    // 단건 조회
    @Override
    public PlanResponseDto findPlanById(Long id) {
        Plan plan = planRepository.findMemoByIdOrElseThrow(id);
        return new PlanResponseDto(plan);
    }

    @Transactional
    @Override
    public PlanResponseDto updatePlan(Long id, String pw, String name, String todo) {
        // 필수값
        if (pw == null || name == null || todo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The password, name and todo are required values.");
        }

        // 수정
        int updatedRow = planRepository.updatePlan(id, pw, name, todo, TimeSet.Current());
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data has been modified.");
        }


        Plan plan = planRepository.findMemoByIdOrElseThrow(id);
        return new PlanResponseDto(plan);

    }

    @Override
    public void deletePlan(Long id, String pw) {
        int deletedRow = planRepository.deletePlan(id, pw);
        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }
}
