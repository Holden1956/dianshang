package com.example.pojo.vo;

import lombok.Data;

/**
 * @author:xuanhe
 * @date:2024/3/1 14:43
 * @modyified By:
 */
@Data
public class AddressUpdateVO {
    /*地址编号*/
    private Integer id;
    /*用户编号*/
    private Integer user_id;
    private String name;
    private Integer province_code;
    private String province_name;
    private Integer city_code;
    private String city_name;
    private Integer area_code;
    private String area_name;
    private Integer zip;
    private String address;

    private String phone;
    private String tel;
    private String tag;
}
