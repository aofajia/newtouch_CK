package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;

public class OrderCheckStatus {
    private Long orderId;

    private Integer orderLegalman;

    private Integer invoiceLegalman;

    private BigDecimal finalAmount;

    private Long storeId;

    private Integer createtime;

    private BigDecimal payed;

    private String status;

    private Integer checkingstatus;

    private Date createdTime;

    private Date updateTime;

    private Integer cancelFlag;

    private String type;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderLegalman() {
        return orderLegalman;
    }

    public void setOrderLegalman(Integer orderLegalman) {
        this.orderLegalman = orderLegalman;
    }

    public Integer getInvoiceLegalman() {
        return invoiceLegalman;
    }

    public void setInvoiceLegalman(Integer invoiceLegalman) {
        this.invoiceLegalman = invoiceLegalman;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Integer getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Integer createtime) {
        this.createtime = createtime;
    }

    public BigDecimal getPayed() {
        return payed;
    }

    public void setPayed(BigDecimal payed) {
        this.payed = payed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getCheckingstatus() {
        return checkingstatus;
    }

    public void setCheckingstatus(Integer checkingstatus) {
        this.checkingstatus = checkingstatus;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}