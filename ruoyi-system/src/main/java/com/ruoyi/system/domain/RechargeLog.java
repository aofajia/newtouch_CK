package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;

public class RechargeLog {
    private String id;

    private String supplierid;

    private String suppliername;

    private String companyid;

    private String companyname;

    private BigDecimal rechargemoney;

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

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid == null ? null : companyid.trim();
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname == null ? null : companyname.trim();
    }

    public BigDecimal getRechargemoney() {
        return rechargemoney;
    }

    public void setRechargemoney(BigDecimal rechargemoney) {
        this.rechargemoney = rechargemoney;
    }

    public Date getCommitdate() {
        return commitdate;
    }

    public void setCommitdate(Date commitdate) {
        this.commitdate = commitdate;
    }
}