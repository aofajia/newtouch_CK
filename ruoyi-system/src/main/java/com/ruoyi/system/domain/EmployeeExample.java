package com.ruoyi.system.domain;

/**
 *  员工扩展类
 */
public class EmployeeExample extends Employee {

     //对接携程的唯一标识
     Integer sequence;

     //部门名称
     String deptName;

     //公司名称
     String companyName;

     //状态名称
     String statusName;

    public  Integer getSequence() {
        return sequence;
    }public void    setSequence(Integer sequence) {
        this.sequence = sequence;
    }public String  getDeptName() {
        return deptName;
    }public void    setDeptName(String deptName) {
        this.deptName = deptName;
    }public String  getCompanyName() {
        return companyName;
    }public void    setCompanyName(String companyName) {
        this.companyName = companyName;
    }public String  getStatusName() {
        return statusName;
    }public void    setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
