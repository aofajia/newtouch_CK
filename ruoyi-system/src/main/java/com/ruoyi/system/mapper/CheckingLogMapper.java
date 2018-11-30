package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.CheckingLog;
import com.ruoyi.system.domain.CheckingMall;

import java.util.List;

public interface CheckingLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(CheckingLog record);

    int insertSelective(CheckingLog record);

    CheckingLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CheckingLog record);

    int updateByPrimaryKey(CheckingLog record);

    List<CheckingLog> selectByMainID(String mainid);
}