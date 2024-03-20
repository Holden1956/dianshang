package com.example.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/*
    更改数量封装
 */
@Data
public class CartIdDTO {
    //商品id
    @NotNull(message = "商品id不能为空")
    private Integer product_id;
    //点击加status=1；点击减status为0
    @NotNull(message = "状态不能为空")
    private Boolean product_status;
    //商品上限
    @NotNull(message = "上限不能为空")
    private Integer product_max;
}
