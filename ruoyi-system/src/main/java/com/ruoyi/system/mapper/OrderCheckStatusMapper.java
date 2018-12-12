package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.OrderCheckStatus;

public interface OrderCheckStatusMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(OrderCheckStatus record);

    int insertSelective(OrderCheckStatus record);

    OrderCheckStatus selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(OrderCheckStatus record);

    int updateByPrimaryKey(OrderCheckStatus record);
}