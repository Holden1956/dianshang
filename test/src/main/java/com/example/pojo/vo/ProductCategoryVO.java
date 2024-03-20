package com.example.pojo.vo;

import lombok.Data;

/**
 * @ProjectName: cqjtu-store
 * @Titile: ProductCategoryVO
 * @Author: Lucky
 * @Description: 商品分类VO
 */
@Data
public class ProductCategoryVO {
    private Integer id;
    private String name;
    private Integer parent_id;
}
