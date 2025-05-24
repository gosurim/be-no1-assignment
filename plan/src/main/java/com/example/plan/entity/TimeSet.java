package com.example.plan.entity;

import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class TimeSet {
    public static String Current(){
        Date nowTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(nowTime);
    }
}
