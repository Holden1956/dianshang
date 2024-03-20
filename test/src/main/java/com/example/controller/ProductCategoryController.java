package com.example.controller;

import com.example.common.R;
import com.example.pojo.vo.ProductCategoryVO;
import com.example.service.ProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ProjectName: test
 * @Titile: ProductCategoryController
 * @Author: Lucky
 * @Description: 商品分类控制器
 */
@RestController
@RequestMapping("api/category")
@Slf4j
@Api(tags = "产品类型模块")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 查询分类
     * @return
     */
    @GetMapping("list")
    @ApiOperation("获取全部产品类型接口")
    public R<List<ProductCategoryVO>> getList() {
        List<ProductCategoryVO> list = productCategoryService.getList();
        return R.ok(list);
    }
}
