package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.OpenTicketInfoCollect;
import com.ruoyi.system.mapper.OpenTicketMapper;
import com.ruoyi.system.service.OpenTicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<OpenTicketInfoCollect> list() {
        List<OpenTicketInfoCollect> list = new ArrayList<>();
        try {
            //获取总金额
            Integer i = openTicketMapper.order_num();
            //获取订单信息
            list = openTicketMapper.list();
            for (OpenTicketInfoCollect collect : list) {
                Double d = sumAccount(collect.getMoney_num(), i);
                collect.setAccount(getTwoDecimal(d) + "%");

            }
        } catch (Exception e) {
            logger.debug("开票信息初始化失败！" + e.getMessage());
        }
        return list;
    }


    /**
     * 计算百分比
     */
    private Double sumAccount(Integer i, Integer j) {
        return i * 1.0 / j;
    }

    private String getTwoDecimal(Double d){
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(d);
    }


}
