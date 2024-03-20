package com.example.service;

import com.example.pojo.dto.OrderOperateDTO;
import com.example.pojo.vo.OrderDetailVO;
import com.example.pojo.vo.OrderVO;
import com.example.pojo.vo.UserLoginVO;

import java.util.List;

public interface OrderService {
    List<OrderVO> getList(UserLoginVO userLoginVO,Integer status);
    void operateOrder(OrderOperateDTO orderOperateDTO, UserLoginVO userLoginVO);

    OrderDetailVO getOrderDetail(UserLoginVO userLoginVO, Integer orderId, Integer productId);
}
