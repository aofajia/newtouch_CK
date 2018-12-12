package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Welfare;

public interface WelfareMapper {
    int deleteByPrimaryKey(Integer uuid);

    int insert(Welfare record);

    int insertSelective(Welfare record);

    Welfare selectByPrimaryKey(Integer uuid);

    int updateByPrimaryKeySelective(Welfare record);

    int updateByPrimaryKey(Welfare record);

    String uuid();
}