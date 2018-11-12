package com.ruoyi.system.domain;

public class BWConfigtype {
    private String configtype;

    private String typename;

    public String getConfigtype() {
        return configtype;
    }

    public void setConfigtype(String configtype) {
        this.configtype = configtype == null ? null : configtype.trim();
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename == null ? null : typename.trim();
    }
}