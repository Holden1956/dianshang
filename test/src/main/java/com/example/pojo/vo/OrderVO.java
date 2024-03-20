package com.example.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Date 2024/3/5 16:13
 * @Author qixuan he
 * @Description: 订单VO
 */
@Data
public class OrderVO {
    private Integer id;
    private String recv_name;
    private LocalDateTime order_time;
    private Integer status;
    private Integer total_price;//订单总价
    private List<OrderItemVO> orderItemVOList;
}
