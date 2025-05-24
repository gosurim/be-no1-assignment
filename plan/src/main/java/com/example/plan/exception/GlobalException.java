package com.example.plan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalException {
    // ResponseStatusException 예외 발생 시 실행 ↓
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseException(ResponseStatusException ex){
        return new ResponseEntity<>(ex.getReason(), ex.getStatusCode());
    } // 비밀번호 불일치 오류 시 여기에서 자동으로 잡히고,
    // 사용자는 http status 401, 메시지를 받게됨

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex){
        return new ResponseEntity<>("서버 오류", HttpStatus.INTERNAL_SERVER_ERROR);
    }





}
