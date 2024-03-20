package com.example.controller;

import com.example.common.JWTUtils;
import com.example.common.R;
import com.example.pojo.dto.OrderOperateDTO;
import com.example.pojo.vo.OrderDetailVO;
import com.example.pojo.vo.OrderVO;
import com.example.pojo.vo.UserLoginVO;
import com.example.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Date 2024/3/5 16:23
 * @Author qixuan he
 * @Description: 订单控制器
 */
@RestController
@RequestMapping("api/order")// 一级
@Slf4j
@Api(tags = "订单模块")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("list/{status}")
    @ApiOperation("状态获取订单")
    public R<List<OrderVO>> getOrderList(HttpServletRequest request, @PathVariable Integer status) {
        String token = request.getHeader("Authorization");
        UserLoginVO userLoginVO = JWTUtils.parseToken(token);
        List<OrderVO> orderVOList = orderService.getList(userLoginVO, status);
        return R.ok(orderVOList);
    }

    @GetMapping("getDetail")
    @ApiOperation("订单详情")
    public R<OrderDetailVO> getOrderDetail(HttpServletRequest request, Integer order_id, Integer product_id) {
        String token = request.getHeader("Authorization");
        UserLoginVO userLoginVO = JWTUtils.parseToken(token);
        OrderDetailVO orderDetailVO = orderService.getOrderDetail(userLoginVO, order_id, product_id);
        return R.ok(orderDetailVO);
    }

    @RequestMapping("operate")
    @ApiOperation("订单操作")
    public R<Void> operateOrder(HttpServletRequest request, @RequestBody @Validated OrderOperateDTO orderOperateDTO) {
        String token = request.getHeader("Authorization");
        UserLoginVO userLoginVO = JWTUtils.parseToken(token);
        orderService.operateOrder(orderOperateDTO, userLoginVO);
        return R.ok();
    }
}
