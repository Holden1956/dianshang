package com.example.service;

import com.example.pojo.commonPojo.Opt;
import com.example.pojo.vo.OrderVO;
import com.example.pojo.vo.ProductViewVO;

import java.util.List;

public interface BackService {
    List<OrderVO> getOrderList();

    List<Opt> getUserDataByUserId(Integer userId);

    List<ProductViewVO> getProductView();

    List<ProductViewVO> updateProductStatusByPID(Integer id,Integer status);

    List<Opt> getAllUserDataByUserId();

    List<ProductViewVO> updateProductpriorityByPID(Integer id, Integer priority);

    List<ProductViewVO> delProduct(Integer productId);
}
