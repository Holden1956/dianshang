package com.example.pojo.dto;

import com.example.common.PageDTO;
import lombok.Data;

/**
 * @Date 2024/3/5 14:59
 * @Author qixuan he
 * @Description: 收藏模块的查询
 */
@Data
public class FavoritePageDTO extends PageDTO {
    private Integer user_id;//可以不需要加@NotNull游客你就不显示
}
