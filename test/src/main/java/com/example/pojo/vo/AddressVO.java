package com.example.pojo.vo;

import lombok.Data;

/**
 * @author:xuanhe
 * @date:2024/2/29 14:27
 * @modyified By:
 */
@Data
public class AddressVO {
    //收获地址id
    private Integer id;
    //收货人姓名
    private String name;
    //详细地址
    private String address;
    //是否为默认地址
    private Integer is_default;
    //地址类型
    private String tag;
    //电话
    private String phone;
}
