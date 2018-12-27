package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.service.IOrderCheckingService;
import com.ruoyi.system.tool.HRFIExctption;
import com.ruoyi.web.controller.tool.Result;
import com.ruoyi.web.controller.tool.ResultCode;
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
public class OrderCheckingController extends BaseController {

    private String prefix = "system/hrfi";

    @Autowired
    private IOrderCheckingService orderCheckingService;

    @RequestMapping("/home")
    public String home() {
        return prefix + "/orderchecking";
    }

    @PostMapping("/storelist")
    @ResponseBody
    public List<Storemanger> storeList() {
        startPage();
        List<Storemanger> storemangers = orderCheckingService.selectStoreAll();
        return storemangers;
    }

    @PostMapping("/storeConfigList")
    @ResponseBody
    public List<StoreConfig> storeConfigList() {
        startPage();
        List<StoreConfig> storeConfigs = orderCheckingService.selectStoreConfigList();
        return storeConfigs;
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo gatDifferenceOrderList(String id, String startTime, String mainid, String flag) {
        startPage();
        if (id != null && startTime != null && flag == null) {
            List differenceOrderList = orderCheckingService.gatDifferenceOrderList(id, startTime, mainid);
            return getDataTable(differenceOrderList);
        } else if ("0".equals(flag)) {
            return getDataTable(orderCheckingService.getchinkingList(mainid));
        } else {
            return getDataTable(new ArrayList<>());
        }
    }

    @PostMapping("/getchinkinginfo")
    @ResponseBody
    public Result getChinkingInfo(@RequestParam(required = false) String startTime) {
        Map map = null;
        try {
            map = orderCheckingService.getchinkinginfo(startTime);
            return Result.success(map);
        } catch (HRFIExctption hrfiExctption) {
            return Result.failure(ResultCode.RESULE_DATA_NONE, hrfiExctption);
        }
    }

    @PostMapping("/chinking")
    @ResponseBody
    public Result chinking(@RequestParam(required = false) String startTime) {
        Map map = orderCheckingService.chinkingMemberAdvance(startTime, 1);
        return Result.success(map);
    }

    @PostMapping("/storechecking")
    @ResponseBody
    public List storeChecking(@RequestParam(required = false) String startTime, @RequestParam(required = false) String mainid) {
        List storeCheckingList = orderCheckingService.storeChecking(startTime, mainid);
        return storeCheckingList;
    }

    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(String id, String startTime, String mainid) {
        List differenceOrderList = orderCheckingService.getchinkingList(mainid);
        ExcelUtil<CheckingLog> util = new ExcelUtil<CheckingLog>(CheckingLog.class);
        return util.exportExcel(differenceOrderList, "DifferenceOrderList");
    }
}