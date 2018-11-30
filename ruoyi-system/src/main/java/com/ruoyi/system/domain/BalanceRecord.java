package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;

public class BalanceRecord {
    private String id;

    private String supplierid;

    private String suppliername;

    private BigDecimal balancemoney;

    private String date;

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

    public BigDecimal getBalancemoney() {
        return balancemoney;
    }

    public void setBalancemoney(BigDecimal balancemoney) {
        this.balancemoney = balancemoney;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    public Date getCommitdate() {
        return commitdate;
    }

    public void setCommitdate(Date commitdate) {
        this.commitdate = commitdate;
    }
}