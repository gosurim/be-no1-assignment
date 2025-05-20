package com.example.plan.controller;

import com.example.plan.dto.PlanRequestDto;
import com.example.plan.dto.PlanResponseDto;
import com.example.plan.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
public class PlanController {
    private final PlanService planService;
    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    // 일정 생성
    @PostMapping
    public ResponseEntity<PlanResponseDto> createPlan(@RequestBody PlanRequestDto dto){
        return new ResponseEntity<>(planService.savePlan(dto), HttpStatus.CREATED);
    }

    // 일정 전체 조회
    @PostMapping("/search")
    public List<PlanResponseDto> findAllPlans(@RequestBody PlanRequestDto dto){
        return planService.findAllPlans(dto.getName(), dto.getModi());
    }

    // 일정 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<PlanResponseDto> findPlanById(@PathVariable Long id) {
        return new ResponseEntity<>(planService.findPlanById(id), HttpStatus.OK);
    }

    // 일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<PlanResponseDto> updatePlan(
            @PathVariable Long id,
            @RequestBody PlanRequestDto dto
    ) {
        return new ResponseEntity<>(planService.updatePlan(id, dto.getPw(), dto.getName(), dto.getTodo()), HttpStatus.OK);
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(
            @PathVariable Long id,
            @RequestBody PlanRequestDto dto
    ) {

        planService.deletePlan(id, dto.getPw());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
