package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Storemanger;
import com.ruoyi.system.domain.StoremangerWithBLOBs;

import java.util.List;

public interface StoremangerMapper {
    int deleteByPrimaryKey(Long storeId);

    int insert(StoremangerWithBLOBs record);

    int insertSelective(StoremangerWithBLOBs record);

    StoremangerWithBLOBs selectByPrimaryKey(Long storeId);

    int updateByPrimaryKeySelective(StoremangerWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(StoremangerWithBLOBs record);

    int updateByPrimaryKey(Storemanger record);

    List<Storemanger> selectAllStore();
}