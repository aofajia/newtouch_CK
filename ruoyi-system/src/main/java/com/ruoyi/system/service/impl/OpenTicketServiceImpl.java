package com.ruoyi.system.service.impl;

import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.Md5Utils;
import com.ruoyi.system.domain.Employee;
import com.ruoyi.system.domain.EmployeeExample;
import com.ruoyi.system.domain.OpenTicketInfoCollect;
import com.ruoyi.system.mapper.EmployeeMapper;
import com.ruoyi.system.mapper.OpenTicketMapper;
import com.ruoyi.system.service.OpenTicketService;
import com.ruoyi.system.utils.NumberArithmeticUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import static com.ruoyi.common.utils.http.HttpClient.sendPost;

@Service
public class OpenTicketServiceImpl implements OpenTicketService {

    //记录日志
    Logger logger = LoggerFactory.getLogger(OpenTicketServiceImpl.class);
    //注入开票订单表
    @Autowired
    private OpenTicketMapper openTicketMapper;
    @Autowired
    private EmployeeMapper employeeMapper;


    @Override
    public List<OpenTicketInfoCollect> list(String supplier, String startDate, String endDate) {
        List<OpenTicketInfoCollect> list = new ArrayList<>();
        List<OpenTicketInfoCollect> listRise = new ArrayList<>();
        try {
            //获取总金额
            Double num = openTicketMapper.order_num(supplier, startDate, endDate);
            //计算比列
            Double sum = 0.0;
            //获取订单信息
            list = openTicketMapper.list(supplier, startDate, endDate);
            for (OpenTicketInfoCollect collect : list) {
                //根据开票抬头获取订单信息
                listRise = openTicketMapper.listByRise(supplier, startDate, endDate, collect.getCompany_id());
                for (int i = 0; i < listRise.size(); i++) {
                    for (OpenTicketInfoCollect rise : listRise) {
                        sum += rise.getMoney_num();
                    }
                }
                BigDecimal b = NumberArithmeticUtils.safeDivide(sum, num);
                collect.setAccount(b + "%");
                collect.setAccount(collect.getAccount().substring(2, collect.getAccount().length()));
                if (collect.getAccount().indexOf("0") >= 0) {
                    collect.setAccount(collect.getAccount().substring(1, collect.getAccount().length()));
                }
                //collect.setMoney_num(sum);
                sum = 0.0;
            }


        } catch (Exception e) {
            logger.debug("开票信息初始化失败！" + e.getMessage());
        }
        return list;
    }

    @Override
    public List<OpenTicketInfoCollect> orderList(String id, String supplier, String startDate, String endDate) {
        List<OpenTicketInfoCollect> list = new ArrayList<>();
        try {
            //使开始日期和结束日期为null
            if (startDate.equals("0") || endDate.equals("0")) {
                startDate = null;
                endDate = null;
            }
            //获取订单明细信息
            list = openTicketMapper.listOrders(id, supplier, startDate, endDate);
        } catch (Exception e) {
            logger.debug("获取开票订单信息失败!" + e.getMessage());
        }
        return list;
    }

    @Override
    public List<OpenTicketInfoCollect> selectInvoice() {
        List<OpenTicketInfoCollect> list = new ArrayList<>();
        try {
            list = openTicketMapper.selectInvoice();
        } catch (Exception e) {
            logger.debug("查询开票抬头信息失败！" + e.getMessage());
        }
        return list;
    }

    @Transactional
    @Override
    public Integer tableWith() {
        Integer i = null;
        try {
            //如果存在表，先删除
            openTicketMapper.tableWith();
            //表创建
            openTicketMapper.tableCreate();
            //数据同步
            openTicketMapper.tableInsert();
            i = 1;
        } catch (Exception e) {
            logger.debug("表同步数据失败！");
        }
        return i;
    }

