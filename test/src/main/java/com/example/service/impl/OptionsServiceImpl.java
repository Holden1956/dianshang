package com.example.service.impl;

import com.example.common.ServiceCode;
import com.example.common.ServiceException;
import com.example.mapper.optionsmapper;
import com.example.pojo.commonPojo.Option;
import com.example.pojo.vo.OptionsVO;
import com.example.service.OptionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2024/3/2 9:20
 * @Author qixuan he
 * @Description: 导航条业务层
 */
@Service// 服务层
@Transactional // 开启事务
@Slf4j// 日志
public class OptionsServiceImpl implements OptionsService {
    @Autowired
    private optionsmapper optionsmapper;

    @Override
    public List<OptionsVO> getOptionList() {
        List<Option> optionList = optionsmapper.getOptionsList();
        if (optionList.isEmpty()) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "没有数据");
        }
        List<OptionsVO> optionsVOList = new ArrayList<>();
        // 封装
        optionList.forEach(option -> {
            OptionsVO optionsVO = new OptionsVO();

            optionsVO.setId(option.getId());
            optionsVO.setParent_id(option.getParent_id());
            optionsVO.setName(option.getName());
            optionsVOList.add(optionsVO);
        });
        return optionsVOList;
    }
}
