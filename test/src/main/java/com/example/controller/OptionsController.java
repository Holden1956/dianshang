package com.example.controller;

import com.example.common.R;
import com.example.pojo.vo.OptionsVO;
import com.example.service.OptionsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Date 2024/3/2 9:18
 * @Author qixuan he
 * @Description: 导航条控制层
 */
@RestController
@RequestMapping("api/option")// 一级
@Slf4j
@Api(tags = "导航模块")
public class OptionsController {
    @Autowired
    private OptionsService optionsService;

    @GetMapping("list") // /api/options/option?parent=1001
    @ApiOperation("获取全导航接口")
    public R<List<OptionsVO>> getOptionList() {//之所以没有加参数parent_id是因为前端会自动为查询结果分配父子关系导航条
        List<OptionsVO> optionsVOList = optionsService.getOptionList();
        return R.ok(optionsVOList);
    }

}
