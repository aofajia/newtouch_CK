package com.ruoyi.web.controller.system;

import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.system.domain.Storemanger;
import com.ruoyi.system.service.IOrderCheckingService;
import com.ruoyi.web.core.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping("/system/hrfi")
public class OrderCheckingController extends BaseController
{

    private String prefix = "system/hrfi";

    @Autowired
    private IOrderCheckingService orderCheckingService;

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
        List list = new ArrayList();
        return getDataTable(list);
    }

    @RequestMapping("/home")
    public String home()
    {
        return  prefix+"/orderchecking";
    }


}