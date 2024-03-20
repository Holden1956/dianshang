package com.example.controller;

import com.example.pojo.commonPojo.Opt;
import com.example.pojo.vo.OrderVO;
import com.example.pojo.vo.ProductViewVO;
import com.example.service.BackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Date 2024/3/13 22:44
 * @Author qixuan he
 * @Description: 后台系统控制层
 */
@RestController
@RequestMapping("api/back")// 一级
@Slf4j
@Api(tags = "后台模块")
@CrossOrigin
public class BackController {
    @Autowired
    private BackService backService;

    @GetMapping("orderView")
    @ApiOperation("order统计查询接口")
    public List<OrderVO> getAddressList() {
        List<OrderVO> orderVOList = backService.getOrderList();
        return orderVOList;
    }

    @GetMapping("user/{user_id}")
    @ApiOperation("用户跟踪")
    public List<Opt> getUserData(@PathVariable Integer user_id) {
        // 在这里编写获取用户数据的逻辑，假设您已经实现了该逻辑并且能够根据用户ID查询到相应的数据
        // 假设您的数据存储在某个服务或数据库中，您可以调用相应的服务或DAO来获取数据
        List<Opt> userData = backService.getUserDataByUserId(user_id);
        return userData;
    }

    @GetMapping("user_actions")
    @ApiOperation("用户跟踪")
    public List<Opt> getAllUserData() {
        // 在这里编写获取用户数据的逻辑，假设您已经实现了该逻辑并且能够根据用户ID查询到相应的数据
        // 假设您的数据存储在某个服务或数据库中，您可以调用相应的服务或DAO来获取数据
        List<Opt> userData = backService.getAllUserDataByUserId();
        return userData;
    }

    @GetMapping("productView")
    @ApiOperation("商品浏览量统计")
    public List<ProductViewVO> getProductViewCount() {
        List<ProductViewVO> productViewVOS = backService.getProductView();
        return productViewVOS;
    }

    @PutMapping("updateStatus/{id}/{status}")
    @ApiOperation("更新商品上架状态")
    public List<ProductViewVO> getProductViewCount(@PathVariable Integer id,@PathVariable Integer status) {
        List<ProductViewVO> productViewVOS = backService.updateProductStatusByPID(id,status);
        return productViewVOS;
    }

    @PutMapping("updatePriority/{id}/{priority}")
    @ApiOperation("更新商品优先级状态")
    public List<ProductViewVO> getProductViewCount2(@PathVariable Integer id,@PathVariable Integer priority) {
        List<ProductViewVO> productViewVOS = backService.updateProductpriorityByPID(id,priority);
        return productViewVOS;
    }

    @DeleteMapping("products/{productId}")
    @ApiOperation("删除商品")
    public List<ProductViewVO> getProductViewCount3(@PathVariable Integer productId) {
        List<ProductViewVO> productViewVOS = backService.delProduct(productId);
        return productViewVOS;
    }
}
