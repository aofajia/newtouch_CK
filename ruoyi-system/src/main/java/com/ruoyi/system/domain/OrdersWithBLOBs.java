package com.ruoyi.system.domain;

public class OrdersWithBLOBs extends Orders {
    private String tostr;

    private String shipAddr;

    private String memo;

    private String markText;

    private String addon;

    public String getTostr() {
        return tostr;
    }

    public void setTostr(String tostr) {
        this.tostr = tostr == null ? null : tostr.trim();
    }

    public String getShipAddr() {
        return shipAddr;
    }

    public void setShipAddr(String shipAddr) {
        this.shipAddr = shipAddr == null ? null : shipAddr.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getMarkText() {
        return markText;
    }

    public void setMarkText(String markText) {
        this.markText = markText == null ? null : markText.trim();
    }

    public String getAddon() {
        return addon;
    }

    public void setAddon(String addon) {
        this.addon = addon == null ? null : addon.trim();
    }
}