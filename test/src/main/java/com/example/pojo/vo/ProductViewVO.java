package com.example.pojo.vo;

import lombok.Data;

/**
 * @Date 2024/3/14 16:05
 * @Author qixuan he
 * @Description: 商品浏览对象
 */
@Data
public class ProductViewVO {
    private Integer id;
    // 分类id
    private Integer category_id;
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
    //浏览计数
    private Integer count;

}
