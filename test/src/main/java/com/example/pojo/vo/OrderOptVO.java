package com.example.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class OrderOptVO {
    private String type;
    private Date created_time;
}
