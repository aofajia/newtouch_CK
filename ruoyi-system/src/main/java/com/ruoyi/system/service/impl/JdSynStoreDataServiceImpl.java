package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.Md5Utils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.BalanceRecord;
import com.ruoyi.system.domain.MallInfos;
import com.ruoyi.system.domain.StoreOrders;
import com.ruoyi.system.mapper.BalanceRecordMapper;
import com.ruoyi.system.service.JdSynStoreDataService;
import com.ruoyi.system.service.MallInfosService;
import com.ruoyi.system.service.StoreOrdersService;
import com.ruoyi.system.utils.NumberArithmeticUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)
public class JdSynStoreDataServiceImpl implements JdSynStoreDataService {
    /**
     * 订单查询接口
     */
    private static final String JD_QUERY_ORDER = "http://test.third-party.newtouch.com/jdmp/ntpmp-api/query-order";
    /**
     * 余额查询接口
     */
    private static final String JD_QUERY_BALANCE = "http://test.third-party.newtouch.com/jdmp/ntpmp-api/query-balance";

    /**
     * 查询京东订单信息接口
     */
    private static final String JD_SELECT_JDORDER = "http://test.third-party.newtouch.com/jdmp/ntpmp-api/select-jdorder";


    @Autowired
    MallInfosService mallInfosService;

    @Autowired
    StoreOrdersService storeOrdersService;

    @Autowired
    BalanceRecordMapper balanceRecordMapper;

    /**
     * 订单查询，并且将查询数据保存到hrfi_storeorders
     *
     * @param syn_storedata_logger 日志信息
     * @param startTime            调试开始时间
     * @param productType          新建订单=1（包含2，3） ;妥投=2;撤单=3
     * @param pageNo               页码
     * @param jdOrderIdIndex       京东日单大于10000条时启用该字段，具体参见接口文档
     * @param eachIndex            接口调用异常时重复调用次数
     */
    public void queryOrder(Logger syn_storedata_logger, Date startTime, String productType, Integer pageNo, String jdOrderIdIndex, int eachIndex) {
        if (StringUtils.isBlank(productType)) {
            productType = "1";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        MallInfos infos = mallInfosService.queryMallInfo();
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
        StringBuffer stringBuffer = new StringBuffer();
        long time = System.currentTimeMillis();
        stringBuffer.append(time);
        JSONObject jo = new JSONObject();
        jo.put("appId", infos.getAppId());
        jo.put("date", sdf.format(startTime));
        jo.put("searchType", productType);
        jo.put("pageNo", Integer.valueOf(pageNo));
        jo.put("pageSize", "100");
        jo.put("jdOrderIdIndex", jdOrderIdIndex);
        stringBuffer.append(jo.toString());
        stringBuffer.append(infos.getSign());
        String md5 = Md5Utils.string2MD5(stringBuffer.toString());
        paramMap.put("appId", infos.getAppId());
        paramMap.put("timestamp", time);
        paramMap.put("sign", md5.toUpperCase());
        try {
            String jsonobj = NumberArithmeticUtils.sendPost(JD_QUERY_ORDER, paramMap, "utf-8", "application/json", jo.toString());
            syn_storedata_logger.info("调用京东查询订单明细接口完成，获取信息：" + jsonobj);

            JSONObject jsonObject = new JSONObject(jsonobj);
            boolean success = jsonObject.getBoolean("success");
            if (!success) {
                if (eachIndex-- > 0) {
                    syn_storedata_logger.info("调用京东查询订单明细接口错误，错误信息：" + jsonObject.getString("resultMessage"));
                    queryOrder(syn_storedata_logger, startTime, productType, pageNo, jdOrderIdIndex, eachIndex);
                }
            } else {
                JSONObject result = jsonObject.getJSONObject("result");
                int total = result.getInt("total");
                int totalPage = result.getInt("totalPage");
                JSONArray orders = result.getJSONArray("orders");
                for (int j = 0; j < orders.length(); j++) {
                    JSONObject order = orders.getJSONObject(j);
                    String jdOrderId = Long.toString(order.getLong("jdOrderId"));
                    BigDecimal amount = order.getBigDecimal("orderPrice");
                    String state = Long.toString(order.getLong("state"));
                    String time1 = order.getString("time");
                    Date utilDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time1);
                    Date date1 = new java.sql.Date(utilDate.getTime());

                    BigDecimal freight = selectJdOrder(syn_storedata_logger, jdOrderId, null, eachIndex);


                    StoreOrders storeOrders = new StoreOrders();
                    storeOrders.setOrderid(jdOrderId);
                    storeOrders.setOrdermoney(amount);
                    storeOrders.setSupplierid("102");
                    storeOrders.setSuppliername("京东订单");
                    storeOrders.setStatus(state);
                    storeOrders.setDate(date1);
                    storeOrders.setCommitdate(new Date());
                    storeOrders.setFreight(freight);
                    storeOrdersService.insertStoreOrders(storeOrders);
                }
                if (totalPage > pageNo++) {
                    queryOrder(syn_storedata_logger, startTime, productType, pageNo, jdOrderIdIndex, eachIndex);
                }
            }
        } catch (Exception e) {
            syn_storedata_logger.info(e.getMessage(), e);
        }
    }

