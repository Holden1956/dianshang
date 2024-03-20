package com.example.pojo.vo;

import lombok.Data;

@Data
public class OrderItemDetailVO {
    private Integer id;
    private Data modified_time;
    private String title;
    private Integer price;
    private Integer num;
    private String total;

    public Integer getTotal() {
        return price * num;
    }
}
