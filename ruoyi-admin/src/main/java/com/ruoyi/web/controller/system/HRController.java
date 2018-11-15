package com.ruoyi.web.controller.system;


import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.system.domain.OpenTicketInfoCollect;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.OpenTicketService;
import com.ruoyi.web.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 商城HR
 *
 * @author ChenKang
 */
@Controller
@RequestMapping("/system/hr")
public class HRController extends BaseController {

    @Autowired
    private OpenTicketService openTicketService;


    private String prefix = "system/hrfi";

    /**
     * 跳转Hr首页
     */
    @RequestMapping("/home")
    public String home() {
        return prefix + "/hr";
    }

    /**
     * 跳转开票界面
     */
    @RequestMapping("/open")
    public String open() {
        return prefix+"/openticketflow";
    }

    /**
     * 跳转开票界面
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list() {
        startPage();
        List<OpenTicketInfoCollect> list = openTicketService.list();
        return getDataTable(list);
    }



}
