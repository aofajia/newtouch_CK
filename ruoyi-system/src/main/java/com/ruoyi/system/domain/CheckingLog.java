package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;

import java.util.Date;

public class CheckingLog {
    private static final long serialVersionUID = 1L;

    private String id;

    private String mainid;

    private String checkingdate;

    private Integer checkingstatus;

    private String supplierid;

    private String suppliername;

    @Excel(name = "供应商")
    private String storename;

    private String orderId;

    @Excel(name = "商城订单号")
    private String order_id;

    @Excel(name = "商城订单金额")
    private String payed;

    private String otherOrderId;

    @Excel(name = "第三方订单号")
    private String other_order_id;

    private String otherPayed;

    @Excel(name = "第三方订单金额")
    private String other_payed;

    private Date createtime;

    private Date createdTime;

    private Date updateTime;

    private Integer cancelFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMainid() {
        return mainid;
    }

    public void setMainid(String mainid) {
        this.mainid = mainid == null ? null : mainid.trim();
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

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOther_order_id() {
        return other_order_id;
    }

    public void setOther_order_id(String other_order_id) {
        this.other_order_id = other_order_id;
    }

    public String getOther_payed() {
        return other_payed;
    }

    public void setOther_payed(String other_payed) {
        this.other_payed = other_payed;
    }
}