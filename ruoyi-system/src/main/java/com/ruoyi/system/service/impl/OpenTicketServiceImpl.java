package com.ruoyi.system.service.impl;

import com.google.common.collect.Lists;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.Md5Utils;
import com.ruoyi.common.utils.ReadExcel;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.OpenTicketService;
import com.ruoyi.system.utils.NumberArithmeticUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpSession;

import static com.ruoyi.common.utils.http.HttpClient.sendPost;

@Service
public class OpenTicketServiceImpl implements OpenTicketService {

    //JD一级地址
    private final String JD_FIRST_ADDRESS = "http://test.third-party.newtouch.com/jdmp/ntpmp-api/query-province";
    //携程开卡
    private final String XC_OPEN_CARD = "http://third-party.newtouch.com/ctripmp/ntpmp-api/send-personnel-info";
    //饿了么全员同步地址
    private final String ELM_OPEN_CARD_WITH = "http://third-party.newtouch.com/elemp/ntpmp-api/batch-employee";
    //饿了么开卡地址
    private final String ELM_OPEN_CARD = "http://third-party.newtouch.com/elemp/ntpmp-api/add-employee";
    //饿了么删除地址
    private final String ELM_DELETE_CARD = "http://third-party.newtouch.com/elemp/ntpmp-api/delete-employee";
    //获取新员工地址
    private final String NEW_EMPLOYEE = "http://59.80.30.153:4090/HR/getNewEmpoloyeeInfo";
    //京东开卡
    private final String JD_OPEN_CARD = "";
    //记录日志
    Logger logger = LoggerFactory.getLogger(OpenTicketServiceImpl.class);
    //注入开票订单表
    @Autowired
    private OpenTicketMapper openTicketMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private WelfareMapper welfareMapper;
    @Autowired
    private HRFI_EmployeeMapper hrfi_employeeMapper;
    @Autowired
    private WithDrawalsMapper withDrawalsMapper;

