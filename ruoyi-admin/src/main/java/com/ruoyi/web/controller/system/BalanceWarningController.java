package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.system.domain.BWConfig;
import com.ruoyi.system.domain.BWConfigtype;
import com.ruoyi.system.domain.StoreConfig;
import com.ruoyi.system.service.IBalanceWarningService;
import com.ruoyi.web.controller.tool.Result;
import com.ruoyi.web.core.base.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ruoyi.web.controller.tool.ResultCode.INTERFACE_INNER_INVOKE_ERROR;
import static com.ruoyi.web.controller.tool.ResultCode.SPECIFIED_STOREMONTHLYMONEY_ISNULL;

/**
 * 余额预警
 * 
 * @author
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequestMapping("/fi/balancewarning")
public class BalanceWarningController extends BaseController
{

    private String prefix = "system/hrfi";

    @Autowired
    private IBalanceWarningService balanceWarningService;

    //纪录日志
//    Logger logger = LoggerFactory.getLogger(BalanceWarningController.class);

    @RequestMapping("/home")
    public String home()
    {
        return  prefix+"/balancewarning";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list()
    {
        startPage();
        List<BWConfig> bwConfigs = balanceWarningService.selectConfigAll();
        return getDataTable(bwConfigs);
    }

    @PostMapping("/config_type")
    @ResponseBody
    public List<BWConfigtype> configtypeList()
    {
        startPage();
        List<BWConfigtype> configtypes = balanceWarningService.selectConfigTypeAll();
        return configtypes;
    }

    @GetMapping("/addstore")
    public String addstore()
    {
        return prefix + "/balancewarning_add_store";
    }

    /**
     * 新增供应商配置
     */
    @Log(title = "新增供应商配置", businessType = BusinessType.INSERT)
    @PostMapping("/addstoreconfig")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addStoreConfig(BWConfig configMap)
    {
        int i = balanceWarningService.addStoreConfig(configMap);
        return toAjax(i);
    }

    /**
     * 新增商城负责人配置
     */
    @Log(title = "新增商城负责人配置", businessType = BusinessType.INSERT)
    @PostMapping("/addmanageconfig")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addMangeConfig(BWConfig configMap)
    {
        int i = balanceWarningService.addMangeConfig(configMap);
        return toAjax(i);
    }

    @PostMapping("/checkstorename")
    @ResponseBody
    public String checkStoreName(String configname)
    {
        startPage();
        //1为已存在  0为不存在
        String intcheck = balanceWarningService.checkStoreName(configname);
        return intcheck;
    }

    @GetMapping("/addmanage")
    public String addmanage()
    {
        return prefix + "/balancewarning_add_manage";
    }

    @GetMapping("/editstore/{roleId}")
    public String editStore(@PathVariable("roleId") String roleId, ModelMap mmap)
    {
        BWConfig bwConfig = balanceWarningService.selectStoreConfigById(roleId);
        mmap.put("storeConfig", bwConfig);
        return prefix + "/balancewarning_edit_store";
    }

    @PostMapping("/getnotextinfo")
    @ResponseBody
    public BWConfig getNoTextInfo(BWConfig configMap)
    {
        String id = configMap.getId();
        BWConfig bwConfig = balanceWarningService.selectStoreConfigById(id);
        return bwConfig;
    }

    /**
     * 修改供应商配置
     */
    @Log(title = "修改供应商配置", businessType = BusinessType.INSERT)
    @PostMapping("/editstoreconfig")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editStoreConfig(BWConfig configMap)
    {
        int i = balanceWarningService.updateStoreConfig(configMap);
        return toAjax(i);
    }

    @GetMapping("/editmanage/{roleId}")
    public String editManage(@PathVariable("roleId") String roleId, ModelMap mmap)
    {
        BWConfig bwConfig = balanceWarningService.selectManageConfigById(roleId);
        mmap.put("manageConfig", bwConfig);
        return prefix + "/balancewarning_edit_manage";
    }

    /**
     * 修改商城负责人配置
     */
    @Log(title = "修改商城负责人配置", businessType = BusinessType.INSERT)
    @PostMapping("/editmanageconfig")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editMangeConfig(BWConfig configMap)
    {
        int i = balanceWarningService.updateManageConfig(configMap);
        return toAjax(i);
    }

    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        try
        {
            int i = balanceWarningService.deleteConfigByIds(ids);
            return toAjax(i);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }
    /**
     * 获取供应商配置信息
     *
     * @return
     */
    /*@RequestMapping(value = "/FI/getStoreMonthlyMoney", method = RequestMethod.POST)
    public Result getStoreMonthlyMoney() {
        try {
            List<StoreConfig> list = balanceWarningService.getStoreMonthlyMoney();
            //如果查询的结果大于0返回success为0提示今天没有离职员工
            if (list.size() > 0) {
                return Result.success(list);
            }
            if (list.size() == 0) {
                return Result.failure(SPECIFIED_STOREMONTHLYMONEY_ISNULL);
            }
        } catch (Exception e) {
//            logger.debug("获取供应商配置信息失败！" + e.getMessage());
            return Result.failure(INTERFACE_INNER_INVOKE_ERROR);

        }
        return Result.success();
    }*/
}