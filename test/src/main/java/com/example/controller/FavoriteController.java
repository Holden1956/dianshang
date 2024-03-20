package com.example.controller;

import com.example.common.JWTUtils;
import com.example.common.PageVO;
import com.example.common.R;
import com.example.pojo.dto.FavoritePageDTO;
import com.example.pojo.vo.ProductVO;
import com.example.pojo.vo.UserLoginVO;
import com.example.service.FavoriteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Date 2024/3/5 15:45
 * @Author qixuan he
 * @Description: 收藏模块控制层
 */
@RestController
@RequestMapping("api/favorite")// 一级
@Slf4j
@Api(tags = "收藏模块")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("list")
    @ApiOperation("查询所有收藏接口")
    public R<PageVO<List<ProductVO>>> getFavoriteList(@RequestBody @Validated FavoritePageDTO favoritePageDTO, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        UserLoginVO userLoginVO = JWTUtils.parseToken(token);
        // 调用业务层
        PageVO<List<ProductVO>> favoriteList = favoriteService.getFavoriteList(favoritePageDTO, userLoginVO);
        return R.ok(favoriteList);
    }

    /**
     * 添加收藏
     * @param product_id
     * @param request
     * @return
     */
    @PostMapping("add")
    @ApiOperation("添加收藏接口")
    public R<Void> addFavorite(Integer product_id,HttpServletRequest request){
        String token = request.getHeader("Authorization");
        UserLoginVO userLoginVO = JWTUtils.parseToken(token);
        favoriteService.addFavorite(product_id,userLoginVO);
        return R.ok();
    }

    /**
     * 取消收藏
     * @param product_id
     * @param request
     * @return
     */
    @DeleteMapping("cancel")
    @ApiOperation("删除藏品接口")
    public R<Void> cancelFavorite(Integer product_id,HttpServletRequest request){
        String token = request.getHeader("Authorization");
        UserLoginVO userLoginVO = JWTUtils.parseToken(token);
        favoriteService.cancelFavorite(product_id,userLoginVO);
        return R.ok();
    }
}
