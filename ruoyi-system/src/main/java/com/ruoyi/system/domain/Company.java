package com.ruoyi.system.domain;

public class Company {
    private Long companyId;

    private String accountCat;

    private String linkman;

    private String tel;

    private String email;

    private String companyTaxno;

    private String companyCtel;

    private String companyAddr;

    private String bankName;

    private Integer bankCardid;

    private String bankCardName;

    private String invoiceName;

    private String invoiceTel;

    private String invoiceAddr;

    private String invoiceCompanyName;

    private String invoicetype;

    private String invoicestate;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getAccountCat() {
        return accountCat;
    }

    public void setAccountCat(String accountCat) {
        this.accountCat = accountCat == null ? null : accountCat.trim();
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman == null ? null : linkman.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getCompanyTaxno() {
        return companyTaxno;
    }

    public void setCompanyTaxno(String companyTaxno) {
        this.companyTaxno = companyTaxno == null ? null : companyTaxno.trim();
    }

    public String getCompanyCtel() {
        return companyCtel;
    }

    public void setCompanyCtel(String companyCtel) {
        this.companyCtel = companyCtel == null ? null : companyCtel.trim();
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr == null ? null : companyAddr.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public Integer getBankCardid() {
        return bankCardid;
    }

    public void setBankCardid(Integer bankCardid) {
        this.bankCardid = bankCardid;
    }

    public String getBankCardName() {
        return bankCardName;
    }

    public void setBankCardName(String bankCardName) {
        this.bankCardName = bankCardName == null ? null : bankCardName.trim();
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName == null ? null : invoiceName.trim();
    }

    public String getInvoiceTel() {
        return invoiceTel;
    }

    public void setInvoiceTel(String invoiceTel) {
        this.invoiceTel = invoiceTel == null ? null : invoiceTel.trim();
    }

    public String getInvoiceAddr() {
        return invoiceAddr;
    }

    public void setInvoiceAddr(String invoiceAddr) {
        this.invoiceAddr = invoiceAddr == null ? null : invoiceAddr.trim();
    }

    public String getInvoiceCompanyName() {
        return invoiceCompanyName;
    }

    public void setInvoiceCompanyName(String invoiceCompanyName) {
        this.invoiceCompanyName = invoiceCompanyName == null ? null : invoiceCompanyName.trim();
    }

    public String getInvoicetype() {
        return invoicetype;
    }

    public void setInvoicetype(String invoicetype) {
        this.invoicetype = invoicetype == null ? null : invoicetype.trim();
    }

    public String getInvoicestate() {
        return invoicestate;
    }

    public void setInvoicestate(String invoicestate) {
        this.invoicestate = invoicestate == null ? null : invoicestate.trim();
    }
}