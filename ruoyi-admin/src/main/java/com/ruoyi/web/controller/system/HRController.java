package com.ruoyi.web.controller.system;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.system.domain.OpenTicketInfoCollect;
import com.ruoyi.system.domain.OpenTicketParms;
import com.ruoyi.system.service.OpenTicketService;
import com.ruoyi.web.controller.tool.RedisUtils;
import com.ruoyi.web.core.base.BaseController;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.List;


/**
 * 商城HR
 *
 * @author ChenKang
 */
@Controller
@RequestMapping("/system/hr")
public class HRController extends BaseController {

    //记录日志
    Logger logger = LoggerFactory.getLogger(HRController.class);
    @Autowired
    private OpenTicketService openTicketService;
    //跳转前缀
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
        return prefix + "/openticketflow";
    }


    /**
     * 跳转修改界面
     */
    @RequestMapping("/editOrders")
    public String editOrdersHome() {
        return prefix + "/editOrders";
    }


    /**
     * 跳转开票历史记录页面
     */
    @RequestMapping("/openTicketRecord")
    public String openTicketRecord() {
        return prefix + "/openticketrecord";
    }

    /**
     * 开票界面数据
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(String supplier, String startDate, String endDate) {
        startPage();
        List<OpenTicketInfoCollect> list = openTicketService.list(supplier, startDate, endDate);
        return getDataTable(list);
    }


    /**
     * 开票界面数据
     */
    @PostMapping("/listOrders")
    @ResponseBody
    public TableDataInfo listOrders(String id, String supplier, String startDate, String endDate) {
        startPage();
        OpenTicketParms ps = (OpenTicketParms) RedisUtils.getObject("ps");
        id = ps.getCompanyId();
        supplier = ps.getSupplier();
        startDate = ps.getStartDate();
        endDate = ps.getEndDate();
        List<OpenTicketInfoCollect> list = openTicketService.orderList(id, supplier, startDate, endDate);
        return getDataTable(list);
    }

    /**
     * 存储参数信息
     */
    @RequestMapping("/storeCompanyByRedis")
    public AjaxResult storeCompanyByRedis(String id, String startTime, String endTime, String suppliertype) {
        try {
            OpenTicketParms ps = new OpenTicketParms();
            ps.setCompanyId(id);
            ps.setStartDate(startTime);
            ps.setEndDate(endTime);
            ps.setSupplier(suppliertype);
            Jedis jedis = RedisUtils.getJedis();
            if (jedis.exists("ps")) {
                jedis.del("ps");
            }
            RedisUtils.setObject("ps", ps);
        } catch (Exception e) {
            logger.debug("redis存储失败！" + e.getMessage());
        }
        return AjaxResult.success();
    }


    /**
     * 查询开票抬头
     */
    @PostMapping("/selectInvoice")
    @ResponseBody
    public List<OpenTicketInfoCollect> selectInvoice() {
        List<OpenTicketInfoCollect> list = openTicketService.selectInvoice();
        return list;
    }


    /**
     * 数据同步
     */
    @RequestMapping("/tableWith")
    @ResponseBody
    public AjaxResult tableWith() {
        Integer i = openTicketService.tableWith();
        if (i > 0) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 调整开票抬头
     */
    @RequestMapping("/changeTicketRise")
    @ResponseBody
    public AjaxResult changeTicketRise(@RequestParam(name = "data", required = false) String data,
                                       @RequestParam(name = "rise", required = false) String rise) {
        try {
            if (!StringUtils.isEmpty(data)) {
                JSONArray arrayList = JSONArray.parseArray(data);
                //转换为目标对象list
                List<OpenTicketInfoCollect> groupList = JSONObject.parseArray(arrayList.toJSONString(), OpenTicketInfoCollect.class);
                openTicketService.changeTicketRise(groupList, rise);
            }
        } catch (Exception e) {
            logger.debug("调整开票抬头失败！" + e.getMessage());
        }
        return AjaxResult.success();
    }


}
