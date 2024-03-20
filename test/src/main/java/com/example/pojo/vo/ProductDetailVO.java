package com.example.pojo.vo;

import lombok.Data;

/**
 * @ProjectName: cqjtu-store
 * @Titile: ProductDetailVO
 * @Author: Lucky
 * @Description: 商品详情VO
 */
@Data
public class ProductDetailVO extends ProductVO {
    /*是否被收藏*/
    private Integer is_favorite;
    /*卖点*/
    private String sell_point;
}
