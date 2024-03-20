package com.example.pojo.vo;

import lombok.Data;

/**
 * @Date 2024/3/5 16:16
 * @Author qixuan he
 * @Description: 订单内每一项
 */
@Data
public class OrderItemVO {
    private Integer id;
    private Integer order_id;
    private String image;
    private Integer product_id;
    private Integer price;
    private Integer num;
    private Integer total;//订单项总价
    private String title;

    public Integer gettotal(){
        return price*num;
    }
}
