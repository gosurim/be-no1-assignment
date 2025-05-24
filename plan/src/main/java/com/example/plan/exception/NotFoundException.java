package com.example.plan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {
    public NotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "해당 할 일을 찾을 수 없습니다.");
    }// 잘못된 정보(id)로 조회하려고 할 떄나
    // 이미 삭제된 정보를 조회하려고 할 때

}
