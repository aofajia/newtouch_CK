package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;

public class CheckingStore {
    private String detailid;

    private String mianid;

    private String checkingdate;

    private Integer checkingstatus;

    private String supplierid;

    private String suppliername;

    private String paymethod;

    private BigDecimal validmoney;

    private BigDecimal storebalance;

    private BigDecimal rechargemoney;

    private BigDecimal storeordersmoney;

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

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    public String getSuppliername() {
        return suppliername;
    }

    public void setSuppliername(String suppliername) {
        this.suppliername = suppliername == null ? null : suppliername.trim();
    }

    public String getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod == null ? null : paymethod.trim();
    }

    public BigDecimal getValidmoney() {
        return validmoney;
    }

    public void setValidmoney(BigDecimal validmoney) {
        this.validmoney = validmoney;
    }

    public BigDecimal getStorebalance() {
        return storebalance;
    }

    public void setStorebalance(BigDecimal storebalance) {
        this.storebalance = storebalance;
    }

    public BigDecimal getRechargemoney() {
        return rechargemoney;
    }

    public void setRechargemoney(BigDecimal rechargemoney) {
        this.rechargemoney = rechargemoney;
    }

    public BigDecimal getStoreordersmoney() {
        return storeordersmoney;
    }

    public void setStoreordersmoney(BigDecimal storeordersmoney) {
        this.storeordersmoney = storeordersmoney;
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