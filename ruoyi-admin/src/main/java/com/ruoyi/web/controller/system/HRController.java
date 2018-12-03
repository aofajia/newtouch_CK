package com.ruoyi.web.controller.system;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.ExcelUtil;
import com.ruoyi.common.utils.ReadExcel;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.system.domain.Employee;
import com.ruoyi.system.domain.EmployeeExample;
import com.ruoyi.system.domain.OpenTicketInfoCollect;
import com.ruoyi.system.domain.OpenTicketParms;
import com.ruoyi.system.service.OpenTicketService;
import com.ruoyi.web.controller.tool.RedisUtils;
import com.ruoyi.web.core.base.BaseController;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;



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
     * 跳转到开卡界面
     */
    @RequestMapping("/openCard")
    public String openCard() {
        return prefix + "/openCard";
    }

    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //excel标题
        String[] title = {"员工编号", "员工姓名", "公司名称", "部门名称", "饭贴", "期间", "福利", "工会费"};
        //excel文件名
        String fileName = "福利信息汇总" + System.currentTimeMillis() + ".xls";
        //sheet名
        String sheetName = "福利信息汇总";
        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, null);
        //响应到客户端
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                logger.debug("发送响应流方法" + e.getMessage());
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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


    @RequestMapping("/exportData")
    @ResponseBody
    public AjaxResult exportData(@RequestParam(name = "address", required = false) String address) {
        try {
            String data = address.replaceAll("/", "//");
            // 对读取Excel表格标题测试
            InputStream is = new FileInputStream(address);
            ReadExcel excelReader = new ReadExcel();
            String[] title = excelReader.readExcelTitle(is);
            System.out.println("获得Excel表格的标题:");
            for (String s : title) {
                System.out.print(s + " ");
            }
            // 对读取Excel表格内容测试
            InputStream is2 = new FileInputStream(address);
            Map<Integer, String> map = excelReader.readExcelContent(is2);
            System.out.println("获得Excel表格的内容:");
            //这里由于xls合并了单元格需要对索引特殊处理
            for (int i = 1; i <= map.size() - 1; i++) {
                System.out.println(map.get(i));
                String s = map.get(i);
                String[] sdata = s.split("-");
            }
        } catch (FileNotFoundException e) {
            System.out.println("未找到指定路径的文件!");
            e.printStackTrace();
        }
        return AjaxResult.success();
    }


    /**
     *  供应商开卡数据同步
     */
    @RequestMapping("/openCardDataWith")
    @ResponseBody
    public AjaxResult openCardDataWith(){
        return openTicketService.getOpenCardInfo();
    }


    /**
     *  供应商开卡数据显示
     */
    @RequestMapping("/openCardDataList")
    @ResponseBody
    public TableDataInfo openCardDataList(){
        startPage();
        List<EmployeeExample> list = openTicketService.openCardDataList();
        return getDataTable(list);
    }


    /**
     *  供应商开卡数据显示
     */
    @RequestMapping("/openCardDataListById")
    @ResponseBody
    public TableDataInfo openCardDataListById(@RequestParam("id")String id){
        startPage();
        List<EmployeeExample> list = openTicketService.openCardDataListById(id);
        return getDataTable(list);
    }


    /**
     * 员工开卡
     */
    @RequestMapping("/employeeOpenCard")
    @ResponseBody
    public AjaxResult employeeOpenCard(@RequestParam(name = "data", required = false) String data) {
        try {
            if (!StringUtils.isEmpty(data)) {
                JSONArray arrayList = JSONArray.parseArray(data);
                //转换为目标对象list
                List<Employee> list = JSONObject.parseArray(arrayList.toJSONString(), Employee.class);
                openTicketService.openCard(list);
            }
        } catch (Exception e) {
            logger.debug("开卡失败！" + e.getMessage());
        }
        return AjaxResult.success();
    }

    public static void main(String[] args) {
        String id = "430481199608204534";
        System.out.println(id.substring(16,17));
        Integer number = Integer.parseInt(id.substring(16,17));
        String check = (number % 2 == 0) ? "这个数字是:偶数" : "这个数字是：奇数";
        System.out.println(check);
    }

}
