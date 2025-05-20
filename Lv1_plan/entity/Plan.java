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

    public Plan(String name, String pw, String todo, String date){
        this.name = name;
        this.pw = pw;
        this.todo = todo;
        this.date = date;
    }

    public Plan(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public Plan(long id, String name, String todo, String date) {
        this.id = id;
        this.name = name;
        this.todo = todo;
        this.date = date;
    }
}
