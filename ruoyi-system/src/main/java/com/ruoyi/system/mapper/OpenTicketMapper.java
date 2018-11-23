package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.OpenTicketInfoCollect;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface OpenTicketMapper {

    //根据供应商，开始日期，结束日期查询每个公司的汇总信息
    List<OpenTicketInfoCollect> list(@Param("supplier") String supplier,@Param("startDate") String startDate,@Param("endDate") String endDate);

    //根据供应商，开始日期，结束日期查询每个订单的信息
    List<OpenTicketInfoCollect> listOrders(@Param("id") String id,@Param("supplier") String supplier,@Param("startDate") String startDate,@Param("endDate") String endDate);

    Double order_num(@Param("supplier") String supplier, @Param("startDate") String startDate, @Param("endDate") String endDate);

    //查询发票抬头
    List<OpenTicketInfoCollect> selectInvoice();

    //表同步
    Integer tableWith();

    //创建表
    Integer tableCreate();

    //添加数据
    Integer tableInsert();

    //修改发票抬头
    Integer updateTicketRise(@Param("id") long id,@Param("rise")String rise);

    //根据发票抬头查询比率,供应商，开始日期，结束日期查询每个公司的汇总信息
    List<OpenTicketInfoCollect> listByRise(@Param("supplier") String supplier,@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("rise")String rise);
}
