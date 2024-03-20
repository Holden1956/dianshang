package com.example.pojo.commonPojo;

import lombok.Data;

@Data
public class CartProductMax {
    //商品id
    private Integer product_id;
    //商家库存最大值
    private Integer ProductMax;
}
