package com.example.pojo.commonPojo;

import lombok.Data;

/**
 * @Date 2024/3/3 14:46
 * @Author qixuan he
 * @Description: 购物车实体类
 */
@Data
public class Cart {
    // 购物车编号，一个user有几种商品，分别一个购物车
    private Integer id;
    // 用户id
    private Integer user_id;
    // 该车中的商品id
    private Integer product_id;
    // 单价
    private Integer price;
    // 数量
    private Integer num;
    // 创建人
    private String created_user;
    // 创建时间
    private Data created_time;
    // 最后修改人
    private String modified_user;
    // 最后修改时间
    private Data modified_time;
}
