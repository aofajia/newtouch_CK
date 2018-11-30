package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.BalanceRecord;

import java.util.List;
import java.util.Map;

public interface BalanceRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(BalanceRecord record);

    int insertSelective(BalanceRecord record);

    BalanceRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BalanceRecord record);

    int updateByPrimaryKey(BalanceRecord record);

    List<BalanceRecord> selectBySupplierId(Map map);
}