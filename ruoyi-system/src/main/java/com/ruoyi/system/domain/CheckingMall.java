package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;

public class CheckingMall {
    private String detailid;

    private String mianid;

    private String checkingdate;

    private Integer checkingstatus;

    private BigDecimal paymoney;

    private BigDecimal residuemoney;

    private BigDecimal leavemoney;

    private BigDecimal welfaremoney;

    private Date createdTime;

    private Date updateTime;

    private Integer cancelFlag;

    public String getDetailid() {
        return detailid;
    }

    public void setDetailid(String detailid) {
        this.detailid = detailid == null ? null : detailid.trim();
    }

    public String getMianid() {
        return mianid;
    }

    public void setMianid(String mianid) {
        this.mianid = mianid == null ? null : mianid.trim();
    }

    public String getCheckingdate() {
        return checkingdate;
    }

    public void setCheckingdate(String checkingdate) {
        this.checkingdate = checkingdate == null ? null : checkingdate.trim();
    }

    public Integer getCheckingstatus() {
        return checkingstatus;
    }

    public void setCheckingstatus(Integer checkingstatus) {
        this.checkingstatus = checkingstatus;
    }

    public BigDecimal getPaymoney() {
        return paymoney;
    }

    public void setPaymoney(BigDecimal paymoney) {
        this.paymoney = paymoney;
    }

    public BigDecimal getResiduemoney() {
        return residuemoney;
    }

    public void setResiduemoney(BigDecimal residuemoney) {
        this.residuemoney = residuemoney;
    }

    public BigDecimal getLeavemoney() {
        return leavemoney;
    }

    public void setLeavemoney(BigDecimal leavemoney) {
        this.leavemoney = leavemoney;
    }

    public BigDecimal getWelfaremoney() {
        return welfaremoney;
    }

    public void setWelfaremoney(BigDecimal welfaremoney) {
        this.welfaremoney = welfaremoney;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCancelFlag() {
        return cancelFlag;
    }

    public void setCancelFlag(Integer cancelFlag) {
        this.cancelFlag = cancelFlag;
    }
}