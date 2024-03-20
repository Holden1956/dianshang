package com.example.pojo.commonPojo;

import lombok.Data;

import java.util.Date;

/**
 * @author:qixuan he
 * @date:2024/2/29 14:14
 * @modyified By:
 */
@Data
public class Address {
    // 地址id
    private Integer id;
    // 收货人id
    private Integer user_id;
    // 收货人姓名
    private String name;
    // 省名
    private String province_name;
    // 省代码
    private Integer province_code;
    // 市名称
    private String city_name;
    // 市代码
    private Integer city_code;
    // 区名称
    private String area_name;
    // 区代码
    private Integer area_code;
    // 邮政编码
    private Integer zip;
    // 详细地址
    private String address;
    // 手机号
    private String phone;
    // 固话
    private String tel;
    // 地址类型
    private String tag;
    // 是否为默认地址
    private Integer is_default;
    // 创建人
    private String created_user;
    // 创建时间
    private Date created_time;
    // 最后修改人
    private String modified_user;
    // 修改时间
    private Date modified_time;
}
