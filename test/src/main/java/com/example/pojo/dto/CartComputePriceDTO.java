package com.example.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Date 2024/3/6 10:38
 * @Author qixuan he
 * @Description: 选择商品计算总价
 */
@Data
public class CartComputePriceDTO {
    @NotNull(message = "商品id不能为空")
    private Integer id;
}
