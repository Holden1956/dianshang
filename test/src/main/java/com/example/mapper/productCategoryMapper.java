package com.example.mapper;

import com.example.pojo.commonPojo.ProductCategory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface productCategoryMapper {
    List<ProductCategory> getList();

    ProductCategory getById(Integer id);
}
