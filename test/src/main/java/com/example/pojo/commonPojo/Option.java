package com.example.pojo.commonPojo;

import lombok.Data;

/**
 * @Date 2024/3/2 9:49
 * @Author qixuan he
 * @Description: 导航实体
 */
@Data
public class Option {
    // 导航编号
    private Integer id;
    // 父级编号
    private Integer parent_id;
    // 导航条名称
    private String name;
    // 导航条状态
    private Integer status;
    // 排序号
    private Integer sort_order;
    // 是否是父类
    private Integer is_parent;
    // 创建人
    private String created_user;
    // 创建时间
    private Data created_time;
    // 最后修改人
    private String modified_user;
    // 最后修改时间
    private Data modified_time;
}
