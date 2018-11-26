package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.IOrderCheckingService;
import com.ruoyi.system.tool.BigDecimalUtils;
import com.ruoyi.system.tool.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderCheckingServiceImpl implements IOrderCheckingService
{
    @Autowired
    private StoremangerMapper storemangerMapper;
    @Autowired
    private BWConfigMapper bwConfigMapper;
    @Autowired
    private MemberAdvanceMapper memberAdvanceMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private RechargeLogMapper rechargeLogMapper;
    @Autowired
    private BalanceRecordMapper balanceRecordMapper;
    @Autowired
    private CheckingLogMapper checkingLogMapper;

    @Override
    public List<Storemanger> selectStoreAll()
    {
        List<Storemanger> storemangers = storemangerMapper.selectAllStore();

        return storemangers;
    }

    @Override
    public List<StoreConfig> selectStoreConfigList()
    {
        List<StoreConfig> storeConfigs = bwConfigMapper.selectStoreConfigAll();
        return storeConfigs;
    }

    @Override
    public Map chinkingMemberAdvance(String startTime)
    {
        /*Map<String, Object> paramMap = new HashMap();
        paramMap.put("startTime",startTime);
        //存入金额,支出金额
        Map imorexmoney = memberAdvanceMapper.getMonthMoney(paramMap);
        paramMap.put("memo","每月充值");
        //每月充值
        Map monthMoney = memberAdvanceMapper.getMonthMoney(paramMap);
        //离职提现金额
        paramMap.replace("memo","离职提现");
        Map offjobMoney = memberAdvanceMapper.getMonthMoney(paramMap);

        Map<String, Object> responseMap = new HashMap();

        //收入金额
        double importmoney = BigDecimalUtils.formatBigDecimalToDouble(imorexmoney.get("import_money").toString());
        //支出金额
        double explodemoney = BigDecimalUtils.formatBigDecimalToDouble(imorexmoney.get("explode_money").toString());
        //每月充值
        double monthmoney = BigDecimalUtils.formatBigDecimalToDouble(monthMoney.get("import_money").toString());
        //离职提现金额
        double offjobmoney = BigDecimalUtils.formatBigDecimalToDouble(offjobMoney.get("explode_money").toString());

        //存入差值
        double minu_money = BigDecimalUtils.formatBigDecimalToDouble(importmoney - monthmoney);
        double employeepaymoney = BigDecimalUtils.formatBigDecimalToDouble(explodemoney - minu_money - offjobmoney);
        double employeeresiduemoney = BigDecimalUtils.formatBigDecimalToDouble(monthmoney - employeepaymoney);*/

        Map<String, String> paramMap = new HashMap();
        paramMap.put("startTime",startTime);

        //员工消费总金额
        Map employeePayMoney = ordersMapper.getEmployeePayMoney(paramMap);
        String employeepaymoney = employeePayMoney.get("employeepaymoney").toString();
        BigDecimal bpaymoney = new BigDecimal(employeepaymoney);

        //员工剩余账户总金额
        paramMap.put("supplierid","Mall");
        List<BalanceRecord> balanceRecords = balanceRecordMapper.selectBySupplierId(paramMap);
        BalanceRecord balanceRecord = balanceRecords.get(0);
        BigDecimal employeeresiduemoney = balanceRecord.getBalancemoney();
        paramMap.remove("supplierid");

        //申请提现额度
        String url = "http://59.80.30.153:4090/方法名";
        HttpClientUtil httpUtil=new HttpClientUtil();
        String str=httpUtil.doPost(url,paramMap,"UTF-8");
        JSONObject result = JSONObject.parseObject(str);
        String offjobmoney = "999.99";
        BigDecimal bleavemoney = new BigDecimal("999.990");
//        String offjobmoney = result.getString("");
//        BigDecimal bleavemoney = new BigDecimal(offjobmoney);

        //每月充值
        paramMap.put("memo","每月充值");
        Map monthMoney = memberAdvanceMapper.getMonthMoney(paramMap);
        String monthmoney = monthMoney.get("import_money").toString();
        BigDecimal bmonthmoney = new BigDecimal(monthmoney);

        //判断商城恒等式是否相等
        String flag = "";
        BigDecimal formerSum = bpaymoney.add(employeeresiduemoney).add(bleavemoney);
        int i = formerSum.compareTo(bmonthmoney);
        if (i < 0)
        {
            flag = "<";
        }
        if (i == 0)
        {
            flag = "=";
        }
        if (i > 0)
        {
            flag = ">";
        }

        Map<String, Object> responseMap = new HashMap();
        //员工消费总金额
        responseMap.put("employee_paymoney",bpaymoney   );
        //员工剩余账户总金额
        responseMap.put("employee_residuemoney",employeeresiduemoney);
        //申请提现额度
        responseMap.put("employee_leavemoney",bleavemoney);
        //商城充值总金额
        responseMap.put("mall_rechargemoney",bmonthmoney);
        //恒等式平衡标志位
        responseMap.put("flag",flag);
        return responseMap;
    }

    @Override
    public List storeChecking(String startTime)
    {
        List<Map> responseList = new ArrayList<>();

        List<StoreConfig> storeConfigs = selectStoreConfigList();
        for (int i = 0; i < storeConfigs.size() ; i++)
        {
            StoreConfig storeConfig = storeConfigs.get(i);
            String store_id = storeConfig.getStore_id();
            String shop_name = storeConfig.getShop_name();
            String paymethod = storeConfig.getPaymethod();
            Map<String, Object> paramMap = new HashMap();
            paramMap.put("store_id",store_id);
            paramMap.put("startTime",startTime);

            Map<String, Object> responseMap = new HashMap();
            responseMap.put("store_id",store_id);
            responseMap.put("shop_name",shop_name);
            responseMap.put("paymethod",paymethod);
            if("precharge".equals(paymethod))
            {
                //预充值供应商
                //获取有效订单总金额
                Map validOrderSum = ordersMapper.getValidOrderSum(paramMap);
                String validmoney = validOrderSum.get("validmoney").toString();
                BigDecimal valid = new BigDecimal(validmoney);
                responseMap.put("validmoney",validmoney);

                //获取当前时间供应商余额记录表中的余额(定时任务 从供应商接口获取)
                String storebalance = "999.99";
                BigDecimal balance = new BigDecimal(storebalance);
                responseMap.put("storebalance",storebalance);

                //从供应商充值记录表中获取 对应供应商累计充值总金额值总金额
                Map rechargeSum = rechargeLogMapper.selectRechargeSumByStore(paramMap);
                String rechargemoney = rechargeSum.get("rechargemoney").toString();
                BigDecimal recharge = new BigDecimal(rechargemoney);
                responseMap.put("rechargemoney",rechargemoney);

                //判断供应商恒等式是否相等
                String flag = "";
                BigDecimal storeSum = valid.add(balance);
                int j = storeSum.compareTo(recharge);
                if (j == 0)
                {
                    flag = "=";
                    CheckingLog checkingLog = new CheckingLog();
                    checkingLog.setId(UUID.randomUUID().toString().toUpperCase());
                    checkingLog.setCheckingdate(startTime);
                    checkingLog.setCheckingstatus(0);
                    checkingLog.setSupplierid(store_id);
                    checkingLog.setSuppliername(shop_name);
                    Date nowdate = new Date();
                    checkingLog.setCreatetime(nowdate);
                    checkingLogMapper.insert(checkingLog);
                }
            }
            else if ("monthly".equals(paymethod))
            {
                //月结
                //获取有效订单总金额
                Map validOrderSum = ordersMapper.getValidOrderSum(paramMap);
                String validmoney = validOrderSum.get("validmoney").toString();
                BigDecimal valid = new BigDecimal(validmoney);
                responseMap.put("paymethod",validmoney);

                //获取当前时间 供应商订单总额(定时任务 从供应商接口处获取所有订单 并记录表(供应商ID 订单号 订单时间 金额))
                String storeordersmoney = "123.12";
                BigDecimal rdersmoney = new BigDecimal(storeordersmoney);
                responseMap.put("storeordersmoney",storeordersmoney);

                //判断供应商恒等式是否相等
                String flag = "";
                int j = valid.compareTo(rdersmoney);
                if (j == 0)
                {
                    flag = "=";
                    CheckingLog checkingLog = new CheckingLog();
                    checkingLog.setId(UUID.randomUUID().toString().toUpperCase());
                    checkingLog.setCheckingdate(startTime);
                    checkingLog.setCheckingstatus(0);
                    checkingLog.setSupplierid(store_id);
                    checkingLog.setSuppliername(shop_name);
                    Date nowdate = new Date();
                    checkingLog.setCreatetime(nowdate);
                    checkingLogMapper.insert(checkingLog);
                }
            }
            responseList.add(responseMap);
        }
        return responseList;
    }

    @Override
    public List gatDifferenceOrderList(String id, String startTime)
    {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("id",id);
        paramMap.put("startTime",startTime);
        List<DifferenceOrderList> list = ordersMapper.gatDifferenceOrderList(paramMap);

        Date nowdate = new Date();
        for (int i = 0; i < list.size(); i++)
        {
            CheckingLog checkingLog = new CheckingLog();
            checkingLog.setId(UUID.randomUUID().toString().toUpperCase());
            checkingLog.setCheckingdate(startTime);
            checkingLog.setCheckingstatus(1);
            checkingLog.setSupplierid(id);
            checkingLog.setSuppliername(list.get(i).getStorename());
            checkingLog.setOrderId(list.get(i).getOrder_id());
            checkingLog.setPayed(list.get(i).getPayed());
            checkingLog.setOtherOrderId(list.get(i).getOther_order_id());
            checkingLog.setOtherPayed(list.get(i).getOther_payed());
            checkingLog.setCreatetime(nowdate);
            checkingLogMapper.insert(checkingLog);
        }
        return list;
    }
}
