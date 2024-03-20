package com.example.pojo.vo;

import lombok.Data;

/**
 * @Date 2024/3/5 10:05
 * @Author qixuan he
 * @Description: 带有收藏标志位的商品VO
 */
@Data
public class ProductWithFavoriteVO extends ProductVO{
    private Integer is_Favorite;
}
