package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.Md5Utils;
import com.ruoyi.system.domain.MallInfos;
import com.ruoyi.system.domain.StoreOrders;
import com.ruoyi.system.service.MallInfosService;
import com.ruoyi.system.service.OuFeiSynStoreDataService;
import com.ruoyi.system.service.StoreOrdersService;
import com.ruoyi.system.utils.NumberArithmeticUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class OuFeiSynStoreDataServiceImpl implements OuFeiSynStoreDataService {
    @Autowired
    MallInfosService mallInfosService;
    @Autowired
    StoreOrdersService storeOrdersService;
    /**
     * 查询欧飞订单明细（查余额）
     */
    private static final String OUFEI_QUERY_BILL = "http://test.third-party.newtouch.com/oufeimp/ntpmp-api/query-bill";

    /**
     * 查询欧飞订单明细（查余额）接口
     * @param syn_storedata_logger 日志信息
     * @param endTime 截止时间
     * @param num 接口调用异常时重复调用次数
     */
    public void queryBill(Logger syn_storedata_logger, Date endTime, int num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        MallInfos infos = mallInfosService.queryMallInfo();
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
        StringBuffer stringBuffer = new StringBuffer();
        long time = System.currentTimeMillis();
        stringBuffer.append(time);
        JSONObject jo = new JSONObject(new LinkedHashMap());
        jo.put("appId", infos.getAppId());
        jo.put("starttime", "20181110"); //根据情况 修改获取起始时间
        jo.put("endtime", sdf.format(endTime));
        jo.put("cardid", "");
        jo.put("orderstat", "4");
        jo.put("classtype", "0");
        stringBuffer.append(jo.toString());
        stringBuffer.append(infos.getSign());
        String md5 = Md5Utils.string2MD5(stringBuffer.toString());
        paramMap.put("appId", infos.getAppId());
        paramMap.put("timestamp", time);
        paramMap.put("sign", md5.toUpperCase());
        try {
            String dataList = NumberArithmeticUtils.sendPost(OUFEI_QUERY_BILL, paramMap, "utf-8", "application/json", jo.toString());
            syn_storedata_logger.info("调用欧飞查询订单明细接口完成，获取信息：" + dataList);
            if (!"CP流水号".equals(dataList.substring(0, 5))) {
                syn_storedata_logger.info("调用欧飞查询订单明细接口错误，错误信息：" + dataList);
                num--;
                queryBill(syn_storedata_logger, endTime, num);
            } else{
                String[] line = dataList.split("\n");
                for (int j = 1; j < line.length-1 ; j++) {
                    String[] info = line[j].split("\\|");
                    String liushuihao = info[0];//CP流水号
                    String dingdanhao = info[1].substring(12);//SP订单号
                    String shangpinbianhao = info[2];//商品编号
                    String shangpingshuliang = info[3];//商品数量
                    String chongzhizhanghao = info[4];//充值账号
                    String dingdanjine = info[5];//订单金额
                    String dingdanshijian = info[6];//订单时间
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date utilDate = sdf.parse(dingdanshijian);
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
                    storeOrdersService.insertStoreOrders(storeOrders);
                }
            }
        } catch (Exception e) {
            syn_storedata_logger.info(e.getMessage(),e);
        }
    }
}