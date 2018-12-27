package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.Md5Utils;
import com.ruoyi.system.domain.BalanceRecord;
import com.ruoyi.system.domain.StoreConfig;
import com.ruoyi.system.domain.StoreOrders;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.IOrderCheckingService;
import com.ruoyi.system.service.ISynStoreDataService;
import com.ruoyi.system.service.JdSynStoreDataService;
import com.ruoyi.system.service.OuFeiSynStoreDataService;
import com.ruoyi.system.tool.BigDecimalUtils;
import com.ruoyi.system.tool.XMLUtil;
import com.ruoyi.system.utils.NumberArithmeticUtils;
import com.ruoyi.system.utils.Request;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class SynStoreDataServiceImpl implements ISynStoreDataService {
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
    @Autowired
    private BWConfigMapper bwConfigMapper;
    @Autowired
    private StoreOrdersMapper storeOrdersMapper;
    @Autowired
    JdSynStoreDataService jdSynStoreDataService;
    @Autowired
    OuFeiSynStoreDataService ouFeiSynStoreDataService;

    @Override
    public int sumMemberadvance(Logger syn_storedata_logger) throws Exception {
        BalanceRecord balanceRecord = membersMapper.sumMemberadvance();
        balanceRecord.setId(UUID.randomUUID().toString().toUpperCase());
        balanceRecord.setSupplierid("000");
        balanceRecord.setSupplierid("Mall");
        balanceRecord.setSuppliername("商城");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        balanceRecord.setCommitdate(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        balanceRecord.setDate(sdf.format(date));

        // 打印信息到日志
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        syn_storedata_logger.info(sdf1.format(new Date()) + "统计所有用户余额信息：" + balanceRecord);

        int insert = balanceRecordMapper.insert(balanceRecord);
        return insert;
    }

    @Override
    public void orderChecking(Logger syn_storedata_logger) throws Exception {
        // 打印信息到日志
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        String startTime = sdf.format(date);
//        String startTime = "2018-11-26";
        syn_storedata_logger.info(sdf1.format(new Date()) + "自动对账开始，对账截至日期为：" + startTime);

        //调用商城恒等式对账方法
        Map map = orderCheckingService.chinkingMemberAdvance(startTime, 0);

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
        List<Map> storeDateList = orderCheckingService.storeChecking(startTime, mainid);
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

        for (int i = 0; i < storeDateList.size(); i++) {
            //需要先判断恒等式是否相等 在调用订单详细对账

            if ((Integer) storeDateList.get(i).get("intflag") != 0) {
                //调用订单详细对账方法
                orderCheckingService.gatDifferenceOrderList(storeDateList.get(i).get("store_id").toString(), startTime, mainid);
            }
        }

        syn_storedata_logger.info(sdf1.format(new Date()) + "自动对账结束");
    }

    @Override
    public void getStoreAdvance(Logger syn_storedata_logger) throws Exception {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        String startTime = sdf.format(date);

        syn_storedata_logger.info(sdf1.format(new Date()) + "供应商预存款获取开始，对账截至日期为：" + startTime);

        List<StoreConfig> storeConfigs = bwConfigMapper.selectStoreConfigAll();
        for (int i = 0; i < storeConfigs.size(); i++) {

            StoreConfig storeConfig = storeConfigs.get(i);
            String store_id = storeConfig.getStore_id();
            String shop_name = storeConfig.getShop_name();
            String paymethod = storeConfig.getPaymethod();

            if ("precharge".equals(paymethod)) {
                //预充值供应商
                syn_storedata_logger.info("供应商：" + shop_name + "获取余额。");

                BalanceRecord balanceRecord = new BalanceRecord();
                balanceRecord.setId(UUID.randomUUID().toString().toUpperCase());
                if ("103".equals(store_id)) {
                    balanceRecord.setSupplierid(store_id);
                    balanceRecord.setSuppliername("欧飞");
                    balanceRecord.setDate(startTime);
                    balanceRecord.setCommitdate(new Date());

                    String url = "http://third-party.newtouch.com/oufeimp/ntpmp-api/query-user-info";

                    Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
                    StringBuffer stringBuffer = new StringBuffer();
                    long time = System.currentTimeMillis();
                    stringBuffer.append(time);
                    JSONObject jo = new JSONObject();
                    jo.put("appId", "newtouchmall");
                    stringBuffer.append(jo.toString());
                    stringBuffer.append("ac063f15ccff416b9a2278318920926f");
                    String md5 = Md5Utils.string2MD5(stringBuffer.toString());
                    paramMap.put("appId", "newtouchmall");
                    paramMap.put("timestamp", time);
                    paramMap.put("sign", md5.toUpperCase());
                    try {
                        String xml = NumberArithmeticUtils.sendPost(url, paramMap, "utf-8", "application/json", jo.toString());
                        Request req = (Request) XMLUtil.convertXmlStrToObject(Request.class, xml);
                        if ("1".equals(req.getRetcode())) {
                            String totalBalance = req.getTotalBalance();
                            BigDecimal balancemoney = new BigDecimal(totalBalance);

                            syn_storedata_logger.info("供应商：" + shop_name + "获取余额值为：" + balancemoney);

                            balanceRecord.setBalancemoney(balancemoney);
                            balanceRecordMapper.insertSelective(balanceRecord);
                        } else {
                            syn_storedata_logger.info("供应商：" + shop_name + "获取余额失败，错误描述：" + req.getErr_msg());
                        }
                    } catch (IOException e) {
                        syn_storedata_logger.info("供应商：" + shop_name + "获取余额失败，报错原因：" + e.toString());
                        e.printStackTrace();
                    }
                } else if ("102".equals(store_id)) {

                    jdSynStoreDataService.queryBalance(syn_storedata_logger, store_id, date, "4", 3);
                }
            }
        }
        syn_storedata_logger.info(sdf1.format(new Date()) + "供应商预存款获取结束，对账截至日期为：" + startTime);
    }

    @Override
    public void getStoreOrders(Logger syn_storedata_logger, int num) throws Exception {

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();

        syn_storedata_logger.info("供应商订单同步功能获取开始，对账截至日期为：" + date);

        List<StoreConfig> storeConfigs = bwConfigMapper.selectStoreConfigAll();
        for (int i = 0; i < storeConfigs.size(); i++) {
            if (num > 0) {
                StoreConfig storeConfig = storeConfigs.get(i);
                String store_id = storeConfig.getStore_id();
                String shop_name = storeConfig.getShop_name();
                String paymethod = storeConfig.getPaymethod();

                if ("102".equals(store_id)) {
                    //京东商城
                    jdSynStoreDataService.queryOrder(syn_storedata_logger, date, "1", 1, null, num);
                } else if ("103".equals(store_id)) {
                    //欧飞
                    ouFeiSynStoreDataService.queryBill(syn_storedata_logger, date, num);
                } else if ("106".equals(store_id)) {
                    //获取携程订单
                    String url = "http://third-party.newtouch.com/ctripmp/ntpmp-api/get-order-settlement";

                    List<String> productTypeList = new ArrayList<String>();
                    productTypeList.add("1");
                    productTypeList.add("2");

                    for (int l = 0; l < productTypeList.size(); l++) {
                        String productType = productTypeList.get(l);

                        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
                        StringBuffer stringBuffer = new StringBuffer();
                        long time = System.currentTimeMillis();
                        stringBuffer.append(time);
                        JSONObject jo = new JSONObject();
                        jo.put("appId", "newtouchmall");
                        jo.put("dateFrom", "2018-12-01");
                        jo.put("dateTo", "2018-12-14");
                        jo.put("productType", productType);
                        JSONArray json = new JSONArray();
                        json.put(jo);
                        stringBuffer.append(jo.toString());
                        stringBuffer.append("ac063f15ccff416b9a2278318920926f");
                        String md5 = Md5Utils.string2MD5(stringBuffer.toString());
                        paramMap.put("appId", "newtouchmall");
                        paramMap.put("timestamp", time);
                        paramMap.put("sign", md5.toUpperCase());


                        String jsonobj = NumberArithmeticUtils.sendPost(url, paramMap, "utf-8", "application/json", jo.toString());
                        syn_storedata_logger.info("调用携程酒店查询订单明细接口完成，获取信息：" + jsonobj);

                        if ("调用携程订单明细接口错误".equals(jsonobj)) {
                            syn_storedata_logger.info("调用携程酒店查询订单明细接口错误，错误信息：" + jsonobj);
                        } else {
                            JSONObject jsonObject = new JSONObject(jsonobj);
                            JSONObject status = jsonObject.getJSONObject("Status");
                            if ("false".equals(status.getString("Success"))) {
                                syn_storedata_logger.info("调用欧飞查询订单明细接口错误，错误信息：" + status.getString("Message") + status.getString("ErrorCode"));
                                num--;
                                getStoreOrders(syn_storedata_logger, num);
                            } else {
                                if ("1".equals(productType)) {
                                    JSONArray flightOrderAccountSettlementList = jsonObject.getJSONArray("FlightOrderAccountSettlementList");
                                    for (int j = 0; j < flightOrderAccountSettlementList.length(); j++) {
                                        JSONArray orderSettlementList = flightOrderAccountSettlementList.getJSONObject(j).getJSONArray("OrderSettlementList");
                                        for (int k = 0; k < orderSettlementList.length(); k++) {
                                            JSONObject orderSettlementBaseInfo = orderSettlementList.getJSONObject(k).getJSONObject("OrderSettlementBaseInfo");
                                            String orderID = orderSettlementBaseInfo.getString("OrderID");

                                            double amount = orderSettlementBaseInfo.getDouble("Amount");

                                            String createtime = orderSettlementBaseInfo.getString("CreateTime");

                                            Date utilDate = sdf1.parse(createtime);
                                            Date date1 = new java.sql.Date(utilDate.getTime());

                                            StoreOrders storeOrders = new StoreOrders();
                                            storeOrders.setOrderid(orderID);
                                            storeOrders.setOrdermoney(new BigDecimal(amount));
                                            storeOrders.setSupplierid("106");
                                            storeOrders.setSuppliername("携程订单");
                                            storeOrders.setStatus("1");
                                            storeOrders.setDate(date1);
                                            storeOrders.setCommitdate(new Date());
                                            StoreOrders ifnull = storeOrdersMapper.selectByPrimaryKey(orderID);
                                            if (ifnull == null) {
                                                storeOrdersMapper.insertSelective(storeOrders);
                                            } else {
                                                storeOrdersMapper.updateByPrimaryKeySelective(storeOrders);
                                            }
                                        }
                                    }
                                }
                                if ("2".equals(productType)) {
                                    JSONArray lstHtlSettlement = jsonObject.getJSONArray("LstHtlSettlement");
                                    for (int j = 0; j < lstHtlSettlement.length(); j++) {
                                        JSONArray lstHotelSettlementDetail = lstHtlSettlement.getJSONObject(j).getJSONArray("LstHotelSettlementDetail");
                                        for (int k = 0; k < lstHotelSettlementDetail.length(); k++) {
                                            JSONObject settlementDetail = lstHotelSettlementDetail.getJSONObject(k).getJSONObject("SettlementDetail");
                                            String orderID = settlementDetail.getString("OrderID");

                                            double amount = settlementDetail.getDouble("Amount");
                                            double servicefee = settlementDetail.getDouble("Servicefee");
                                            double extraCharge = settlementDetail.getDouble("ExtraCharge");
                                            double orderMoney = BigDecimalUtils.formatBigDecimalToDouble(amount + servicefee + extraCharge);

                                            String createtime = settlementDetail.getString("Createtime");

                                            Date utilDate = sdf1.parse(createtime);
                                            Date date1 = new java.sql.Date(utilDate.getTime());

                                            StoreOrders storeOrders = new StoreOrders();
                                            storeOrders.setOrderid(orderID);
                                            storeOrders.setOrdermoney(new BigDecimal(orderMoney));
                                            storeOrders.setSupplierid("106");
                                            storeOrders.setSuppliername("携程订单");
                                            storeOrders.setStatus("1");
                                            storeOrders.setDate(date1);
                                            storeOrders.setCommitdate(new Date());
                                            StoreOrders ifnull = storeOrdersMapper.selectByPrimaryKey(orderID);
                                            if (ifnull == null) {
                                                storeOrdersMapper.insertSelective(storeOrders);
                                            } else {
                                                storeOrdersMapper.updateByPrimaryKeySelective(storeOrders);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        syn_storedata_logger.info("供应商订单同步功能获取结束，对账截至日期为：" + date);
    }

    public String getJdOrders(String url, String startTime, String productType, String jdOrderIdIndex) throws Exception {
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
        StringBuffer stringBuffer = new StringBuffer();
        long time = System.currentTimeMillis();
        stringBuffer.append(time);
        JSONObject jo = new JSONObject();
        jo.put("appId", "newtouchmall");
        jo.put("date", startTime);
        jo.put("searchType", productType);
        jo.put("pageNo", "1");
        jo.put("pageNo", "100");
        jo.put("jdOrderIdIndex", jdOrderIdIndex);
        stringBuffer.append(jo.toString());
        stringBuffer.append("ac063f15ccff416b9a2278318920926f");
        String md5 = Md5Utils.string2MD5(stringBuffer.toString());
        paramMap.put("appId", "newtouchmall");
        paramMap.put("timestamp", time);
        paramMap.put("sign", md5.toUpperCase());


        String jsonobj = NumberArithmeticUtils.sendPost(url, paramMap, "utf-8", "application/json", jo.toString());
        return jsonobj;
    }

    public String getMinOrderId(JSONArray orders) throws Exception {
        String minOrderId = "";
        long min = 0l;

        for (int j = 0; j < orders.length(); j++) {
            JSONObject order = orders.getJSONObject(j);
            String jdOrderId = order.getString("jdOrderId");
            String time = order.getString("time");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date ordertime = df.parse(time);
            long timel = ordertime.getTime();
            if (timel < min) {
                min = timel;
                minOrderId = jdOrderId;
            }
        }


        return minOrderId;
    }
}
