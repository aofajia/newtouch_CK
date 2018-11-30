package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;

public class StoreOrders {
    private String id;

    private String supplierid;

    private String suppliername;

    private String orderid;

    private BigDecimal ordermoney;

    private Date date;

    private Date commitdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public BigDecimal getOrdermoney() {
        return ordermoney;
    }

    public void setOrdermoney(BigDecimal ordermoney) {
        this.ordermoney = ordermoney;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getCommitdate() {
        return commitdate;
    }

    public void setCommitdate(Date commitdate) {
        this.commitdate = commitdate;
    }
}