    /**
     * 京东余额查询
     *
     * @param syn_storedata_logger 日志信息
     * @param store_id             京东ID
     * @param startTime            调试开始时间
     * @param payType              支付类型 4：余额 7：网银钱包 101：金采支付
     * @param eachIndex
     */
    public void queryBalance(Logger syn_storedata_logger, String store_id, Date startTime, String payType, int eachIndex) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isBlank(payType)) {
            payType = "4";
        }
        MallInfos infos = mallInfosService.queryMallInfo();
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
        StringBuffer stringBuffer = new StringBuffer();
        long time = System.currentTimeMillis();
        stringBuffer.append(time);
        JSONObject jo = new JSONObject();
        jo.put("appId", infos.getAppId());
        jo.put("payType", payType);
        stringBuffer.append(jo.toString());
        stringBuffer.append(infos.getSign());
        String md5 = Md5Utils.string2MD5(stringBuffer.toString());
        paramMap.put("appId", infos.getAppId());
        paramMap.put("timestamp", time);
        paramMap.put("sign", md5.toUpperCase());
        try {
            String jsonobj = NumberArithmeticUtils.sendPost(JD_QUERY_BALANCE, paramMap, "utf-8", "application/json", jo.toString());
            syn_storedata_logger.info("调用京东查询余额接口完成，获取信息：" + jsonobj);

            JSONObject jsonObject = new JSONObject(jsonobj);
            boolean success = jsonObject.getBoolean("success");
            if (!success) {
                if (eachIndex-- > 0) {
                    queryBalance(syn_storedata_logger, store_id, startTime, payType, eachIndex);
                }
            } else {
                String totalBalance = jsonObject.getString("result");
                BigDecimal balancemoney = new BigDecimal(totalBalance);
                syn_storedata_logger.info("供应商京东查询余额值为：" + balancemoney);

                //京东
                BalanceRecord balanceRecord = new BalanceRecord();
                balanceRecord.setId(UUID.randomUUID().toString().toUpperCase());
                balanceRecord.setSupplierid(store_id);
                balanceRecord.setSuppliername("京东");
                balanceRecord.setDate(sdf.format(startTime));
                balanceRecord.setCommitdate(new Date());
                balanceRecord.setBalancemoney(balancemoney);
                balanceRecordMapper.insertSelective(balanceRecord);
            }
        } catch (Exception e) {
            syn_storedata_logger.info(e.getMessage(), e);
        }
    }

    /**
     * 查询京东订单信息接口
     */
    public BigDecimal selectJdOrder(Logger syn_storedata_logger, String jdOrderId, String queryExts, int eachIndex) {
        BigDecimal returnFreight = BigDecimal.ZERO;

        if (StringUtils.isBlank(jdOrderId)) {
            syn_storedata_logger.info("订单号为空!");
            return returnFreight;
        }
        MallInfos infos = mallInfosService.queryMallInfo();
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
        StringBuffer stringBuffer = new StringBuffer();
        long time = System.currentTimeMillis();
        stringBuffer.append(time);
        JSONObject jo = new JSONObject();
        jo.put("appId", infos.getAppId());
        jo.put("jdOrderId", jdOrderId);
        jo.put("queryExts", queryExts);
        stringBuffer.append(jo.toString());
        stringBuffer.append(infos.getSign());
        String md5 = Md5Utils.string2MD5(stringBuffer.toString());
        paramMap.put("appId", infos.getAppId());
        paramMap.put("timestamp", time);
        paramMap.put("sign", md5.toUpperCase());
        try {
            String jsonobj = NumberArithmeticUtils.sendPost(JD_SELECT_JDORDER, paramMap, "utf-8", "application/json", jo.toString());
            syn_storedata_logger.info("查询京东订单信息：" + jsonobj);

            JSONObject jsonObject = new JSONObject(jsonobj);
            boolean success = jsonObject.getBoolean("success");
            if (!success) {
                if (eachIndex-- > 0) {
                    selectJdOrder(syn_storedata_logger, jdOrderId, queryExts, eachIndex);
                }
            } else {
                JSONObject result = jsonObject.getJSONObject("result");

                BigDecimal freight = result.getBigDecimal("freight");
                returnFreight = freight;
                syn_storedata_logger.info("查询京东订单信息运费信息：" + freight);
            }
        } catch (Exception e) {
            syn_storedata_logger.info(e.getMessage(), e);
        }
         return returnFreight;
    }
}