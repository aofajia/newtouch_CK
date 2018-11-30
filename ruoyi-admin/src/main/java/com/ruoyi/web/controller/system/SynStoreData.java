package com.ruoyi.web.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.service.ISynStoreDataService;
import com.ruoyi.system.tool.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 定时任务调度
 *
 * @author ruoyi
 */
@Component("SynSotreData")
public class SynStoreData
{

    @Autowired
    private ISynStoreDataService synStoreDataService;

    public void sumMemberadvance()
    {
        //每日零时统计所有用户余额
        int i = synStoreDataService.sumMemberadvance();
    }

    public void getStoreAdvance()
    {
        //获取各供应商配置接口
        //调用接口获取数据
        Map<String, String> paramMap = new HashMap();

        String url = "http://接口地址:端口号/方法名";
        HttpClientUtil httpUtil=new HttpClientUtil();
        String str=httpUtil.doPost(url,paramMap,"UTF-8");
        JSONObject result = JSONObject.parseObject(str);
    }

    public void getStoreOrders()
    {
        //获取各供应商配置接口
        //调用接口获取数据
        Map<String, String> paramMap = new HashMap();

        String url = "http://接口地址:端口号/方法名";
        HttpClientUtil httpUtil=new HttpClientUtil();
        String str=httpUtil.doPost(url,paramMap,"UTF-8");
        JSONObject result = JSONObject.parseObject(str);
    }

}