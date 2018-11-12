package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.system.domain.BWConfigtype;
import com.ruoyi.system.domain.SysRole;
import com.ruoyi.system.service.IBalanceWarningService;
import com.ruoyi.web.core.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        List list = new ArrayList();
        return getDataTable(list);
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
     * 新增余额预警配置
     */
    /*@Log(title = "余额预警配置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult add(SysRole role)
    {
        role.setCreateBy(ShiroUtils.getLoginName());
        ShiroUtils.clearCachedAuthorizationInfo();
        return toAjax(roleService.insertRole(role));

    }*/

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

    @GetMapping("/edit/{roleId}")
    public String edit(@PathVariable("roleId") Long roleId, ModelMap mmap)
    {
        return prefix + "/edit";
    }


}