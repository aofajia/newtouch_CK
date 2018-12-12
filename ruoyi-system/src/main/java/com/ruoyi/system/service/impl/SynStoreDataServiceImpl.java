package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.Md5Utils;
import com.ruoyi.system.domain.BalanceRecord;
import com.ruoyi.system.domain.StoreConfig;
import com.ruoyi.system.domain.StoreOrders;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.IOrderCheckingService;
import com.ruoyi.system.service.ISynStoreDataService;
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
    @Autowired
    private BWConfigMapper bwConfigMapper;
    @Autowired
    private StoreOrdersMapper storeOrdersMapper;

    @Override
    public int sumMemberadvance(Logger syn_storedata_logger)  throws Exception
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
    public void orderChecking(Logger syn_storedata_logger)  throws Exception
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
//        String startTime = "2018-11-26";
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

    @Override
    public void getStoreAdvance(Logger syn_storedata_logger) throws Exception
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        String startTime = sdf.format(date);

        syn_storedata_logger.info(sdf1.format(new Date())+"供应商预存款获取开始，对账截至日期为："+startTime);

        List<StoreConfig> storeConfigs = bwConfigMapper.selectStoreConfigAll();
        for (int i = 0; i < storeConfigs.size() ; i++)
        {

            StoreConfig storeConfig = storeConfigs.get(i);
            String store_id = storeConfig.getStore_id();
            String shop_name = storeConfig.getShop_name();
            String paymethod = storeConfig.getPaymethod();

            if("precharge".equals(paymethod))
            {
                //预充值供应商
                syn_storedata_logger.info("供应商："+shop_name+"获取余额。");

                BalanceRecord balanceRecord = new BalanceRecord();
                balanceRecord.setId(UUID.randomUUID().toString().toUpperCase());
                if("103".equals(store_id))
                {
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
                    paramMap.put("appId","newtouchmall");
                    paramMap.put("timestamp",time);
                    paramMap.put("sign",md5.toUpperCase());
                    try
                    {
                        String xml = NumberArithmeticUtils.sendPost(url,paramMap, "utf-8", "application/json",jo.toString());
                        Request req = (Request) XMLUtil.convertXmlStrToObject(Request.class, xml);
                        if("1".equals(req.getRetcode()))
                        {
                            String totalBalance = req.getTotalBalance();
                            BigDecimal balancemoney = new BigDecimal(totalBalance);

                            syn_storedata_logger.info("供应商："+shop_name+"获取余额值为："+balancemoney);

                            balanceRecord.setBalancemoney(balancemoney);
                            balanceRecordMapper.insertSelective(balanceRecord);
                        }
                        else
                        {
                            syn_storedata_logger.info("供应商："+shop_name+"获取余额失败，错误描述："+req.getErr_msg());
                        }
                    }
                    catch (IOException e)
                    {
                        syn_storedata_logger.info("供应商："+shop_name+"获取余额失败，报错原因："+e.toString());
                        e.printStackTrace();
                    }
                }
                else if("102".equals("store_id"))
                {
                    //京东
                }
            }
        }
        syn_storedata_logger.info(sdf1.format(new Date())+"供应商预存款获取结束，对账截至日期为："+startTime);
    }

    @Override
    public void getStoreOrders(Logger syn_storedata_logger,int num) throws Exception
    {

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        String startTime = sdf.format(date);
        String endTime = sdf2.format(date);

        syn_storedata_logger.info(sdf1.format(new Date()) + "供应商订单同步功能获取开始，对账截至日期为：" + startTime);

        List<StoreConfig> storeConfigs = bwConfigMapper.selectStoreConfigAll();
        for (int i = 0; i < storeConfigs.size(); i++)
        {
            if(num > 0) {
                StoreConfig storeConfig = storeConfigs.get(i);
                String store_id = storeConfig.getStore_id();
                String shop_name = storeConfig.getShop_name();
                String paymethod = storeConfig.getPaymethod();

                if ("102".equals(store_id))
                {

                }
                else if ("103".equals(store_id))
                {
                    //获取欧飞订单
                    String url = "http://third-party.newtouch.com/oufeimp/ntpmp-api/query-bill";

                    Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
                    StringBuffer stringBuffer = new StringBuffer();
                    long time = System.currentTimeMillis();
                    stringBuffer.append(time);
                    JSONObject jo = new JSONObject(new LinkedHashMap());
                    jo.put("appId", "newtouchmall");
                    jo.put("starttime", "20181110"); //根据情况 修改获取起始时间
                    jo.put("endtime", endTime);
                    jo.put("cardid", "");
                    jo.put("orderstat", "4");
                    jo.put("classtype", "0");
    //                JSONArray json = new JSONArray();
    //                json.put(jo);
                    stringBuffer.append(jo.toString());
                    stringBuffer.append("ac063f15ccff416b9a2278318920926f");
                    String md5 = Md5Utils.string2MD5(stringBuffer.toString());
                    paramMap.put("appId", "newtouchmall");
                    paramMap.put("timestamp", time);
                    paramMap.put("sign", md5.toUpperCase());
                    String dataList = NumberArithmeticUtils.sendPost(url, paramMap, "utf-8", "application/json", jo.toString());
                    syn_storedata_logger.info("调用欧飞查询订单明细接口完成，获取信息：" + dataList);
                    if (!"CP流水号".equals(dataList.substring(0, 5)))
                    {
                        syn_storedata_logger.info("调用欧飞查询订单明细接口错误，错误信息：" + dataList);
                        num--;
                        getStoreOrders(syn_storedata_logger, num);
                    }
                    else
                    {
                        String[] line = dataList.split("\n");
                        for (int j = 1; j < line.length-1 ; j++)
                        {
                            String[] info = line[j].split("\\|");
                            String liushuihao = info[0];//CP流水号
                            String dingdanhao = info[1].substring(12);//SP订单号
                            String shangpinbianhao = info[2];//商品编号
                            String shangpingshuliang = info[3];//商品数量
                            String chongzhizhanghao = info[4];//充值账号
                            String dingdanjine = info[5];//订单金额
                            String dingdanshijian = info[6];//订单时间
                            Date utilDate = sdf1.parse(dingdanshijian);
                            Date date1 = new java.sql.Date(utilDate.getTime());
                            String dingdanzhuangtai = info[7];//订单状态

                            StoreOrders storeOrders = new StoreOrders();
                            storeOrders.setOrderid(dingdanhao);
                            storeOrders.setOrdermoney(new BigDecimal(dingdanjine));
                            storeOrders.setSupplierid("103");
                            storeOrders.setSuppliername("欧飞订单");
                            storeOrders.setStatus(dingdanzhuangtai);
                            storeOrders.setDate(date1);
                            storeOrders.setCommitdate(new Date());
                            StoreOrders ifnull = storeOrdersMapper.selectByPrimaryKey(dingdanhao);
                            if(ifnull == null)
                            {
                                storeOrdersMapper.insertSelective(storeOrders);
                            }
                            else
                            {
                                storeOrdersMapper.updateByPrimaryKeySelective(storeOrders);
                            }
                        }
                    }
                }
                else if ("106".equals(store_id))
                {
                    //获取携程订单
                    String url = "third-party.newtouch.com/ctripmp/ntpmp-api/get-order-settlement";

                    Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
                    StringBuffer stringBuffer = new StringBuffer();
                    long time = System.currentTimeMillis();
                    stringBuffer.append(time);
                    JSONObject jo = new JSONObject();
                    jo.put("appId", "newtouchmall");
                    jo.put("dateFrom", "20181120");
                    jo.put("dateTo", "20181203");
                    jo.put("productType", "1");
                    JSONArray json = new JSONArray();
                    json.put(jo);
                    stringBuffer.append(jo.toString());
                    stringBuffer.append("ac063f15ccff416b9a2278318920926f");
                    String md5 = Md5Utils.string2MD5(stringBuffer.toString());
                    paramMap.put("appId", "newtouchmall");
                    paramMap.put("timestamp", time);
                    paramMap.put("sign", md5.toUpperCase());
                    String xml = NumberArithmeticUtils.sendPost(url, paramMap, "utf-8", "application/json", jo.toString());
                    String[] split = xml.split("\n");
                    for (int j = 1; j < split.length - 1; j++) {
                        System.out.println("第" + j + "行数据：" + split[j]);
                    }
                }
            }
        }
        syn_storedata_logger.info(sdf1.format(new Date()) + "供应商订单同步功能获取结束，对账截至日期为：" + startTime);
    }
}
