package com.example.controller;

import com.example.common.JWTUtils;
import com.example.common.R;
import com.example.pojo.dto.CartAddDTO;
import com.example.pojo.dto.CartComputePriceDTO;
import com.example.pojo.dto.CartIdDTO;
import com.example.pojo.vo.*;
import com.example.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Date 2024/3/3 14:59
 * @Author qixuan he
 * @Description: 购物车操作控制类
 */
@RestController
@CrossOrigin(origins ={"http://localhost"},allowCredentials="true")
@RequestMapping("api/cart")// 一级
@Slf4j
@Api(tags = "购物车模块")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("list")
    @ApiOperation("购物车查询接口")
    public R<List<CartVO>> getCartList(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        System.out.println(token);
        // 解析token：知道用户的身份
        UserLoginVO loinUser = JWTUtils.parseToken(token);
        // 知道了是谁在修改地址
        return R.ok(cartService.getCartList(loinUser));
    }

    @GetMapping("computePrice")
    @ApiOperation("购物车计算选择商品价格接口")
    public R<Integer> computePrice(@RequestBody CartComputePriceDTO[] cartComputePriceDTOList, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        UserLoginVO userLoginVO = JWTUtils.parseToken(token);
        return R.ok(cartService.computePrice(cartComputePriceDTOList, userLoginVO));
    }

    @PostMapping("settlement")
    @ApiOperation("结算购物车接口")
    public R<CartSettlementVO> settlementCart(@RequestBody CartComputePriceDTO[] cartComputePriceDTOS, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        UserLoginVO userLoginVO = JWTUtils.parseToken(token);
        return R.ok(cartService.settlementCart(cartComputePriceDTOS, userLoginVO));
    }

    @PutMapping("update")
    @ApiOperation("更改数量接口")
    public R<CartNumVO> getCartUpList(HttpServletRequest request, @RequestBody CartIdDTO cartIdDTO) {
        String token = request.getHeader("Authorization");
        // 解析token：知道用户的身份
        UserLoginVO loinUser = JWTUtils.parseToken(token);
        return R.ok(cartService.getCartUpList(loinUser, cartIdDTO));
    }

    @GetMapping("onlinePay/{address_id}")
    @ApiOperation("在线支付")
    public R<CartOnlinePayVO> getOnlinePayInfo(@PathVariable Integer address_id,HttpServletRequest request){
        String token = request.getHeader("Authorization");
        // 解析token：知道用户的身份
        UserLoginVO loinUser = JWTUtils.parseToken(token);
        return R.ok(cartService.getOnlinePayInfo(loinUser,address_id));
    }

    @GetMapping("PayOk")
    @ApiOperation("确认付款")
    public R<CartOnlinePayVO> getPayOkInfo(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        // 解析token：知道用户的身份
        UserLoginVO loinUser = JWTUtils.parseToken(token);
        return R.ok(cartService.getPayOKInfo(loinUser));
    }
    /**
     * 删除购物车所选商品
     * @param product_id
     * @param request
     * @return
     */
    @DeleteMapping("delete/{product_id}")
    @ApiOperation("删除特定商品接口")
    public R<Void> delete(@PathVariable Integer product_id,HttpServletRequest request){
        //获取用户的身份
        String token = request.getHeader("Authorization");
        UserLoginVO userLoginVO = JWTUtils.parseToken(token);
        System.out.println("删除特定商品接口"+userLoginVO.getId());
        cartService.delete(product_id,userLoginVO);
        return R.ok();
    }
    /**
     * 删除所选所有商品
     * @param product_id
     * @param request
     * @return
     */
    @DeleteMapping("delete1/{product_id}")
    @ApiOperation("删除多项商品接口")
    public R<Void> delete1(@PathVariable List<Integer> product_id,  HttpServletRequest request){
        //获取用户的身份
        String token = request.getHeader("Authorization");
        UserLoginVO userLoginVO = JWTUtils.parseToken(token);
        cartService.delete1(product_id,userLoginVO);
        return R.ok();
    }

    @PostMapping("addIntoCart")
    @ApiOperation("加入购物车接口")
    public R<Void> addIntoCart(@RequestBody @Validated CartAddDTO cartAddDTO, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        UserLoginVO userLoginVO = JWTUtils.parseToken(token);
        cartService.addIntoCart(cartAddDTO,userLoginVO);
        return R.ok();
    }
}
