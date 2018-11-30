package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;

public class User {
    private String id;

    private String username;

    private String age;

    private String birthday ;

    private String deptname;

    private String hodd;

    public User(String id, String username, String age, String birthday, String deptname, String hodd) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.birthday = birthday;
        this.deptname = deptname;
        this.hodd = hodd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getHodd() {
        return hodd;
    }

    public void setHodd(String hodd) {
        this.hodd = hodd;
    }
}