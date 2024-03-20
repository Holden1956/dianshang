package com.example.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author:xuanhe
 * @date:2024/3/1 1:19
 * @modyified By:
 */
@Data
@ApiModel(value = "新增/修改收货地址信息", description = "对用户新增/修改收货地址信息进行封装")
public class AddressInsertDTO {

    // 新增的地址，一开始没有id
    // 修改的地址，一开始有id
    // 想法：新增与修改共用一个DTO，so id 不校验
    // 地址编号
    private Integer id;

    // 用户编号
    private Integer user_id;
    // 收货人用户名
    @NotEmpty(message = "收货人不能为空")
    @ApiModelProperty(value = "收货人用户名", name = "name", example = "Alex", required = true)
    private String name;
    // 省级编码
    @NotNull(message = "省级编码不能为空")
    @ApiModelProperty(value = "省级编码", name = "province_code", example = "500000", required = true)
    private Integer province_code;
    // 省级名称
    @NotEmpty(message = "省级名称不能为空")
    @ApiModelProperty(value = "省级名称", name = "province_name", example = "重庆市", required = true)
    private String province_name;
    // 城市编码
    @NotNull(message = "城市编码不能为空")
    @ApiModelProperty(value = "城市编码", name = "city_code", example = "500100", required = true)
    private Integer city_code;
    // 城市名称
    @NotEmpty(message = "城市名称不能为空")
    @ApiModelProperty(value = "城市名称", name = "city_name", example = "直辖市", required = true)
    private String city_name;
    // 区域编码
    @NotNull(message = "区域编码不能为空")
    @ApiModelProperty(value = "区域编码", name = "area_code", example = "500101", required = true)
    private Integer area_code;
    // 区域名称
    @NotEmpty(message = "区域名称不能为空")
    @ApiModelProperty(value = "区域名称", name = "area_name", example = "万州区", required = true)
    private String area_name;
    // 邮政编码
    @ApiModelProperty(value = "邮政编码", name = "zip", example = "666666", required = false)
    private Integer zip;
    // 详细地址
    @NotEmpty(message = "详细地址不能为空")
    @ApiModelProperty(value = "详细地址", name = "address", example = "xx镇11号", required = true)
    private String address;
    // 电话
    @NotEmpty(message = "电话不能为空")
    @ApiModelProperty(value = "电话", name = "phone", example = "182****1123", required = true)
    private String phone;
    // 固话
    @ApiModelProperty(value = "固话", name = "tel", example = "****", required = false)
    private String tel;
    // 类型
    @ApiModelProperty(value = "地址类型", name = "tag", example = "学校", required = false)
    private String tag;
}
