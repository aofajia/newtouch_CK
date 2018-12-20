package com.ruoyi.system.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;

public class WithDrawals {
    private Integer withdrawNo;

    private Integer staffCard;

    private String staffName;

    private Integer memberId;

    private BigDecimal money;

    private String withdrawType;

    private String begin;

    private String auditing;

    private Long opAuditing;

    private Integer tAuditing;

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public Integer getWithdrawNo() {
        return withdrawNo;
    }

    public void setWithdrawNo(Integer withdrawNo) {
        this.withdrawNo = withdrawNo;
    }

    public Integer getStaffCard() {
        return staffCard;
    }

    public void setStaffCard(Integer staffCard) {
        this.staffCard = staffCard;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName == null ? null : staffName.trim();
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

    public String getWithdrawType() {
        return withdrawType;
    }

    public void setWithdrawType(String withdrawType) {
        this.withdrawType = withdrawType == null ? null : withdrawType.trim();
    }

    public String getAuditing() {
        return auditing;
    }

    public void setAuditing(String auditing) {
        this.auditing = auditing == null ? null : auditing.trim();
    }

    public Long getOpAuditing() {
        return opAuditing;
    }

    public void setOpAuditing(Long opAuditing) {
        this.opAuditing = opAuditing;
    }

    public Integer gettAuditing() {
        return tAuditing;
    }

    public void settAuditing(Integer tAuditing) {
        this.tAuditing = tAuditing;
    }
}