package com.example.mapper;

import com.example.pojo.commonPojo.Opt;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface optmapper {

    int createdOpt(Opt opt);
    List<Opt> getUserOptByUserID(Integer user_id);

    Integer getViewCountByProductId(Integer product_id);

    List<Opt> getAllUserAction();
}
