package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.DifferenceOrderList;
import com.ruoyi.system.domain.Orders;
import com.ruoyi.system.domain.OrdersWithBLOBs;

import java.util.List;
import java.util.Map;

public interface OrdersMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(OrdersWithBLOBs record);

    int insertSelective(OrdersWithBLOBs record);

    OrdersWithBLOBs selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(OrdersWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(OrdersWithBLOBs record);

    int updateByPrimaryKey(Orders record);

    Map getValidOrderSum(Map map);

    Map getEmployeePayMoney(Map map);

    List<DifferenceOrderList> gatDifferenceOrderList(Map map);
}