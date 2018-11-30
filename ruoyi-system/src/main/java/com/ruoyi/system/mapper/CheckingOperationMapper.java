package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.CheckingOperation;

import java.util.List;

public interface CheckingOperationMapper {
    int deleteByPrimaryKey(String mainid);

    int insert(CheckingOperation record);

    int insertSelective(CheckingOperation record);

    CheckingOperation selectByPrimaryKey(String mainid);

    int updateByPrimaryKeySelective(CheckingOperation record);

    int updateByPrimaryKey(CheckingOperation record);

    List<CheckingOperation> selectByStartTime(String startTime);
}