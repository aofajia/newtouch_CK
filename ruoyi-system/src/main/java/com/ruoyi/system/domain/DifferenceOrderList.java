package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

public class DifferenceOrderList
{
    private static final long serialVersionUID = 1L;

    @Excel(name = "供应商")
    private String storename;

    @Excel(name = "商城订单号")
    private String order_id;

    @Excel(name = "商城订单金额")
    private String payed;

    @Excel(name = "第三方订单号")
    private String other_order_id;

    @Excel(name = "第三方订单金额")
    private String other_payed;

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

    public String getPayed() {
        return payed;
    }

    public void setPayed(String payed) {
        this.payed = payed;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("storename", getStorename())
                .append("order_id", getOrder_id())
                .append("payed", getPayed())
                .append("other_order_id", getOther_order_id())
                .append("other_payed", getOther_payed())
                .toString();
    }
}