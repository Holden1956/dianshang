package com.example.service;

import com.example.common.PageVO;
import com.example.pojo.dto.ProductCategoryPageDTO;
import com.example.pojo.vo.ProductDetailVO;
import com.example.pojo.vo.ProductVO;
import com.example.pojo.vo.ProductWithFavoriteVO;
import com.example.pojo.vo.UserLoginVO;

import java.util.List;

public interface ProductService {

    List<ProductVO> getNewList();

    List<ProductVO> getHotList();

    PageVO<List<ProductWithFavoriteVO>> getListByCategoryId(ProductCategoryPageDTO productCategoryPageDTO, UserLoginVO userLoginVO);

    // PageInfo<Product> getListByCategoryId(ProductCategoryPageDTO categoryPageDTO);

    PageVO<List<ProductVO>> getListByCategoryId3(ProductCategoryPageDTO categoryPageDTO);

    ProductDetailVO getDetailById(Integer id, UserLoginVO userLoginVO);
}
