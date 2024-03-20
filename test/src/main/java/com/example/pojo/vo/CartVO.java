package com.example.pojo.vo;


import lombok.Data;

/**
 * @Date 2024/3/3 14:45
 * @Author qixuan he
 * @Description: 某用户购物车
 */
@Data
public class CartVO {
    // 购物车id
    private Integer id;
    // 商品id
    private Integer product_id;
    // 商品图片
    private String product_image;
    // 商品名称
    private String product_name;
    // 单价
    private Integer product_price;
    // 数量
    private Integer product_num;
    // 金额=单价*数量
    private Integer product_total;
}
