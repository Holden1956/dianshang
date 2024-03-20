package com.example.pojo.commonPojo;

import lombok.Data;

import java.util.Date;

/**
 * @ProjectName: cqjtu-store
 * @Titile: ProductCategory
 * @Author: Lucky
 * @Description: 商品分类实体类
 */
@Data
public class ProductCategory {
    //主键
    private Integer id;
    //父分类id
    private Integer parent_id;
    //名称
    private String name;
    //状态
    private Integer status;
    //排序号
    private Integer	sort_order;
    //是否是父分类
    private Integer is_parent;

    //创建人
    private String created_user;
    //  创建时间
    private Date created_time;
    //最后修改人
    private String modified_user;
    //最后修改时间
    private Date modified_time;

}
