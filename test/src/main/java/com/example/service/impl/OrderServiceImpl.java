package com.example.service.impl;

import com.example.common.ServiceCode;
import com.example.common.ServiceException;
import com.example.mapper.ordermapper;
import com.example.mapper.productmapper;
import com.example.pojo.commonPojo.Operate;
import com.example.pojo.commonPojo.Order;
import com.example.pojo.commonPojo.OrderItem;
import com.example.pojo.commonPojo.Product;
import com.example.pojo.dto.OrderOperateDTO;
import com.example.pojo.vo.*;
import com.example.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Date 2024/3/5 16:27
 * @Author qixuan he
 * @Description: 订单业务
 */
@Service
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ordermapper ordermapper;
    @Autowired
    private productmapper productmapper;

    @Override
    public List<OrderVO> getList(UserLoginVO userLoginVO, Integer status) {
        if(status!=0&&status!=1&&status!=2&&status!=3&&status!=5){
            throw new ServiceException(ServiceCode.ERR_BAD_REQUEST,"没有此状态");
        }
        if (status ==0) {
            System.out.println("查询全部");
            List<OrderVO> orderList = ordermapper.getOrderList(userLoginVO.getId());
            if (orderList == null) {
                throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "此用户没有订单数据");
            }
            return orderList;
        }
        System.out.println("查询状态");
        List<OrderVO> orderList = ordermapper.getOrderListByStatus(userLoginVO.getId(), status);
        if (orderList == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "此用户没有该状态订单数据");
        }
        return orderList;
    }

    @Override//订单操作
    public void operateOrder(OrderOperateDTO orderOperateDTO, UserLoginVO userLoginVO) {
        if (userLoginVO == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "无用户登录");
        }
        if (!orderOperateDTO.getUser_name().equals(userLoginVO.getUsername())) {
            throw new ServiceException(ServiceCode.ERR_FORBIDDEN, "该用户禁止修改");
        }
        if(orderOperateDTO.getOperateCode()<1||orderOperateDTO.getOperateCode()>4){
            throw new ServiceException(ServiceCode.ERR_BAD_REQUEST, "操作码错误，操作码有1，2，3，4分别代表去付款，去收货，去评价、去退货");
        }//操作码有1，2，3，4分别代表去付款，去收货，去评价、去退货
        Order order = new Order();
        order.setId(orderOperateDTO.getId());
        order.setModified_user(userLoginVO.getUsername());
        order.setStatus(orderOperateDTO.getOperateCode() + 1);
        int result = ordermapper.updateOrder(order);
        if (result != 1) {
            throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "订单状态修改失败");
        }
        List<OrderItem> orderItemList = ordermapper.getOrderItemList(orderOperateDTO.getId());
        orderItemList.forEach(orderItem -> {
            Product product = productmapper.getProductByID(orderItem.getProduct_id());
            System.out.println(product);
            if (product == null) {
                throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "该订单下商品未找到");
            }
            Operate operate = new Operate();
            operate.setUser_id(userLoginVO.getId());
            operate.setType(orderOperateDTO.getOperateCode());
            operate.setCategory_id(product.getCategory_id());
            operate.setProduct_id(orderItem.getProduct_id());
            int result2 = ordermapper.insertOperate(operate);
            if (result2 != 1) {
                throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "订单项操作失败");
            }
        });
    }

    @Override
    public OrderDetailVO getOrderDetail(UserLoginVO userLoginVO, Integer orderId, Integer productId) {
        System.out.println(productId);
        Product product = productmapper.getProductByID(productId);
        if (product == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "未找到该商品");
        }
        Order order = ordermapper.getOrderById(orderId);
        if (order == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "未找到该订单");
        }
        OrderItemDetailVO orderItemDetailVO = ordermapper.getOrderItemDetail(orderId, productId);
        if (orderItemDetailVO == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "订单项信息为空");
        }
        List<OrderOptVO> orderOptVOList = ordermapper.getOrderOpt(productId, userLoginVO.getId());
        if (orderOptVOList.isEmpty()) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "用户操作信息记录为空");
        }
        // 查询邮编
        Integer zip = ordermapper.getZip(order.getRecv_province(), order.getRecv_city(), order.getRecv_area());
        OrderDetailVO orderDetailVO = new OrderDetailVO();
        orderDetailVO.setOrderItemDetailVO(orderItemDetailVO);
        orderDetailVO.setOrderOptVOList(orderOptVOList);
        orderDetailVO.setZip(zip);
        orderDetailVO.setRecv_phone(order.getRecv_phone());
        orderDetailVO.setRecv_name(order.getRecv_name());
        orderDetailVO.setRecv_address(order.getRecv_address());
        return orderDetailVO;
    }

}
