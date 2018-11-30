package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.CheckingMall;

import java.util.List;

public interface CheckingMallMapper {
    int deleteByPrimaryKey(String detailid);

    int insert(CheckingMall record);

    int insertSelective(CheckingMall record);

    CheckingMall selectByPrimaryKey(String detailid);

    int updateByPrimaryKeySelective(CheckingMall record);

    int updateByPrimaryKey(CheckingMall record);

    List<CheckingMall> selectByMainID(String mainid);
}