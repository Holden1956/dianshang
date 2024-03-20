package com.example.service;

import com.example.common.PageVO;
import com.example.pojo.dto.FavoritePageDTO;
import com.example.pojo.vo.ProductVO;
import com.example.pojo.vo.UserLoginVO;

import java.util.List;

public interface FavoriteService {
    PageVO<List<ProductVO>> getFavoriteList(FavoritePageDTO favoritePageDTO, UserLoginVO userLoginVO);
    void addFavorite(Integer productId, UserLoginVO userLoginVO);

    void cancelFavorite(Integer productId, UserLoginVO userLoginVO);
}
