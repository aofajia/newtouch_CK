package com.ruoyi.system.domain;

import java.util.Date;

/**
 *  供应商月度总消费金额信息
 */
public class StoreConfig
{

    private String storeid;

    private String shopname;

    private String monthlymoney;

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getMonthlymoney() {
        return monthlymoney;
    }

    public void setMonthlymoney(String monthlymoney) {
        this.monthlymoney = monthlymoney;
    }
}