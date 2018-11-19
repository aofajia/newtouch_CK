package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.BWConfig;
import com.ruoyi.system.domain.StoreConfig;

import java.util.List;
import java.util.Map;

public interface BWConfigMapper {
    int deleteByPrimaryKey(String id);

    int insert(BWConfig record);

    int insertSelective(BWConfig record);

    BWConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BWConfig record);

    int updateByPrimaryKey(BWConfig record);

    Map checkConfigName(String configName);

    List<BWConfig> selectConfigAll();

    Map checkConfigNameExceptMe(BWConfig bwConfig);

    List<StoreConfig> selectStoreConfigAll();
}