    public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
    }

    @Override
    public List<OpenTicketInfoCollect> list(String supplier, String startDate, String endDate) {
        List<OpenTicketInfoCollect> list = Lists.newArrayList();
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
            if (supplier.equals("0")) {
                supplier = null;
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
            JSONObject object = null;
            Employee employee = null;
            for (int i = 0; i < jsonArray.size(); i++) {
                object = jsonArray.getJSONObject(i);
                employee = new Employee();
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
    public AjaxResult openCard(List<EmployeeExample> list) {
        try {
            String result = NumberArithmeticUtils.sendPost(ELM_OPEN_CARD, params(list), "utf-8", "application/json", NumberArithmeticUtils.ProLogObject(list).toString());
            JSONObject jsonObject = JSON.parseObject(result);
            //解析数据
            String data = jsonObject.getString("result");
            JSONObject objectCode = JSON.parseObject(data);
            //解析返回值
            String code = objectCode.getString("code");
            //如果为200表示成功
            if (code.equals("200")) {
                //修改状态
                for (EmployeeExample example : list) {
                    employeeMapper.updateByEmpoloyeeId(example.getEmployeeno(), 1);
                }
                return AjaxResult.success("饿了么开卡成功！");
            } else {
                return AjaxResult.error("饿了么开卡失败！");
            }
        } catch (Exception e) {
            logger.debug("开卡失败！" + e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }

    @Override
    public List<EmployeeExample> openCardDataListById(String id) {
        return employeeMapper.openCardDataListById(id);
    }

    @Override
    public AjaxResult deleteByELM(List<EmployeeExample> list) {
        try {
            //调用地址
            String result = NumberArithmeticUtils.sendPost(ELM_DELETE_CARD, params(list), "utf-8", "application/json", NumberArithmeticUtils.ProLogObject(list).toString());
            JSONObject jsonObject = JSON.parseObject(result);
            //解析数据
            String data = jsonObject.getString("result");
            JSONObject objectCode = JSON.parseObject(data);
            //解析返回值
            String code = objectCode.getString("code");
            //如果为200表示成功
            if (code.equals("200")) {
                //修改状态
                for (EmployeeExample example : list) {
                    employeeMapper.updateByEmpoloyeeId(example.getEmployeeno(), 0);
                }
                return AjaxResult.success("饿了么删除成功！");
            } else {
                return AjaxResult.error("饿了么删除失败！！");
            }
        } catch (Exception e) {
            return AjaxResult.error("饿了么删除失败！" + e.getMessage());
        }
    }

    @Transactional
    @Override
    public AjaxResult getNewEmployeeTask() {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            String nowDate = df.format(new Date());// new Date()为获取当前系统时间
            Map<String, String> parameters = new LinkedHashMap<>();
            parameters.put("date", nowDate);
            String result = sendPost(NEW_EMPLOYEE, parameters);
            JSONObject jsonObject = JSON.parseObject(result);
            //获取返回值
            String code = jsonObject.getString("code");
            //如果为1即表示成功
            if (code.equals("1")) {
                //获取data
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                Employee employee = null;
                JSONObject object = null;
                //表示今天没有新近员工
                if (jsonArray.size() == 0) {
                    logger.debug(nowDate + "今天没有新近员工！");
                    return AjaxResult.success();
                } else {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        object = jsonArray.getJSONObject(i);
                        //把数据装进实体类
                        employee = new Employee();
                        employee.setEmployeeno(object.getString("employeeId"));
                        employee.setCompanyId(object.getString("companyId"));
                        employee.setName(object.getString("employeeName"));
                        employee.setDeptid(object.getString("departmentId"));
                        employee.setEmail(object.getString("email"));
                        employee.setGender("男");
                        employee.setPhonenumber(object.getString("phone"));
                        employeeMapper.insertSelective(employee);
                    }
                    logger.debug("添加新员工成功，当前时间为" + nowDate);
                }
            } else {
                logger.debug("调用接口失败！");
            }

        } catch (Exception e) {
            logger.debug("定时器获取新员工插入失败！" + e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult openCardByXC(List<EmployeeExample> examples) {
        try {
            Map<String, Object> paramMap1 = new LinkedHashMap<String, Object>();
            paramMap1.put("appId", "newtouchmall");
            paramMap1.put("personalInformation", NumberArithmeticUtils.ProLogList2Json(examples));
            org.json.JSONObject json = new org.json.JSONObject(paramMap1);
            String result = NumberArithmeticUtils.sendPost(XC_OPEN_CARD, XCParams(examples), "utf-8", "application/json", json.toString());
            JSONObject jsonObject = JSON.parseObject(result);
            //解析数据
            String data = jsonObject.getString("result");
            JSONObject objectCode = JSON.parseObject(data);
            //解析返回值
            String code = objectCode.getString("result");
            //如果为200表示成功
            if (code.equals("Success")) {
                //修改状态
                for (EmployeeExample example : examples) {
                    employeeMapper.updateByEmpoloyeeId(example.getEmployeeno(), 3);
                }
                return AjaxResult.success("携程开卡成功！");
            } else {
                return AjaxResult.error("携程开卡失败！");
            }
        } catch (Exception e) {
            logger.debug("携程开卡失败！" + e.getMessage());
            return AjaxResult.error();
        }
    }

    @Transactional
    @Override
    public AjaxResult exportData(String address) {
        try {
            // 对读取Excel表格标题测试
            InputStream is = new FileInputStream(address);
            ReadExcel excelReader = new ReadExcel();
            String[] title = excelReader.readExcelTitle(is);
            //获得Excel表格的标题
            for (String s : title) {
                System.out.print(s + " ");
            }
            // 对读取Excel表格内容测试
            InputStream is2 = new FileInputStream(address);
            Map<Integer, String> map = excelReader.readExcelContent(is2);
            //获得Excel表格的内容

            //这里由于xls合并了单元格需要对索引特殊处理
            Welfare welfare = null;
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 1; i <= map.size() - 1; i++) {
                String s = map.get(i);
                String[] sData = s.split("-");
                //获取每个单元格的数据
                if (sData[0].indexOf(".") >= 0) {
                    welfare = new Welfare();
                    welfare.setId(sData[0].substring(0, sData[0].length() - 2));
                    welfare.setName(sData[1]);
                    welfare.setDate(df.format(new Date()));
                    welfare.setCompany(sData[2]);
                    welfare.setDept(sData[3]);
                    welfare.setWelfare(sData[4]);
                    welfare.setUuid(welfareMapper.uuid());
                    welfareMapper.insertSelective(welfare);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("未找到指定路径的文件!");
            e.printStackTrace();
        }
        return AjaxResult.success();
    }

    @Override
    public List<Welfare> listWelfare() {
        return welfareMapper.listWelfare();
    }

    @Override
    public AjaxResult JDOpenCard(List<OpenTicketInfoCollect> list) {
        try {
            Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
            paramMap.put("appId", "newtouchmall");
            paramMap.put("settlementId", "");//结算单号（一个结算单号可对对应多个第三方申请单号）
            paramMap.put("invoiceType", 1);//发票类型   1普通 2增值税
            paramMap.put("invoiceOrg", "1"); //开票机构id
            paramMap.put("invoiceDate", "1");//期望开票时间
            paramMap.put("billToParty", "1");//收票单位 （填写开票省份）
            paramMap.put("billToer", "吴亿笛");//收票人
            paramMap.put("billToContact", "");//收票人联系方式
            paramMap.put("billToProvince", 0);//收票人地址（省）
            paramMap.put("billToCity", 0);//收票人地址（市）
            paramMap.put("billToCounty", 0);//收票人地址（区）
            paramMap.put("billToTown", 0);//收票人地址（街道）
            paramMap.put("billToAddress", "");//收票人地址（详细地址）
            paramMap.put("orderAmountCash", new BigDecimal(1));//总金额
            paramMap.put("invoiceParticulars", list);//开票详情（限制在500个订单之内）

        } catch (Exception e) {
            logger.debug("申请京东发表失败！" + e.getMessage());
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    @Transactional
    @Override
    public AjaxResult exportDataEmployee(String address, HttpSession session) {
        try {
            // 对读取Excel表格标题测试
            InputStream is = new FileInputStream(address);
            ReadExcel excelReader = new ReadExcel();
            // 对读取Excel表格内容测试
            InputStream is2 = new FileInputStream(address);
            Map<Integer, String> map = excelReader.readExcelContent(is2);
            //获得Excel表格的内容
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            DecimalFormat sf = new DecimalFormat("#");
            //这里由于xls合并了单元格需要对索引特殊处理
            HRFI_Employee employee = null;
            //SysUser currentUser = ShiroUtils.getUser();
            for (int i = 1; i <= map.size() - 1; i++) {
                String s = map.get(i);
                String[] sData = s.split("-");
                //获取每个单元格的数据
                if (sData[0].indexOf(".") >= 0) {
                    employee = new HRFI_Employee();
                    employee.setId(sData[0].substring(0, sData[0].length() - 2));
                    employee.setName(sData[1]);
                    employee.setCompany(sData[2]);
                    employee.setDept(sData[3]);
                    employee.setPhone(sf.parse(sData[4]).toString());
                    employee.setGender(sData[5]);
                    employee.setEmail(sData[6]);
                    employee.setEntryDate(df.format(new Date()));
                    employee.setUpdateDate(session.getId());
                    employee.setUpdateDate(df.format(new Date()));
                    hrfi_employeeMapper.insertSelective(employee);
                }
            }

        } catch (FileNotFoundException e) {
            logger.debug("导入员工信息数据失败！" + e.getMessage());
            AjaxResult.error();
        } catch (ParseException e) {
            logger.debug("类型转换失败！" + e.getMessage());
            AjaxResult.error();
        }
        return AjaxResult.success();
    }

    @Override
    public List<HRFI_Employee> employeeList(String id, String company) {
        return hrfi_employeeMapper.list(id, company);
    }

    @Override
    public AjaxResult addEmployee(HRFI_Employee employee) {
        try {
            Integer i = hrfi_employeeMapper.insertSelective(employee);
            if (i > 0) {
                return AjaxResult.success();
            } else {
                return AjaxResult.error();
            }
        } catch (Exception e) {
            logger.debug("新增员工失败" + e.getMessage());
            return AjaxResult.error();
        }
    }

    @Override
    public AjaxResult editEmployee(HRFI_Employee employee) {
        try {
            Integer i = hrfi_employeeMapper.updateByPrimaryKeySelective(employee);
            if (i > 0) {
                return AjaxResult.success();
            } else {
                return AjaxResult.error();
            }
        } catch (Exception e) {
            logger.debug("修改员工失败" + e.getMessage());
            return AjaxResult.error();
        }
    }

    @Override
    public List<WithDrawals> quitList(String id) {
        return withDrawalsMapper.list(id);
    }

    @Override
    public List<JDAddress> firstLevelAddress() {
        List<JDAddress> list = new ArrayList<>();
        int count = 3;
        do {
            try {
                Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
                paramMap.put("appId", "newtouchmall");
                org.json.JSONObject json = new org.json.JSONObject(paramMap);
                String result = NumberArithmeticUtils.sendPost(JD_FIRST_ADDRESS, JDParams(json.toString()), "utf-8", "application/json", json.toString());
                JSONObject jsonObject = JSON.parseObject(result);
                //解析数据
                JDAddress jdAddress = null;
                String success = jsonObject.getString("success");
                if (success.equals("true")) {
                    String data = jsonObject.getString("result");
                    String sData = data.substring(1, data.length() - 1);
                    String[] address = sData.split(",");
                    list = JDAddressResolve(address, list, jdAddress);
                    if (jumpOut(list)){
                        break;
                    }
                } else {
                    logger.debug("调用京东一级地址失败！" + System.currentTimeMillis());
                }
            } catch (Exception e) {
                logger.debug("调用京东一级地址异常！" + e.getMessage());
                e.getMessage();
            }
            count--;
        }while (count > 1);
        return list;
    }

    @Override
    public List<JDAddress> otherLevelAddress(Integer id, String address) {
        List<JDAddress> list = new ArrayList<>();
        int count = 3;
        do {
            try {
                Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
                paramMap.put("appId", "newtouchmall");
                paramMap.put("id", id);
                org.json.JSONObject json = new org.json.JSONObject(paramMap);
                String result = NumberArithmeticUtils.sendPost(address, JDParams(json.toString()), "utf-8", "application/json", json.toString());
                JSONObject jsonObject = JSON.parseObject(result);
                //解析数据
                String success = jsonObject.getString("success");
                JDAddress jdAddress = null;
                if (success.equals("true")) {
                    String data = jsonObject.getString("result");
                    String sData = data.substring(1, data.length() - 1);
                    String[] sAddress = sData.split(",");
                    list = JDAddressResolve(sAddress, list, jdAddress);
                    if (jumpOut(list)){
                        break;
                    }else{
                        continue;
                    }
                } else {
                    logger.debug("调用京东地址失败！" + System.currentTimeMillis());
                }
            } catch (Exception e) {
                logger.debug("调用京东地址失败！" + e.getMessage());
                e.getMessage();
            }
        }while (count > 1);

        return list;
    }

    //跳出循环
    private boolean jumpOut(Object obj){
        if (obj != null){
            return true;
        }else{
            return false;
        }
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
        card = (number << 2 == 0) ? "女" : "男";
        return card;
    }


    /**
     * 解析京东地址算法
     */
    private List<JDAddress> JDAddressResolve(String[] data, List<JDAddress> list, JDAddress jdAddress) {
        for (int i = 0; i < data.length; i++) {
            String[] nextAddress = data[i].split(":");
            jdAddress = new JDAddress();
            jdAddress.setAddressName(nextAddress[0]);
            jdAddress.setId(Integer.parseInt(nextAddress[1]));
            list.add(jdAddress);
        }
        return list;
    }


    /**
     * 对接京东地址sign
     */
    private Map<String, Object> JDParams(String object) {
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
        StringBuffer stringBuffer = new StringBuffer();
        long time = System.currentTimeMillis();
        stringBuffer.append(time);
        stringBuffer.append(object);
        stringBuffer.append("651e83d0ef47603076d99601ceb95d64");
        String md5 = Md5Utils.string2MD5(stringBuffer.toString());
        paramMap.put("appId", "newtouchmall");
        paramMap.put("timestamp", time);
        paramMap.put("sign", md5.toUpperCase());
        return paramMap;
    }

    /**
     * 对接JD sign算法
     */
    private Map<String, Object> params(List<EmployeeExample> list) {
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
        StringBuffer stringBuffer = new StringBuffer();
        long time = System.currentTimeMillis();
        stringBuffer.append(time);
        stringBuffer.append(NumberArithmeticUtils.ProLogObject(list).toString());
        stringBuffer.append("ac063f15ccff416b9a2278318920926f");
        String md5 = Md5Utils.string2MD5(stringBuffer.toString());
        paramMap.put("appId", "newtouchmall");
        paramMap.put("timestamp", time);
        paramMap.put("sign", md5.toUpperCase());
        return paramMap;
    }

    /**
     * 对接携程 sign算法
     */
    private Map<String, Object> XCParams(List<EmployeeExample> list) {
        Map<String, Object> paramMap1 = new LinkedHashMap<String, Object>();
        paramMap1.put("appId", "newtouchmall");
        paramMap1.put("personalInformation", NumberArithmeticUtils.ProLogList2Json(list));
        org.json.JSONObject json = new org.json.JSONObject(paramMap1);
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
        StringBuffer stringBuffer = new StringBuffer();
        long time = System.currentTimeMillis();
        stringBuffer.append(time);
        stringBuffer.append(json.toString());
        stringBuffer.append("ac063f15ccff416b9a2278318920926f");
        String md5 = Md5Utils.string2MD5(stringBuffer.toString());
        paramMap.put("appId", "newtouchmall");
        paramMap.put("timestamp", time);
        paramMap.put("sign", md5.toUpperCase());
        return paramMap;
    }


}
