package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.MemberAdvance;

import java.util.Map;

public interface MemberAdvanceMapper {
    int deleteByPrimaryKey(Integer logId);

    int insert(MemberAdvance record);

    int insertSelective(MemberAdvance record);

    MemberAdvance selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(MemberAdvance record);

    int updateByPrimaryKey(MemberAdvance record);

    Map getMonthMoney(Map map);

    Map getOffjobMoney(Map map);
}