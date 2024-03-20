package com.example.pojo.commonPojo;

import lombok.Data;

/**
 * @author:qixuan he
 * @date:2024/3/1 10:20
 * @description:
 */
@Data
// t_dict_district表封装实体类，省/城市/区域全写在一张表（t_dict_district）里面了
public class District {
    // 记录id
    private Integer id;
    // 父级记录
    private Integer parent;
    // 当前记录的编码
    private Integer code;
    // 当前记录的名称
    private String name;
}