package com.ruoyi.system.domain;

import java.util.Date;

public class CheckingLog {
    private String id;

    private String checkingdate;

    private Integer checkingstatus;

    private String supplierid;

    private String suppliername;

    private String orderId;

    private String payed;

    private String otherOrderId;

    private String otherPayed;

    private Date createtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getPayed() {
        return payed;
    }

    public void setPayed(String payed) {
        this.payed = payed == null ? null : payed.trim();
    }

    public String getOtherOrderId() {
        return otherOrderId;
    }

    public void setOtherOrderId(String otherOrderId) {
        this.otherOrderId = otherOrderId == null ? null : otherOrderId.trim();
    }

    public String getOtherPayed() {
        return otherPayed;
    }

    public void setOtherPayed(String otherPayed) {
        this.otherPayed = otherPayed == null ? null : otherPayed.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}