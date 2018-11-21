package com.ruoyi.system.service.impl;

import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.system.domain.OpenTicketInfoCollect;
import com.ruoyi.system.mapper.OpenTicketMapper;
import com.ruoyi.system.service.OpenTicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpenTicketServiceImpl implements OpenTicketService {

    //注入开票订单表
    @Autowired
    private OpenTicketMapper openTicketMapper;

    //记录日志
    Logger logger = LoggerFactory.getLogger(OpenTicketServiceImpl.class);

    @Override
    public List<OpenTicketInfoCollect> list(String supplier,String startDate,String endDate) {
        List<OpenTicketInfoCollect> list = new ArrayList<>();
        try {
            //获取总金额
            Double i = openTicketMapper.order_num(supplier,startDate,endDate);
            //获取订单信息
            list = openTicketMapper.list(supplier,startDate,endDate);
            for (OpenTicketInfoCollect collect : list) {
                Double d = sumAccount(collect.getMoney_num(), i);
                collect.setAccount(getTwoDecimal(d) + "%");
            }
        } catch (Exception e) {
            logger.debug("开票信息初始化失败！" + e.getMessage());
        }
        return list;
    }

    @Override
    public List<OpenTicketInfoCollect> orderList(String id,String supplier, String startDate, String endDate) {
        List<OpenTicketInfoCollect> list = new ArrayList<>();
        try{
            //使开始日期和结束日期为null
            if (startDate.equals("0") || endDate.equals("0")){
                startDate = null;
                endDate = null;
            }
            //获取订单明细信息
            list = openTicketMapper.listOrders(id,supplier,startDate,endDate);
        }catch (Exception e){
            logger.debug("获取开票订单信息失败!"+e.getMessage());
        }
        return list;
    }

    @Override
    public List<OpenTicketInfoCollect> selectInvoice() {
        List<OpenTicketInfoCollect> list = new ArrayList<>();
        try{
            list = openTicketMapper.selectInvoice();
        }catch (Exception e){
            logger.debug("查询开票抬头信息失败！"+e.getMessage());
        }
        return list;
    }

    @Transactional
    @Override
    public Integer tableWith() {
        Integer i = null;
        try{
           openTicketMapper.tableWith();
           openTicketMapper.tableCreate();
           openTicketMapper.tableInsert();
           i = 1;
        }catch (Exception e){
            logger.debug("表同步数据失败！");
        }
        return i;
    }

    @Override
    public AjaxResult changeTicketRise(List<OpenTicketInfoCollect> list,String rise) {
        try{
            for (OpenTicketInfoCollect collect:list) {
                openTicketMapper.updateTicketRise(collect.getOrdersId(),rise);
            }
        }catch (Exception e){
            logger.debug("调整发票信息失败！"+e.getMessage());
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }


    /**
     * 计算百分比
     */
    private Double sumAccount(Double i, Double  j) {
        return i * 1.0 / j;
    }

    /**
     * 保留两位小数
     */
    private String getTwoDecimal(Double d){
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(d);
    }


}
