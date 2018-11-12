package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.BWConfigtype;

import java.util.List;
import java.util.Map;

public interface BWConfigtypeMapper {
    int deleteByPrimaryKey(String configtype);

    int insert(BWConfigtype record);

    int insertSelective(BWConfigtype record);

    BWConfigtype selectByPrimaryKey(String configtype);

    int updateByPrimaryKeySelective(BWConfigtype record);

    int updateByPrimaryKey(BWConfigtype record);

    List<BWConfigtype> selectConfigTypeAll();

    Map checkStoreName(String configname);
}