package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.system.domain.DifferenceOrderList;
import com.ruoyi.system.domain.StoreConfig;
import com.ruoyi.system.domain.Storemanger;
import com.ruoyi.system.domain.SysRole;
import com.ruoyi.system.service.IOrderCheckingService;
import com.ruoyi.web.controller.tool.Result;
import com.ruoyi.web.core.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
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

    @PostMapping("/storeConfigList")
    @ResponseBody
    public List<StoreConfig> storeConfigList()
    {
        startPage();
        List<StoreConfig> storeConfigs = orderCheckingService.selectStoreConfigList();
        return storeConfigs;
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo gatDifferenceOrderList(String id,String startTime)
    {
        startPage();
        if(id != null && startTime != null)
        {
            List differenceOrderList = orderCheckingService.gatDifferenceOrderList(id, startTime);
            return getDataTable(differenceOrderList);
        }
        else
        {
            return getDataTable(new ArrayList<>());
        }
    }

    @PostMapping("/chinking")
    @ResponseBody
    public Result chinking(@RequestParam(required = false) String startTime)
    {
        Map map = orderCheckingService.chinkingMemberAdvance(startTime);
        return Result.success(map);
    }

    @PostMapping("/storechecking")
    @ResponseBody
    public List storeChecking(@RequestParam(required = false) String startTime)
    {
        List storeCheckingList = orderCheckingService.storeChecking(startTime);
        return storeCheckingList;
    }

    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(String id,String startTime)
    {
        List differenceOrderList = orderCheckingService.gatDifferenceOrderList(id, startTime);
        ExcelUtil<DifferenceOrderList> util = new ExcelUtil<DifferenceOrderList>(DifferenceOrderList.class);
        return util.exportExcel(differenceOrderList, "DifferenceOrderList");
    }
}