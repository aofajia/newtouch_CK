package com.ruoyi.web.controller.system;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.ExcelUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.system.domain.*;
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
import javax.servlet.http.HttpSession;
import java.io.*;
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
	 * 跳转到开卡界面
	 */
	@RequestMapping("/openCard")
	public String openCard() {
		return prefix + "/openCard";
	}

	/**
	 *  跳转到员工信息处
	 */
	@RequestMapping("/employee")
	public String employee() {
		return prefix + "/employee";
	}

	/**
	 * 跳转新增员工界面
	 */
	@RequestMapping("/insertEmployee")
	public String insertEmployee() {
		return prefix + "/insert_employee";
	}


	/**
	 * 跳转修改界面
	 */
	@RequestMapping("/editEmployee")
	public String editEmployee() {
		return prefix + "/edit_employee";
	}

	/**
	 *  跳转到离职提现界面
	 */
	@RequestMapping("/quit")
	public String quit() {
		return prefix + "/quit";
	}

	/**
	 * 导出报表
	 *
	 * @return
	 */
	@RequestMapping(value = "/export")
	@ResponseBody
	public AjaxResult export(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//excel标题
		String[] title = {"员工编号", "员工姓名", "公司名称", "部门名称", "福利费","福利类型"};
		//excel文件名
		String fileName = "welfare" + System.currentTimeMillis() + ".xls";
		//sheet名
		String sheetName = "welfare";
		//创建HSSFWorkbook
		HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, null);
		//响应到客户端
		close(request,response,fileName,wb);
		return AjaxResult.success();
	}


	/**
	 * 导出报表
	 *
	 * @return
	 */
	@RequestMapping(value = "/exportEmployee")
	@ResponseBody
	public AjaxResult exportEmployee(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//excel标题
		String[] title = {"员工编号", "员工姓名", "公司名称", "部门名称", "手机","性别","邮箱"};
		//excel文件名
		String fileName = "Employee" + System.currentTimeMillis() + ".xls";
		//sheet名
		String sheetName = "Employee";
		//创建HSSFWorkbook
		HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, null);
		//响应到客户端
		close(request,response,fileName,wb);
		return AjaxResult.success();
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
	public AjaxResult exportData(@RequestParam(name = "address", required = false) String address) throws IOException {
		return openTicketService.exportData(address);
	}

	/**
	 * 供应商开卡数据同步
	 */
	@RequestMapping("/openCardDataWith")
	@ResponseBody
	public AjaxResult openCardDataWith() {
		return openTicketService.getOpenCardInfo();
	}

	/**
	 * 供应商开卡数据显示
	 */
	@RequestMapping("/openCardDataList")
	@ResponseBody
	public TableDataInfo openCardDataList() {
		startPage();
		List<EmployeeExample> list = openTicketService.openCardDataList();
		return getDataTable(list);
	}


	/**
	 * 供应商开卡数据显示
	 */
	@RequestMapping("/openCardDataListById")
	@ResponseBody
	public TableDataInfo openCardDataListById(@RequestParam("id") String id) {
		startPage();
		List<EmployeeExample> list = openTicketService.openCardDataListById(id);
		return getDataTable(list);
	}

	;

	/**
	 * 饿了么员工开卡
	 */
	@RequestMapping("/employeeOpenCard")
	@ResponseBody
	public AjaxResult employeeOpenCard(@RequestParam(name = "data", required = false) String data) {
		try {
			if (!StringUtils.isEmpty(data)) {
				JSONArray arrayList = JSONArray.parseArray(data);
				//转换为目标对象list
				List<EmployeeExample> list = JSONObject.parseArray(arrayList.toJSONString(), EmployeeExample.class);
				return openTicketService.openCard(list);
			}
		} catch (Exception e) {
			logger.debug("开卡失败！" + e.getMessage());
			return AjaxResult.error();
		}
		return AjaxResult.success();
	}


	/**
	 * 饿了么员工删除
	 */
	@RequestMapping("/employeeDeleteCard")
	@ResponseBody
	public AjaxResult employeeDeleteCard(@RequestParam(name = "data", required = false) String data) {
		try {
			if (!StringUtils.isEmpty(data)) {
				JSONArray arrayList = JSONArray.parseArray(data);
				//转换为目标对象list
				List<EmployeeExample> list = JSONObject.parseArray(arrayList.toJSONString(), EmployeeExample.class);
				return openTicketService.deleteByELM(list);
			}
		} catch (Exception e) {
			logger.debug("饿了么删除失败！" + e.getMessage());
			return AjaxResult.error();
		}
		return AjaxResult.success();
	}

	/**
	 *  携程开卡
	 */
	@RequestMapping("/XCOpenCard")
	@ResponseBody
	public AjaxResult XCOpenCard(@RequestParam(name = "data", required = false) String data) {
		try {
			if (!StringUtils.isEmpty(data)) {
				JSONArray arrayList = JSONArray.parseArray(data);
				//转换为目标对象list
				List<EmployeeExample> list = JSONObject.parseArray(arrayList.toJSONString(), EmployeeExample.class);
				return openTicketService.openCardByXC(list);
			}
		} catch (Exception e) {
			logger.debug("开卡失败！" + e.getMessage());
			return AjaxResult.error();
		}
		return AjaxResult.success();
	}


	/**
	 *  员工信息管理查询
	 */
	@RequestMapping("/employeeList")
	@ResponseBody
	public TableDataInfo employeeList(String id,String company) {
		startPage();
		return getDataTable(openTicketService.employeeList(id,company));
	}


	/**
	 *  福利商城查询
	 */
	@RequestMapping("/selectWelfare")
	@ResponseBody
	public TableDataInfo selectWelfare() {
		startPage();
		return getDataTable(openTicketService.listWelfare());
	}


	/**
	 *  JD开票
	 */
	@RequestMapping("/JDOpenTicket")
	@ResponseBody
	public AjaxResult JDOpenTicket() {
		OpenTicketParms ps = (OpenTicketParms) RedisUtils.getObject("ps");
		String id = ps.getCompanyId();
		String supplier = ps.getSupplier();
		String startDate = ps.getStartDate();
		String endDate = ps.getEndDate();
		List<OpenTicketInfoCollect> list = openTicketService.orderList(id, supplier, startDate, endDate);
		openTicketService.JDOpenCard(list);
		return null;
	}

	/**
	 * 导入员工信息
	 */
    @RequestMapping("/exportDataEmployee")
    @ResponseBody
    public AjaxResult exportDataEmployee(@RequestParam(name = "address", required = false) String address, HttpSession session) throws IOException {
        return openTicketService.exportDataEmployee(address,session);
    }

	/**
	 * 新增员工
	 */
	@RequestMapping("/addEmployee")
	@ResponseBody
	public AjaxResult addEmployee(HRFI_Employee employee){
		return openTicketService.addEmployee(employee);
	}

	/**
	 * 新增员工
	 */
	@RequestMapping("/updateEmployee")
	@ResponseBody
	public AjaxResult editEmployee(HRFI_Employee employee){
		return openTicketService.editEmployee(employee);
	}

	/**
	 *  存储员工信息
	 */
	@RequestMapping("/storeEmployeeByRedis")
	public AjaxResult storeEmployeeByRedis(HRFI_Employee employee) {
		try {
			Jedis jedis = RedisUtils.getJedis();
			if (jedis.exists("employee")) {
				jedis.del("employee");
			}
			RedisUtils.setObject("employee",employee);
		} catch (Exception e) {
			logger.debug("redis存储失败！" + e.getMessage());

		}
		return AjaxResult.success();
	}


	/**
	 *  查看员工信息
	 */
	@RequestMapping("/selectEmployeeByRedis")
	@ResponseBody
	public HRFI_Employee selectEmployeeByRedis() {
		HRFI_Employee employee = new HRFI_Employee();
		try {
			employee = (HRFI_Employee)RedisUtils.getObject("employee");
		} catch (Exception e) {
			logger.debug("查看员工信息失败！" + e.getMessage());
		}
		return employee;
	}

	/**
	 * 离职提现一览
	 */
	@RequestMapping("/quitList")
	@ResponseBody
	public TableDataInfo quitList(String id){
		startPage();
		List<WithDrawals> list = openTicketService.quitList(id);
		return getDataTable(list);
	}



	private void close(HttpServletRequest request, HttpServletResponse response,String fileName,HSSFWorkbook wb){
	   try {
		   this.setResponseHeader(response, fileName);
		   OutputStream os = response.getOutputStream();
		   wb.write(os);
		   os.flush();
		   os.close();
	   } catch (Exception e) {
		   logger.debug(e.getMessage());
	   }
   }
}
