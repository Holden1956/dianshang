package com.example.mapper;

import com.example.pojo.commonPojo.Favorite;
import com.example.pojo.commonPojo.Product;
import com.example.pojo.dto.FavoritePageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface favoritemapper {

    Favorite getFavorite(@Param("product_id") Integer product_id, @Param("user_id") Integer user_id);

    List<Product> getFavoriteProduct(FavoritePageDTO favoritePageDTO);

    int count(Integer id);

    int insert(Favorite favorite);

    int delete(Integer product_id, Integer user_id);
}
