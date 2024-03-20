package com.example.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Date 2024/3/8 10:06
 * @Author qixuan he
 * @Description: 加入购物车
 */
@Data
public class CartAddDTO {
    @NotNull(message = "商品Id不能为空")
    private Integer id;
    @NotNull(message = "商品数量不能为空")
    private Integer num;
}
