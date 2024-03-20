package com.example.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Date 2024/3/1 15:58
 * @Author qixuan he
 * @Description:
 */
@Data
public class AddressUpdateDTO {
    //如果是新增，地址是没有编号的
    //如果是修改，地址是有编号的，而且还有用户编号
    // 初衷：新增和修改进行重用

    //这里不校验，因为这个DTO要用来做新增
    /*地址编号*/
    private Integer id;
    /*用户编号 */
    private Integer user_id;
    @NotEmpty(message = "收货人不能为空")
    private String name;
    @NotNull(message = "省/直辖市的编号不能为空")
    private Integer province_code;
    @NotEmpty(message = "省/直辖市的名称不能为空")
    private String province_name;
    @NotNull(message = "城市编号不能为空")
    private Integer city_code;
    @NotEmpty(message = "城市名称不能为空")
    private String city_name;

    private Integer area_code;
    private String area_name;
    private Integer zip;
    private String address;

    private String phone;
    private String tel;
    private String tag;
}
