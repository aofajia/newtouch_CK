package com.ruoyi.web.controller.system;

import com.ruoyi.system.service.ISynStoreDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

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

}