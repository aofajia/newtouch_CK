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

    public void sumMemberadvance()
    {
        //每日零时统计所有用户余额
        try
        {
            int i = synStoreDataService.sumMemberadvance(syn_storedata_logger);
        }
        catch (Exception e)
        {
            syn_storedata_logger.info("每日零时统计所有用户余额程序报错，错误信息："+e.toString());
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
        /*SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
                if("103".equals(store_id) || "104".equals(store_id))
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
        }*/
    }

    public void getStoreOrders()
    {
        //获取各供应商配置接口
        //调用接口获取数据
        try
        {
            synStoreDataService.getStoreOrders(syn_storedata_logger);
        }
        catch (Exception e)
        {
            syn_storedata_logger.info("供应商订单同步功能程序报错，错误信息："+e.toString());
            e.printStackTrace();
        }
        /*if (result != null && !result.trim().equals(""))
        {
            BufferedReader br = null;
            try
            {
                //响应头解析 图片地址解析
                br = new BufferedReader(  //包装
                        new InputStreamReader(
                                new ByteArrayInputStream(result.getBytes())));//从输入流中读信息
                while(br.ready())
                {

                    String str = br.readLine(); //读一行
                    String[] split = str.split("|");

                    if("S".equals(split[0].substring(0,1)))
                    {
                        String liushuihao = split[0];//CP流水号
                        String dingdanhao = split[1];//SP订单号
                        String shangpinbianhao = split[2];//商品编号
                        String shangpingshuliang = split[3];//商品数量
                        String chongzhizhanghao = split[4];//充值账号
                        String dingdanjine = split[5];//订单金额
                        String dingdanshijian = split[6];//订单时间
                        String dingdanzhuangtai = split[7];//订单状态

                        StoreOrders storeOrders = new StoreOrders();
                        storeOrders.setId(UUID.randomUUID().toString().toUpperCase());
                        storeOrders.setSupplierid("103");
                        storeOrders.setSuppliername("欧飞订单");
                        storeOrders.setOrderid(dingdanhao);
                        storeOrders.setOrdermoney(new BigDecimal(dingdanjine));
                        storeOrders.setDate(new Date(dingdanshijian));
                        storeOrders.setCommitdate(new Date());
                    }


                }
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }*/

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