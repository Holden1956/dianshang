package com.example.pojo.vo;

import lombok.Data;

/**
 * @Date 2024/3/2 9:16
 * @Author qixuan he
 * @Description: 导航条查询
 */
@Data
public class OptionsVO {//之所以有parent_id是因为，前端menu.js里面有写商品分类表的数据
    // 导航编号
    private Integer id;
    // 父级编号
    private Integer parent_id;
    // 导航名称
    private String name;
}
