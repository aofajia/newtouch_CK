package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.BWConfigLOG;

public interface BWConfigLOGMapper {
    int deleteByPrimaryKey(String logid);

    int insert(BWConfigLOG record);

    int insertSelective(BWConfigLOG record);

    BWConfigLOG selectByPrimaryKey(String logid);

    int updateByPrimaryKeySelective(BWConfigLOG record);

    int updateByPrimaryKey(BWConfigLOG record);
}