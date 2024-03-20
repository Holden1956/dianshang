package com.example.pojo.commonPojo;

import lombok.Data;

/**
 * @Date 2024/3/13 18:28
 * @Author qixuan he
 * @Description: 用户行为
 */
@Data
public class Opt {
    private Integer id;
    private Integer user_id;
    private Integer product_id;
    private Integer category_id;
    private String type;
    private Data created_time;
}
