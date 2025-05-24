package com.example.plan.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class User {
    // lv3
    // userId, emial, name, crDate, mdDate
    private Long id;
    private String name;
    private String email;
    private LocalDateTime crDate;
    private LocalDateTime mdDate;


}
