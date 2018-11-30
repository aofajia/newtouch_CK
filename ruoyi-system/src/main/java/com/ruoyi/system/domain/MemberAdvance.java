package com.ruoyi.system.domain;

import java.math.BigDecimal;

public class MemberAdvance {
    private Integer logId;

    private Integer memberId;

    private BigDecimal money;

    private String message;

    private Integer mtime;

    private String paymentId;

    private Long orderId;

    private Long porderno;

    private Long jdporderno;

    private String memo;

    private BigDecimal importMoney;

    private BigDecimal explodeMoney;

    private BigDecimal memberAdvance;

    private BigDecimal shopAdvance;

    private String disabled;

    private BigDecimal unionFee;

    private BigDecimal subsidy;

    private BigDecimal welfare;

    private Integer targetId;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Integer getMtime() {
        return mtime;
    }

    public void setMtime(Integer mtime) {
        this.mtime = mtime;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId == null ? null : paymentId.trim();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getPorderno() {
        return porderno;
    }

    public void setPorderno(Long porderno) {
        this.porderno = porderno;
    }

    public Long getJdporderno() {
        return jdporderno;
    }

    public void setJdporderno(Long jdporderno) {
        this.jdporderno = jdporderno;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public BigDecimal getImportMoney() {
        return importMoney;
    }

    public void setImportMoney(BigDecimal importMoney) {
        this.importMoney = importMoney;
    }

    public BigDecimal getExplodeMoney() {
        return explodeMoney;
    }

    public void setExplodeMoney(BigDecimal explodeMoney) {
        this.explodeMoney = explodeMoney;
    }

    public BigDecimal getMemberAdvance() {
        return memberAdvance;
    }

    public void setMemberAdvance(BigDecimal memberAdvance) {
        this.memberAdvance = memberAdvance;
    }

    public BigDecimal getShopAdvance() {
        return shopAdvance;
    }

    public void setShopAdvance(BigDecimal shopAdvance) {
        this.shopAdvance = shopAdvance;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled == null ? null : disabled.trim();
    }

    public BigDecimal getUnionFee() {
        return unionFee;
    }

    public void setUnionFee(BigDecimal unionFee) {
        this.unionFee = unionFee;
    }

    public BigDecimal getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(BigDecimal subsidy) {
        this.subsidy = subsidy;
    }

    public BigDecimal getWelfare() {
        return welfare;
    }

    public void setWelfare(BigDecimal welfare) {
        this.welfare = welfare;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }
}