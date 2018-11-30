package com.ruoyi.system.domain;

import java.util.Date;

/**
 *  供应商月度总消费金额信息
 */
public class StoreConfig
{

    private String store_id;

    private String shop_name;

    private String monthlymoney;

    private String paymethod;

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) { this.store_id = store_id; }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getMonthlymoney() {
        return monthlymoney;
    }

    public void setMonthlymoney(String monthlymoney) {
        this.monthlymoney = monthlymoney;
    }

    public String getPaymethod() { return paymethod; }

    public void setPaymethod(String paymethod) { this.paymethod = paymethod; }
}