package com.example.pojo.commonPojo;

import lombok.Data;

import java.util.Date;

@Data
public class OrderItem {
    private Integer id;
    private Integer order_id;
    private Integer product_id;
    private Integer num;
    private Integer price;
    private String image;
    private String title;
    private String created_user;
    private Date created_time;
    private String modified_user;
    private Date modified_time;
}
