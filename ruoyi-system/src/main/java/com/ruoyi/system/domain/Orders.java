package com.ruoyi.system.domain;

import java.math.BigDecimal;

public class Orders {
    private Long orderId;

    private BigDecimal totalAmount;

    private BigDecimal discountValue;

    private BigDecimal finalAmount;

    private String payStatus;

    private String shipStatus;

    private String isDelivery;

    private Integer createtime;

    private Integer lastModified;

    private String payment;

    private Integer shippingId;

    private String shipping;

    private Integer memberId;

    private String status;

    private String confirm;

    private String shipArea;

    private String shipName;

    private BigDecimal weight;

    private BigDecimal itemnum;

    private String ip;

    private String shipZip;

    private String shipTel;

    private String shipEmail;

    private String shipTime;

    private String shipMobile;

    private BigDecimal costItem;

    private String isTax;

    private BigDecimal costTax;

    private String taxCompany;

    private String isProtect;

    private BigDecimal costProtect;

    private BigDecimal costPayment;

    private String currency;

    private BigDecimal curRate;

    private BigDecimal scoreU;

    private BigDecimal scoreG;

    private BigDecimal discount;

    private BigDecimal pmtGoods;

    private BigDecimal pmtOrder;

    private BigDecimal payed;

    private String disabled;

    private String markType;

    private BigDecimal costFreight;

    private String extend;

    private String orderRefer;

    private String source;

    private Integer confirmTime;

    private Long storeId;

    private Integer commentsCount;

    private String refundStatus;

    private Integer actId;

    private String orderType;

    private String isExtend;

    private String orderKind;

    private Long jdorderno;

    private Long jdporderno;

    private String invoiceStatus;

    private String jdorderstate;

    private Integer orderLegalman;

    private Integer invoiceLegalman;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus == null ? null : payStatus.trim();
    }

    public String getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(String shipStatus) {
        this.shipStatus = shipStatus == null ? null : shipStatus.trim();
    }

    public String getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(String isDelivery) {
        this.isDelivery = isDelivery == null ? null : isDelivery.trim();
    }

    public Integer getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Integer createtime) {
        this.createtime = createtime;
    }

    public Integer getLastModified() {
        return lastModified;
    }

    public void setLastModified(Integer lastModified) {
        this.lastModified = lastModified;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment == null ? null : payment.trim();
    }

    public Integer getShippingId() {
        return shippingId;
    }

    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping == null ? null : shipping.trim();
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm == null ? null : confirm.trim();
    }

    public String getShipArea() {
        return shipArea;
    }

    public void setShipArea(String shipArea) {
        this.shipArea = shipArea == null ? null : shipArea.trim();
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName == null ? null : shipName.trim();
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getItemnum() {
        return itemnum;
    }

    public void setItemnum(BigDecimal itemnum) {
        this.itemnum = itemnum;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getShipZip() {
        return shipZip;
    }

    public void setShipZip(String shipZip) {
        this.shipZip = shipZip == null ? null : shipZip.trim();
    }

    public String getShipTel() {
        return shipTel;
    }

    public void setShipTel(String shipTel) {
        this.shipTel = shipTel == null ? null : shipTel.trim();
    }

    public String getShipEmail() {
        return shipEmail;
    }

    public void setShipEmail(String shipEmail) {
        this.shipEmail = shipEmail == null ? null : shipEmail.trim();
    }

    public String getShipTime() {
        return shipTime;
    }

    public void setShipTime(String shipTime) {
        this.shipTime = shipTime == null ? null : shipTime.trim();
    }

    public String getShipMobile() {
        return shipMobile;
    }

    public void setShipMobile(String shipMobile) {
        this.shipMobile = shipMobile == null ? null : shipMobile.trim();
    }

    public BigDecimal getCostItem() {
        return costItem;
    }

    public void setCostItem(BigDecimal costItem) {
        this.costItem = costItem;
    }

    public String getIsTax() {
        return isTax;
    }

    public void setIsTax(String isTax) {
        this.isTax = isTax == null ? null : isTax.trim();
    }

    public BigDecimal getCostTax() {
        return costTax;
    }

    public void setCostTax(BigDecimal costTax) {
        this.costTax = costTax;
    }

    public String getTaxCompany() {
        return taxCompany;
    }

    public void setTaxCompany(String taxCompany) {
        this.taxCompany = taxCompany == null ? null : taxCompany.trim();
    }

    public String getIsProtect() {
        return isProtect;
    }

    public void setIsProtect(String isProtect) {
        this.isProtect = isProtect == null ? null : isProtect.trim();
    }

    public BigDecimal getCostProtect() {
        return costProtect;
    }

    public void setCostProtect(BigDecimal costProtect) {
        this.costProtect = costProtect;
    }

    public BigDecimal getCostPayment() {
        return costPayment;
    }

    public void setCostPayment(BigDecimal costPayment) {
        this.costPayment = costPayment;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public BigDecimal getCurRate() {
        return curRate;
    }

    public void setCurRate(BigDecimal curRate) {
        this.curRate = curRate;
    }

    public BigDecimal getScoreU() {
        return scoreU;
    }

    public void setScoreU(BigDecimal scoreU) {
        this.scoreU = scoreU;
    }

    public BigDecimal getScoreG() {
        return scoreG;
    }

    public void setScoreG(BigDecimal scoreG) {
        this.scoreG = scoreG;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getPmtGoods() {
        return pmtGoods;
    }

    public void setPmtGoods(BigDecimal pmtGoods) {
        this.pmtGoods = pmtGoods;
    }

    public BigDecimal getPmtOrder() {
        return pmtOrder;
    }

    public void setPmtOrder(BigDecimal pmtOrder) {
        this.pmtOrder = pmtOrder;
    }

    public BigDecimal getPayed() {
        return payed;
    }

    public void setPayed(BigDecimal payed) {
        this.payed = payed;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled == null ? null : disabled.trim();
    }

    public String getMarkType() {
        return markType;
    }

    public void setMarkType(String markType) {
        this.markType = markType == null ? null : markType.trim();
    }

    public BigDecimal getCostFreight() {
        return costFreight;
    }

    public void setCostFreight(BigDecimal costFreight) {
        this.costFreight = costFreight;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend == null ? null : extend.trim();
    }

    public String getOrderRefer() {
        return orderRefer;
    }

    public void setOrderRefer(String orderRefer) {
        this.orderRefer = orderRefer == null ? null : orderRefer.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Integer getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Integer confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus == null ? null : refundStatus.trim();
    }

    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public String getIsExtend() {
        return isExtend;
    }

    public void setIsExtend(String isExtend) {
        this.isExtend = isExtend == null ? null : isExtend.trim();
    }

    public String getOrderKind() {
        return orderKind;
    }

    public void setOrderKind(String orderKind) {
        this.orderKind = orderKind == null ? null : orderKind.trim();
    }

    public Long getJdorderno() {
        return jdorderno;
    }

    public void setJdorderno(Long jdorderno) {
        this.jdorderno = jdorderno;
    }

    public Long getJdporderno() {
        return jdporderno;
    }

    public void setJdporderno(Long jdporderno) {
        this.jdporderno = jdporderno;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus == null ? null : invoiceStatus.trim();
    }

    public String getJdorderstate() {
        return jdorderstate;
    }

    public void setJdorderstate(String jdorderstate) {
        this.jdorderstate = jdorderstate == null ? null : jdorderstate.trim();
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
}