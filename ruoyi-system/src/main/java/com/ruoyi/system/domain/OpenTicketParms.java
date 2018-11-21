package com.ruoyi.system.domain;

import java.io.Serializable;

public class OpenTicketParms implements Serializable {

    //开始日期
    String startDate;

    //结束日期
    String endDate;

    //公司Id
    String companyId;

    //供应商
    String supplier;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}
