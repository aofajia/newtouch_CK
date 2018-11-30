package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.CheckingStore;

import java.util.List;

public interface CheckingStoreMapper {
    int deleteByPrimaryKey(String detailid);

    int insert(CheckingStore record);

    int insertSelective(CheckingStore record);

    CheckingStore selectByPrimaryKey(String detailid);

    int updateByPrimaryKeySelective(CheckingStore record);

    int updateByPrimaryKey(CheckingStore record);

    List<CheckingStore> selectByMainID(String mainid);
}