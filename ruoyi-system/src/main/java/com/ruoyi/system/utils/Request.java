package com.ruoyi.system.utils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
// XML文件中的根标识
@XmlRootElement(name = "userinfo")
// 控制JAXB 绑定类中属性和字段的排序
@XmlType(propOrder = {
        "err_msg",
        "retcode",
        "userId",
        "totalBalance",
        "oufeiBalance",
        "feihanBalance"
})
public class Request implements Serializable {

    public Request(){
        super();
    }

    /*错误描述，如请求得到正确返回，此处将为空*/
    private String err_msg = new String();

    /*操作返回代码，1成功,err_msg为空，其它数字具体错误在err_msg返回*/
    private String retcode = new String();


    private String userId = new String();

    /*账户余额*/
    private String totalBalance = new String();

    /*欧飞账户资金余额*/
    private String oufeiBalance = new String();

    /*飞翰资金账户余额*/
    private String feihanBalance = new String();

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getOufeiBalance() {
        return oufeiBalance;
    }

    public void setOufeiBalance(String oufeiBalance) {
        this.oufeiBalance = oufeiBalance;
    }

    public String getFeihanBalance() {
        return feihanBalance;
    }

    public void setFeihanBalance(String feihanBalance) {
        this.feihanBalance = feihanBalance;
    }
}
