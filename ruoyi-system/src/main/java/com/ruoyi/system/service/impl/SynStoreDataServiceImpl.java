package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.IBalanceWarningService;
import com.ruoyi.system.service.IOrderCheckingService;
import com.ruoyi.system.service.ISynStoreDataService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class SynStoreDataServiceImpl implements ISynStoreDataService
{
    @Autowired
    private MembersMapper membersMapper;
    @Autowired
    private BalanceRecordMapper balanceRecordMapper;
    @Autowired
    private IOrderCheckingService orderCheckingService;
    @Autowired
    private CheckingOperationMapper checkingOperationMapper;
    @Autowired
    private CheckingMallMapper checkingMallMapper;
    @Autowired
    private CheckingStoreMapper checkingStoreMapper;

    @Override
    public int sumMemberadvance(Logger syn_storedata_logger)
    {
        BalanceRecord balanceRecord = membersMapper.sumMemberadvance();
        balanceRecord.setId(UUID.randomUUID().toString().toUpperCase());
        balanceRecord.setSupplierid("000");
        balanceRecord.setSupplierid("Mall");
        balanceRecord.setSuppliername("商城");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        balanceRecord.setCommitdate(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        balanceRecord.setDate(sdf.format(date));

        // 打印信息到日志
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        syn_storedata_logger.info(sdf1.format(new Date())+"统计所有用户余额信息："+balanceRecord);

        int insert = balanceRecordMapper.insert(balanceRecord);
        return insert;
    }

    @Override
    public void orderChecking(Logger syn_storedata_logger)
    {
        // 打印信息到日志
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        String startTime = sdf.format(date);
        syn_storedata_logger.info(sdf1.format(new Date())+"自动对账开始，对账截至日期为："+startTime);

        //调用商城恒等式对账方法
        Map map = orderCheckingService.chinkingMemberAdvance(startTime,0);

        //主ID 操作批次ID
        String mainid = map.get("mainid").toString();
        /*//插入对账操作记录表
        CheckingOperation checkingOperation = new CheckingOperation();
        checkingOperation.setMainid(mainid);
        checkingOperation.setCheckingdate(startTime);
        checkingOperation.setCheckingtype("0");
        checkingOperationMapper.insert(checkingOperation);
        syn_storedata_logger.info(sdf1.format(new Date())+"对账操作记录信息："+checkingOperation);*/

        /*//插入商城恒等式记录表
        CheckingMall checkingMall = new CheckingMall();
        checkingMall.setDetailid(UUID.randomUUID().toString().toUpperCase());
        checkingMall.setMianid(mainid);
        checkingMall.setCheckingdate(startTime);
        checkingMall.setCheckingstatus((Integer) map.get("intflag"));
        checkingMall.setPaymoney((BigDecimal) map.get("employee_paymoney"));
        checkingMall.setResiduemoney((BigDecimal) map.get("employee_residuemoney"));
        checkingMall.setLeavemoney((BigDecimal) map.get("employee_leavemoney"));
        checkingMall.setWelfaremoney((BigDecimal) map.get("mall_rechargemoney"));
        checkingMallMapper.insert(checkingMall);
        syn_storedata_logger.info(sdf1.format(new Date())+"商城恒等式记录信息："+checkingMall);*/


        //调用供应商恒等式对账方法
        List<Map> storeDateList = orderCheckingService.storeChecking(startTime,mainid);
        /*//插入供应商恒等式记录表
        for (int i = 0; i < storeDateList.size(); i++)
        {
            //供应商恒等式数据
            Map storeDate = storeDateList.get(i);

            CheckingStore checkingStore = new CheckingStore();
            checkingStore.setDetailid(UUID.randomUUID().toString().toUpperCase());
            checkingStore.setMianid(mainid);
            checkingStore.setCheckingdate(startTime);
            checkingStore.setCheckingstatus((Integer) storeDate.get("intflag"));
            checkingStore.setSupplierid(storeDate.get("store_id").toString());
            checkingStore.setSuppliername(storeDate.get("shop_name").toString());
            String paymethod = storeDate.get("paymethod").toString();
            checkingStore.setPaymethod(paymethod);
            checkingStore.setValidmoney((BigDecimal) storeDate.get("validmoney"));
            if("precharge".equals(paymethod))
            {
                checkingStore.setStorebalance((BigDecimal) storeDate.get("storebalance"));
                checkingStore.setRechargemoney((BigDecimal) storeDate.get("rechargemoney"));
            }
            else if("monthly".equals(paymethod))
            {
                checkingStore.setStoreordersmoney((BigDecimal) storeDate.get("storeordersmoney"));
            }
            checkingStoreMapper.insert(checkingStore);
            syn_storedata_logger.info(sdf1.format(new Date())+"供应商恒等式记录信息："+checkingStore);*/

        for (int i = 0; i < storeDateList.size(); i++)
        {
            //需要先判断恒等式是否相等 在调用订单详细对账

            if((Integer)storeDateList.get(i).get("intflag") != 0)
            {
                //调用订单详细对账方法
                orderCheckingService.gatDifferenceOrderList(storeDateList.get(i).get("store_id").toString(),startTime,mainid);
            }
        }

        syn_storedata_logger.info(sdf1.format(new Date())+"自动对账结束");
    }
}
