package com.example.pojo.vo;

import lombok.Data;

/**
 * @Date 2024/3/4 10:27
 * @Author qixuan he
 * @Description: 用于热销排行，新到好货，收藏
 */
@Data
public class ProductVO {
    // 商品id
    private Integer id;
    // 商品名
    private String title;
    // 单价
    private Integer price;
    // 图片路径
    private String image;
}
