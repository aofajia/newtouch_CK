package com.ruoyi.web.controller.system;

import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.system.domain.Storemanger;
import com.ruoyi.system.service.IOrderCheckingService;
import com.ruoyi.web.core.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 订单对账
 * 
 * @author
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequestMapping("/fi/orderchecking")
public class OrderCheckingController extends BaseController
{

    private String prefix = "system/hrfi";

    @Autowired
    private IOrderCheckingService orderCheckingService;

    @RequestMapping("/home")
    public String home()
    {
        return  prefix+"/orderchecking";
    }

    @PostMapping("/storelist")
    @ResponseBody
    public List<Storemanger> storeList()
    {
        startPage();
        List<Storemanger> storemangers = orderCheckingService.selectStoreAll();
        return storemangers;
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(@RequestBody Map map)
    {
        startPage();
        //先获取商城订单中（可开票/全部）订单
        //调用京东接口 按日期获取所有订单
        List list = new ArrayList();
        return getDataTable(list);
    }

    @PostMapping("/chinking")
    @ResponseBody
    public List chinking(@RequestParam(required = false) String endTime)
    {
        //先获取商城订单中（可开票/全部）订单
        //调用京东接口 按日期获取所有订单
        List list = new ArrayList();
        return list;
    }

    @PostMapping("/companylist")
    @ResponseBody
    public List<Storemanger> companyList()
    {
        startPage();
        List<Storemanger> storemangers = orderCheckingService.selectStoreAll();
        return storemangers;
    }
}