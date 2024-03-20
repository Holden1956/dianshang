package com.example.pojo.vo;

import lombok.Data;

/**
 * @Date 2024/3/7 11:56
 * @Author qixuan he
 * @Description: 在线支付
 */
@Data
public class CartOnlinePayVO {
    //订单号
    private Integer id;
    //总金额
    private Integer total_price;
}
