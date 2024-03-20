package com.example.pojo.dto;

import com.example.common.PageDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Date 2024/3/4 14:18
 * @Author qixuan he
 * @Description: 商品分类
 */
@Data
public class ProductCategoryPageDTO extends PageDTO {
    @NotNull(message="分类id不能为空")
    private Integer category_id;
}
