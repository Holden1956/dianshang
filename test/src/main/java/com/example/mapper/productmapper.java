package com.example.mapper;

import com.example.pojo.commonPojo.Product;
import com.example.pojo.dto.ProductCategoryPageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface productmapper {
    List<Product> getNewList();

    List<Product> getHotList();

    Product getProductByID(Integer id);

    List<Product> getListByCategoryId(Integer category_id);

    Integer getIdByCategoryId(Integer category_id);

    int count(Integer category_id);

    List<Product> getProductByPage(ProductCategoryPageDTO productCategoryPageDTO);

    List<Product> getAllProduct();

    Integer updateStatus(@Param("id") Integer id,@Param("newStatus") Integer newStatus);

    Integer updatePriority(@Param(("id")) Integer id, @Param("newPriority") Integer priority);

    Integer delProductBuId(Integer productId);
}
