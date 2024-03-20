package com.example.pojo.commonPojo;

import lombok.Data;

/**
 * @Date 2024/3/3 15:21
 * @Author qixuan he
 * @Description: 商品实体类
 */
@Data
public class Product {
    // 商品id
    private Integer id;
    // 分类id
    private Integer category_id;
    // 商品系列
    private String item_type;
    // 商品标题
    private String title;
    // 卖点
    private String sell_point;
    // 单价
    private Integer price;
    // 库存
    private Integer num;
    // 图片路径
    private String image;
    // 商品状态
    private Integer status;
    // 优先级
    private Integer priority;
    // 创建人
    private String created_user;
    // 创建时间
    private Data created_time;
    // 最后修改人
    private String modified_user;
    // 最后修改时间
    private Data modified_time;
}
