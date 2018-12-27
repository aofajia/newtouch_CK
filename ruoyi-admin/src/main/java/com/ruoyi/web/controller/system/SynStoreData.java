package com.ruoyi.web.controller.system;

import com.ruoyi.system.mapper.BalanceRecordMapper;
import com.ruoyi.system.mapper.StoreOrdersMapper;
import com.ruoyi.system.service.ISynStoreDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * 定时任务调度
 *
 * @author ruoyi
 */
@Component("SynSotreData")
public class SynStoreData
{
    //记录日志
    private static final Logger syn_storedata_logger = LoggerFactory.getLogger("syn-storedata");
    @Autowired
    private ISynStoreDataService synStoreDataService;
    @Autowired
    private BalanceRecordMapper balanceRecordMapper;
    @Autowired
    private StoreOrdersMapper storeOrdersMapper;

    public void sumMemberadvance() {
        //每日零时统计所有用户余额
        try {
            int i = synStoreDataService.sumMemberadvance(syn_storedata_logger);
        } catch (Exception e) {
            syn_storedata_logger.info("每日零时统计所有用户余额程序报错，错误信息：" + e.toString());
            e.printStackTrace();
        }
    }

    public void getStoreAdvance()
    {
        //获取各供应商配置接口
        //调用接口获取数据
        try
        {
            synStoreDataService.getStoreAdvance(syn_storedata_logger);
        }
        catch (Exception e)
        {
            syn_storedata_logger.info("预存款供应商余额记录功能程序报错，错误信息："+e.toString());
            e.printStackTrace();
        }
    }

    public void getStoreOrders()
    {
        //获取各供应商配置接口
        //调用接口获取数据
        try
        {
            int getflag = 3;
            synStoreDataService.getStoreOrders(syn_storedata_logger,getflag);
        }
        catch (Exception e)
        {
            syn_storedata_logger.info("供应商订单同步功能程序报错，错误信息："+e.toString());
            e.printStackTrace();
        }
    }

    public void orderChecking()
    {
        //定时对账功能 在所有数据同步完成之后
        try
        {
            synStoreDataService.orderChecking(syn_storedata_logger);
        }
        catch (Exception e)
        {
            syn_storedata_logger.info("定时对账功能程序报错，错误信息："+e.toString());
            e.printStackTrace();
        }
    }

}