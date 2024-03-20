package com.example.pojo.commonPojo;

import lombok.Data;

import java.util.Date;

/**
 * 订单表
 *
 * @TableName t_order
 */
@Data
public class Order {

    /**
     * id
     */
    private Integer id;
    /**
     * 用户id
     */
    private Integer user_id;
    /**
     * 收件人姓名
     */
    private String recv_name;
    /**
     * 收件人电话
     */
    private String recv_phone;
    /**
     * 收货省
     */
    private String recv_province;
    /**
     * 收货市
     */
    private String recv_city;
    /**
     * 收货区
     */
    private String recv_area;
    /**
     * 详细地址
     */
    private String recv_address;
    /**
     * 订单状态
     */
    private Integer status;
    /**
     * 订单总价
     */
    private Integer total_price;
    /**
     * 订单创建时间
     */
    private Date order_time;
    /**
     * 订单支付时间
     */
    private Date pay_time;
    /**
     * 是否删除
     */
    private Integer is_delete;
    /**
     * 创建用户
     */
    private String created_user;
    /**
     * 创建时间
     */
    private Date created_time;
    /**
     * 最后修改用户
     */
    private String modified_user;
    /**
     * 最后修改时间
     */
    private Date modified_time;
}
