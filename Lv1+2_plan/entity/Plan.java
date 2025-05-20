package com.example.plan.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Plan {
    private Long id;
    private String name;
    private String pw;
    private String todo;
    private String date;
    private String modi;

    public Plan(String name, String pw, String todo, String date, String modi){
        this.name = name;
        this.pw = pw;
        this.todo = todo;
        this.date = date;
        this.modi = modi;
    }

    // 목록 조회
    public Plan(String name, String date) {
        this.name = name;
        this.modi = date;
    }

    public Plan(long id, String name, String todo, String date, String modi) {
        this.id = id;
        this.name = name;
        this.todo = todo;
        this.date = date;
        this.modi = modi;
    }
}