    @Override
    public AjaxResult changeTicketRise(List<OpenTicketInfoCollect> list, String rise) {
        try {
            //修改发票抬头
            for (OpenTicketInfoCollect collect : list) {
                openTicketMapper.updateTicketRise(collect.getOrdersId(), rise);
            }
        } catch (Exception e) {
            logger.debug("调整发票信息失败！" + e.getMessage());
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    @Transactional
    @Override
    public AjaxResult getOpenCardInfo() {
        try {
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("code", "23");
            String result = sendPost("http://localhost:4090/HR/getOpenCardMassage", parameters);
            JSONObject jsonObject = JSON.parseObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Employee employee = new Employee();
                String departmentName = object.getString("departmentName");
                String employeeName = object.getString("employeeName");
                String phone = object.getString("phone");
                String employeeId = object.getString("employeeId");
                String companyId = object.getString("companyId");
                String idCard = object.getString("idCard");
                String email = object.getString("email");
                employee.setDeptid(departmentName);
                employee.setEmployeeno(employeeId);
                employee.setName(employeeName);
                employee.setPhonenumber(phone);
                employee.setCompanyId(companyId);
                employee.setEmail(email);
                if (idCard.length() == 18) {
                    employee.setGender(getGender(idCard));
                }
                employeeMapper.insertSelective(employee);
            }
            return AjaxResult.success();
        } catch (Exception e) {
            logger.debug("供应商开卡同步数据失败！" + e.getMessage());
            return AjaxResult.error();
        }
    }

    @Override
    public List<EmployeeExample> openCardDataList() {
        List<EmployeeExample> list = new ArrayList<>();
        try {
            list = employeeMapper.openCardDataList();
        } catch (Exception e) {
            logger.debug("查询供应商分页信息失败！" + e.getMessage());
        }
        return list;
    }

    @Override
    public AjaxResult openCard(List<Employee> list) {
        List<String> sum = new ArrayList<>();
        try {
            if (list != null) {
                for (Employee employee : list) {
                    sum.add(employee.getEmployeeno());
                    List<EmployeeExample> examples = employeeMapper.openCardData(sum);
                    //调用地址
                    String result = NumberArithmeticUtils.sendPost("http://third-party.newtouch.com/elemp/ntpmp-api/batch-employee",params(examples), "utf-8", "application/json",NumberArithmeticUtils.ProLogList2Json(examples).toString());
                    JSONObject jsonObject = JSON.parseObject(result);
                    //解析数据
                    String data = jsonObject.getString("result");
                    JSONObject objectCode = JSON.parseObject(data);
                    //解析返回值
                    String code = objectCode.getString("code");
                    //如果为200表示成功
                    if (code.equals("200")){
                        logger.debug(employee.getEmployeeno()+"开卡成功！");
                    }else{
                        logger.debug("开卡失败");
                    }
                    //修改状态
                    employeeMapper.updateByEmpoloyeeId(employee.getEmployeeno());

                }
            }
        } catch (Exception e) {
            logger.debug("开卡失败！" + e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<EmployeeExample> openCardDataListById(String id) {
        return employeeMapper.openCardDataListById(id);
    }


    /**
     * 计算百分比
     */
    private Double sumAccount(Double i, Double j) {
        return i * 1.0 / j;
    }

    /**
     * 保留两位小数
     */
    private String getTwoDecimal(Double d) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(d);
    }


    /**
     * 判断奇数偶数
     */
    private String getGender(String card) {
        String num = card.substring(16, 17);
        Integer number = Integer.parseInt(num);
        card = (number % 2 == 0) ? "女" : "男";
        return card;
    }


    /**
     *  参数配置
     */
    private Map<String, Object> params(List<EmployeeExample> list){
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
        StringBuffer stringBuffer = new StringBuffer();
        long time = System.currentTimeMillis();
        stringBuffer.append(time);
        stringBuffer.append(NumberArithmeticUtils.ProLogList2Json(list).toString());
        stringBuffer.append("ac063f15ccff416b9a2278318920926f");
        String md5 = Md5Utils.string2MD5(stringBuffer.toString());
        paramMap.put("appId","newtouchmall");
        paramMap.put("timestamp",time);
        paramMap.put("sign",md5.toUpperCase());
        return paramMap;
    }


}
