package com.example.controller;

import com.example.common.JWTUtils;
import com.example.common.PageVO;
import com.example.common.R;
import com.example.pojo.dto.ProductCategoryPageDTO;
import com.example.pojo.vo.ProductDetailVO;
import com.example.pojo.vo.ProductVO;
import com.example.pojo.vo.ProductWithFavoriteVO;
import com.example.pojo.vo.UserLoginVO;
import com.example.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Date 2024/3/4 10:30
 * @Author qixuan he
 * @Description: 商品控制层
 */
@RestController
@RequestMapping("api/product")
@Slf4j
@Api(tags = "商品模块")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("listNew") // /api/options/option?parent=1001
    @ApiOperation("获取新到好货接口")
    public R<List<ProductVO>> getNewProductList() {// 之所以没有加参数parent_id是因为前端会自动为查询结果分配父子关系导航条
        List<ProductVO> productVOList = productService.getNewList();
        return R.ok(productVOList);
    }

    @GetMapping("listHot") // /api/options/option?parent=1001
    @ApiOperation("获取热销商品接口")
    public R<List<ProductVO>> getHotProductList() {// 之所以没有加参数parent_id是因为前端会自动为查询结果分配父子关系导航条
        List<ProductVO> productVOList = productService.getHotList();
        return R.ok(productVOList);
    }

    @GetMapping("list")
    @ApiOperation("分类查询（带自定义分页功能）接口")
    public R<PageVO<List<ProductWithFavoriteVO>>> getListByCategoryId(@RequestBody @Validated ProductCategoryPageDTO categoryPageDTO , HttpServletRequest request) {

        // 获取用户的身份：请求头中是含有token的
        String token = request.getHeader("Authorization");
        // 解析token：知道用户的身份
        UserLoginVO loinUser = null;
        if(token!=null&& token!=""){
            //已经登录
            loinUser=JWTUtils.parseToken(token);
        }
        PageVO<List<ProductWithFavoriteVO>> pageVO = productService.getListByCategoryId(categoryPageDTO,loinUser);
        return R.ok(pageVO);
    }

    // @GetMapping("list2")// 分页，使用pageHelper实现
    // @ApiOperation("分页查询（pageHelper）接口")
    // public R<PageInfo<Product>> getListByCategoryId2(@RequestBody ProductCategoryPageDTO categoryPageDTO) {
    //     PageInfo<Product> productVOList = productService.getListByCategoryId(categoryPageDTO);
    //     return R.ok(productVOList);
    // }

    /**
     * 自定义分页
     * @param categoryPageDTO
     * @return
     */
    @GetMapping("list3")// 分页，自定义实现，更好
    @ApiOperation("分页查询（自定义）接口")
    public R<PageVO<List<ProductVO>>> getListByCategoryId3(@RequestBody ProductCategoryPageDTO categoryPageDTO) {
        PageVO<List<ProductVO>> listPageVO = productService.getListByCategoryId3(categoryPageDTO);
        return R.ok(listPageVO);
    }

    @GetMapping("{id}")
    @ApiOperation("查询商品详情接口")
    public R<ProductDetailVO> getById(@PathVariable Integer id, HttpServletRequest request){
        String token = request.getHeader("Authorization");

        UserLoginVO userLoginVO = null;
        if(token != null && token!=""){
            //已经登录
            userLoginVO = JWTUtils.parseToken(token);
        }
        ProductDetailVO detailVO = productService.getDetailById(id,userLoginVO);
        return R.ok(detailVO);
    }
}
