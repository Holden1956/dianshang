package com.example.mapper;

import com.example.pojo.commonPojo.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface cartmapper {
    //获取购物车列表
    List<Cart> getCartListByUserId(Integer id);

    Cart getCartByPUId(@Param("user_id")Integer user_id,@Param("id") Integer id);

    int delCartCell(@Param("user_id") Integer user_id, @Param("id") Integer id);

    //方法，用于购物车内数量加减
    Cart getProductId(Integer productId);
    Integer updateAddNum(@Param("product_id") Integer product_id,@Param("user_id") Integer user_id);
    Integer updateDecreaseNum(@Param("product_id") Integer product_id,@Param("user_id") Integer user_id);

    int save(Cart cart);

    List<Cart> getCartListByPUId(@Param("user_id")Integer user_id,@Param("id") Integer id);
}
