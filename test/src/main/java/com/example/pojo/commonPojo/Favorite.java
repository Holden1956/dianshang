package com.example.pojo.commonPojo;

import lombok.Data;

/**
 * @Date 2024/3/4 8:43
 * @Author qixuan he
 * @Description: 收藏实体类
 */
@Data
public class Favorite {
    // 收藏id
    private Integer id;
    // 用户id
    private Integer user_id;
    // 商品id
    private Integer product_id;
    // 创建人
    private String created_user;
    // 创建时间
    private Data created_time;
    // 最后修改人
    private String modified_user;
    // 最后修改时间
    private Data modified_time;
}
