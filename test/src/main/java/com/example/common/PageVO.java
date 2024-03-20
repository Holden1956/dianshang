package com.example.common;

import lombok.Data;

/**
 * @Date 2024/3/4 15:12
 * @Author qixuan he
 * @Description: 用于所有分页查询
 */
@Data
public class PageVO<T> {
    // 页码
    private Integer pageIndex;
    // 每页数据量
    private Integer pageSize;
    // 总数据量
    private Integer count;
    // 总页码
    private Integer pages;
    // 数据
    private T data;

    public Integer getPages() {//事先算出总页面数
        return count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
    }

    public PageVO(Integer pageIndex, Integer pageSize, Integer count, T data) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.count = count;
        this.data = data;
    }
}
