package com.ruoyi.system.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ruoyi.system.domain.EmployeeExample;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import com.google.common.base.Optional;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberArithmeticUtils {


    /**
     * BigDecimal的加法运算封装
     *
     * @param b1
     * @param bn
     * @return
     * @author : shijing
     * 2017年3月23日下午4:53:21
     */
    public static BigDecimal safeAdd(BigDecimal b1, BigDecimal... bn) {
        if (null == b1) {
            b1 = BigDecimal.ZERO;
        }
        if (null != bn) {
            for (BigDecimal b : bn) {
                b1 = b1.add(null == b ? BigDecimal.ZERO : b);
            }
        }
        return b1;
    }

    /**
     * Integer加法运算的封装
     *
     * @param b1 第一个数
     * @param bn 需要加的加法数组
     * @return
     * @author : shijing
     * 2017年3月23日下午4:54:08
     * @注 ： Optional  是属于com.google.common.base.Optional<T> 下面的class
     */
    public static Integer safeAdd(Integer b1, Integer... bn) {
        if (null == b1) {
            b1 = 0;
        }
        Integer r = b1;
        if (null != bn) {
            for (Integer b : bn) {
                r += Optional.fromNullable(b).or(0);
            }
        }
        return r > 0 ? r : 0;
    }

    /**
     * 计算金额方法
     *
     * @param b1
     * @param bn
     * @return
     * @author : shijing
     * 2017年3月23日下午4:53:00
     */
    public static BigDecimal safeSubtract(BigDecimal b1, BigDecimal... bn) {
        return safeSubtract(true, b1, bn);
    }

    /**
     * BigDecimal的安全减法运算
     *
     * @param isZero 减法结果为负数时是否返回0，true是返回0（金额计算时使用），false是返回负数结果
     * @param b1     被减数
     * @param bn     需要减的减数数组
     * @return
     * @author : shijing
     * 2017年3月23日下午4:50:45
     */
    public static BigDecimal safeSubtract(Boolean isZero, BigDecimal b1, BigDecimal... bn) {
        if (null == b1) {
            b1 = BigDecimal.ZERO;
        }
        BigDecimal r = b1;
        if (null != bn) {
            for (BigDecimal b : bn) {
                r = r.subtract((null == b ? BigDecimal.ZERO : b));
            }
        }
        return isZero ? (r.compareTo(BigDecimal.ZERO) == -1 ? BigDecimal.ZERO : r) : r;
    }

    /**
     * 整型的减法运算，小于0时返回0
     *
     * @param b1
     * @param bn
     * @return
     * @author : shijing
     * 2017年3月23日下午4:58:16
     */
    public static Integer safeSubtract(Integer b1, Integer... bn) {
        if (null == b1) {
            b1 = 0;
        }
        Integer r = b1;
        if (null != bn) {
            for (Integer b : bn) {
                r -= Optional.fromNullable(b).or(0);
            }
        }
        return null != r && r > 0 ? r : 0;
    }

    /**
     * 金额除法计算，返回2位小数（具体的返回多少位大家自己看着改吧）
     *
     * @param b1
     * @param b2
     * @return
     * @author : shijing
     * 2017年3月23日下午5:02:17
     */
    public static <T extends Number> BigDecimal safeDivide(T b1, T b2) {
        return safeDivide(b1, b2, BigDecimal.ZERO);
    }

    /**
     * BigDecimal的除法运算封装，如果除数或者被除数为0，返回默认值
     * 默认返回小数位后2位，用于金额计算
     *
     * @param b1
     * @param b2
     * @param defaultValue
     * @return
     * @author : shijing
     * 2017年3月23日下午4:59:29
     */
    public static <T extends Number> BigDecimal safeDivide(T b1, T b2, BigDecimal defaultValue) {
        if (null == b1 || null == b2) {
            return defaultValue;
        }
        try {
            return BigDecimal.valueOf(b1.doubleValue()).divide(BigDecimal.valueOf(b2.doubleValue()), 2, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * BigDecimal的乘法运算封装
     *
     * @param b1
     * @param b2
     * @return
     * @author : shijing
     * 2017年3月23日下午5:01:57
     */
    public static <T extends Number> BigDecimal safeMultiply(T b1, T b2) {
        if (null == b1 || null == b2) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(b1.doubleValue()).multiply(BigDecimal.valueOf(b2.doubleValue())).setScale(2, BigDecimal.ROUND_HALF_UP);
    }


    public static Double divide(Double val1, Double val2, int scale) {
        if (null == val1) {
            val1 = new Double(0);
        }
        if (null == val2 || val2 == 0) {
            val2 = new Double(1);
        }
        return new BigDecimal(Double.toString(val1)).divide(new BigDecimal(Double.toString(val2)))
                .setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    public static String sendPost(String url, Map<String, Object> params, String charset, String contentType, String requestBody) throws ClientProtocolException, IOException {


        RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(3000).build();
        CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(url);
        if (params != null && !params.isEmpty()) {
            stringBuffer.append("?");
            for (String key : params.keySet()) {
                stringBuffer.append(key).append("=").append(params.get(key)).append("&");
            }

        }
        String finalUrl = stringBuffer.toString();
        if (finalUrl.endsWith("&")) {
            finalUrl = finalUrl.substring(0, finalUrl.lastIndexOf("&"));
        }
        HttpPost httpPost = new HttpPost(finalUrl);
        httpPost.setHeader("Content-type", contentType);
        if (requestBody != null) {
            httpPost.setEntity(new StringEntity(requestBody));
        }

        CloseableHttpResponse response = httpclient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            httpPost.abort();
            throw new RuntimeException("HttpClient,error status code :" + statusCode);
        }
        HttpEntity entity = response.getEntity();
        String result = null;
        if (entity != null) {
            result = EntityUtils.toString(entity, charset);
            EntityUtils.consume(entity);
            response.close();
            return result;
        } else {
            return null;
        }
    }


    public static JSONArray ProLogList2Json(List<EmployeeExample> list) {
        JSONArray json = new JSONArray();
        String sex = null;
        for (EmployeeExample pLog : list) {
            JSONObject jo = new JSONObject();
            jo.put("sequence", Integer.parseInt(pLog.getEmployeeno()));
            jo.put("employeeID", pLog.getEmployeeno());
            jo.put("mobilePhone", pLog.getPhonenumber());
            jo.put("name", decodeUnicode(pLog.getName()));
            if (pLog.getGender().equals("男")) {
                sex = "M";
            }
            if (pLog.getGender().equals("女")) {
                sex = "F";
            } else {
                sex = "M";
            }
            jo.put("gender", sex);
            jo.put("email", pLog.getEmail());
            jo.put("valid", "A");
            json.put(jo);
        }
        return json;
    }


    public static JSONObject ProLogObject(List<EmployeeExample> list) {
        JSONObject jo = null;
        for (EmployeeExample pLog : list) {
            jo = new JSONObject();
            jo.put("employeeNo", Integer.parseInt(pLog.getEmployeeno()));
            jo.put("phoneNumber", pLog.getPhonenumber());
            jo.put("name", decodeUnicode(pLog.getName()));
            jo.put("deptName", pLog.getDeptName());
        }
        return jo;
    }

    public static JSONObject XCProLogObject(List<EmployeeExample> list) {
        JSONObject jo = new JSONObject();
        for (EmployeeExample pLog : list) {
            jo.put("sequence", Integer.parseInt(pLog.getEmployeeno()));
            jo.put("employeeNo", pLog.getEmployeeno());
            jo.put("mobilePhone", pLog.getPhonenumber());
            jo.put("name", decodeUnicode(pLog.getName()));
            jo.put("gender", "M");
            jo.put("email", pLog.getEmail());
        }
        return jo;
    }


    public static com.alibaba.fastjson.JSONArray test(List<EmployeeExample> list) {
        com.alibaba.fastjson.JSONArray jsonArray = new com.alibaba.fastjson.JSONArray();
        for (EmployeeExample pLog : list) {
            com.alibaba.fastjson.JSONObject jo = new com.alibaba.fastjson.JSONObject();
            jo.put("employeeNo", pLog.getEmployeeno());
            jo.put("phoneNumber", pLog.getPhonenumber());
            jo.put("name", pLog.getName());
            jo.put("deptName", pLog.getDeptName());
            jsonArray.add(jo);
        }
        return jsonArray;
    }


    /**
     * 解码 Unicode \\uXXXX
     *
     * @param str
     * @return
     */
    public static String decodeUnicode(String str) {
        Charset set = Charset.forName("UTF-8");
        Pattern p = Pattern.compile("\\\\u([0-9a-fA-F]{4})");
        Matcher m = p.matcher(str);
        int start = 0;
        int start2 = 0;
        StringBuffer sb = new StringBuffer();
        while (m.find(start)) {
            start2 = m.start();
            if (start2 > start) {
                String seg = str.substring(start, start2);
                sb.append(seg);
            }
            String code = m.group(1);
            int i = Integer.valueOf(code, 16);
            byte[] bb = new byte[4];
            bb[0] = (byte) ((i >> 8) & 0xFF);
            bb[1] = (byte) (i & 0xFF);
            ByteBuffer b = ByteBuffer.wrap(bb);
            sb.append(String.valueOf(set.decode(b)).trim());
            start = m.end();
        }
        start2 = str.length();
        if (start2 > start) {
            String seg = str.substring(start, start2);
            sb.append(seg);
        }
        return sb.toString();
    }


    public static void main(String[] args) throws IOException {
        EmployeeExample example = new EmployeeExample();
        example.setEmployeeno("180925");
        example.setName("张成辉");
        example.setDeptName("EB7");
        example.setPhonenumber("17638564328");
        List<EmployeeExample> examples = new ArrayList<>();
        examples.add(example);
        String result = NumberArithmeticUtils.sendPost("http://test.third-party.newtouch.com/elemp/ntpmp-api/add-employee", null, "utf-8", "application/json", NumberArithmeticUtils.ProLogObject(examples).toString());
        System.out.println(result);


    }

}
