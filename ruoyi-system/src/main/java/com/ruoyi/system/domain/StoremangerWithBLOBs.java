package com.ruoyi.system.domain;

public class StoremangerWithBLOBs extends Storemanger {
    private String companyRemark;

    private String companyUrl;

    private String remark;

    private String approvedremark;

    public String getCompanyRemark() {
        return companyRemark;
    }

    public void setCompanyRemark(String companyRemark) {
        this.companyRemark = companyRemark == null ? null : companyRemark.trim();
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl == null ? null : companyUrl.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getApprovedremark() {
        return approvedremark;
    }

    public void setApprovedremark(String approvedremark) {
        this.approvedremark = approvedremark == null ? null : approvedremark.trim();
    }
}