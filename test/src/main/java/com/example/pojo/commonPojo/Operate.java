package com.example.pojo.commonPojo;


import lombok.Data;

import java.util.Date;

@Data
public class Operate {
    //操作id
    private Integer id;
    //用户id
    private Integer user_id;
    //产品id
    private Integer product_id;
    //产品分类id
    private Integer category_id;
    //操作类型
    private Integer type;
    //操作时间
    private Date created_time;

}
