package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.StoreOrders;

public interface StoreOrdersMapper {
    int deleteByPrimaryKey(String orderid);

    int insert(StoreOrders record);

    int insertSelective(StoreOrders record);

    StoreOrders selectByPrimaryKey(String orderid);

    int updateByPrimaryKeySelective(StoreOrders record);

    int updateByPrimaryKey(StoreOrders record);
}