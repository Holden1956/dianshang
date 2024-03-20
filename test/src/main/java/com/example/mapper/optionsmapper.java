package com.example.mapper;

import com.example.pojo.commonPojo.Option;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface optionsmapper {

    List<Option> getOptionsList();
}
