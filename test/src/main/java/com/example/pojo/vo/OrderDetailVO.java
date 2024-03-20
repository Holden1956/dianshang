package com.example.pojo.vo;

import lombok.Data;

import java.util.List;


@Data
public class OrderDetailVO {
    // 商品状态信息，根据商品id及请求头中用户id从opt表查询
    // private List<Operate> operateList;

    // 地址信息，根据订单id从订单表查询
    private String recv_name;
    private Integer zip;
    private String recv_phone;
    private String recv_address;
    private OrderItemDetailVO orderItemDetailVO;
    private List<OrderOptVO> orderOptVOList;
    // 商品详情信息，根据订单id及商品id从订单项表查询
//    private Integer id;
//    private Data modifiedTime;
//    private String title;
//    private Integer price;
//    private Integer num;
//    private String total;
//
//    public Integer getTotal() {
//        return price * num;
//    }

}
