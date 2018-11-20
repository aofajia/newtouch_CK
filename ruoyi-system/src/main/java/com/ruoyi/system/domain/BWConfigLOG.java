package com.ruoyi.system.domain;

import java.math.BigDecimal;

public class BWConfigLOG {
    private String logid;

    private String id;

    private String configtype;

    private String configname;

    private String supplierid;

    private String paymethod;

    private BigDecimal warningmoney;

    private BigDecimal monthlymoney;

    private String storeaccount;

    private String invoicetype;

    private String contacts;

    private String email;

    private String storebalanceurl;

    private String startdate;

    private String enddate;

    private String createtime;

    public String getLogid() {
        return logid;
    }

    public void setLogid(String logid) {
        this.logid = logid == null ? null : logid.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getConfigtype() {
        return configtype;
    }

    public void setConfigtype(String configtype) {
        this.configtype = configtype == null ? null : configtype.trim();
    }

    public String getConfigname() {
        return configname;
    }

    public void setConfigname(String configname) {
        this.configname = configname == null ? null : configname.trim();
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    public String getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod == null ? null : paymethod.trim();
    }

    public BigDecimal getWarningmoney() {
        return warningmoney;
    }

    public void setWarningmoney(BigDecimal warningmoney) {
        this.warningmoney = warningmoney;
    }

    public BigDecimal getMonthlymoney() {
        return monthlymoney;
    }

    public void setMonthlymoney(BigDecimal monthlymoney) {
        this.monthlymoney = monthlymoney;
    }

    public String getStoreaccount() {
        return storeaccount;
    }

    public void setStoreaccount(String storeaccount) {
        this.storeaccount = storeaccount == null ? null : storeaccount.trim();
    }

    public String getInvoicetype() {
        return invoicetype;
    }

    public void setInvoicetype(String invoicetype) {
        this.invoicetype = invoicetype == null ? null : invoicetype.trim();
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts == null ? null : contacts.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getStorebalanceurl() {
        return storebalanceurl;
    }

    public void setStorebalanceurl(String storebalanceurl) {
        this.storebalanceurl = storebalanceurl == null ? null : storebalanceurl.trim();
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate == null ? null : startdate.trim();
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate == null ? null : enddate.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public BWConfigLOG(String id)
    {
        this.id = id;
    }

    public BWConfigLOG(BWConfig bwConfig) {
        this.id = bwConfig.getId();
        this.configtype = bwConfig.getConfigtype();
        this.configname = bwConfig.getConfigname();
        this.supplierid = bwConfig.getSupplierid();
        this.paymethod = bwConfig.getPaymethod();
        this.warningmoney = bwConfig.getWarningmoney();
        this.monthlymoney = bwConfig.getMonthlymoney();
        this.storeaccount = bwConfig.getStoreaccount();
        this.invoicetype = bwConfig.getInvoicetype();
        this.contacts = bwConfig.getContacts();
        this.email = bwConfig.getEmail();
        this.storebalanceurl = bwConfig.getStorebalanceurl();
        this.startdate = bwConfig.getStartdate();
        this.enddate = bwConfig.getEnddate();
    }
}