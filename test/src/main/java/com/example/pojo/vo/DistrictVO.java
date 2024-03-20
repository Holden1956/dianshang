package com.example.pojo.vo;

import lombok.Data;

/**
 * @author:xuanhe
 * @date:2024/3/1 10:28
 * @modyified By:
 */
@Data
public class DistrictVO {//用于新增收获地址接口的下拉列表事务级联
    //区域编码
    private Integer code;
    //区域名称
    private String name;
}
