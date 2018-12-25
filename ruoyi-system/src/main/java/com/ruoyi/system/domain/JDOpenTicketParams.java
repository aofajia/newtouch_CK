package com.ruoyi.system.domain;

/**
 *  京东开票实体
 */
public class JDOpenTicketParams {

    //NTPMP分发给商城的ID
    String appId;
    //结算单号（唯一，一个结算单号可对对应多个第三方申请单号）
    String settlementId;
    //发票类型   1普通 2增值税 3 电子票
    String invoiceType;
    //开票机构ID（联系京东业务确定）
    String invoiceOrg;
    //期望开票时间  YYYY-MM-DD
    String invoiceDate;
    //收票单位 （填写开票省份）
    String billToParty;
    //纳税人识别号（增票必须）
    String enterpriseTaxpayer;
    //收票人（邮寄必填）
    String billToer;
    //收票人联系方式 （邮寄必填）
    String billToContact;
    //收票人地址（省）  （邮寄必填）
    String billToProvince;
    //收票人地址（市） （邮寄必填）
    String billToCity;
    //收票人地址（区）  （邮寄必填）
    String billToCounty;
    //收票人地址（街道）（有四级地址则必传，否则传0）（邮寄必填）
    String billToTown;
    //收票人地址（详细地址）（邮寄必填）
    String billToAddress;
    //总金额 ( “100.00” )
    String orderAmountCash;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(String settlementId) {
        this.settlementId = settlementId;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceOrg() {
        return invoiceOrg;
    }

    public void setInvoiceOrg(String invoiceOrg) {
        this.invoiceOrg = invoiceOrg;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getBillToParty() {
        return billToParty;
    }

    public void setBillToParty(String billToParty) {
        this.billToParty = billToParty;
    }

    public String getEnterpriseTaxpayer() {
        return enterpriseTaxpayer;
    }

    public void setEnterpriseTaxpayer(String enterpriseTaxpayer) {
        this.enterpriseTaxpayer = enterpriseTaxpayer;
    }

    public String getBillToer() {
        return billToer;
    }

    public void setBillToer(String billToer) {
        this.billToer = billToer;
    }

    public String getBillToContact() {
        return billToContact;
    }

    public void setBillToContact(String billToContact) {
        this.billToContact = billToContact;
    }

    public String getBillToProvince() {
        return billToProvince;
    }

    public void setBillToProvince(String billToProvince) {
        this.billToProvince = billToProvince;
    }

    public String getBillToCity() {
        return billToCity;
    }

    public void setBillToCity(String billToCity) {
        this.billToCity = billToCity;
    }

    public String getBillToCounty() {
        return billToCounty;
    }

    public void setBillToCounty(String billToCounty) {
        this.billToCounty = billToCounty;
    }

    public String getBillToTown() {
        return billToTown;
    }

    public void setBillToTown(String billToTown) {
        this.billToTown = billToTown;
    }

    public String getBillToAddress() {
        return billToAddress;
    }

    public void setBillToAddress(String billToAddress) {
        this.billToAddress = billToAddress;
    }

    public String getOrderAmountCash() {
        return orderAmountCash;
    }

    public void setOrderAmountCash(String orderAmountCash) {
        this.orderAmountCash = orderAmountCash;
    }



}
