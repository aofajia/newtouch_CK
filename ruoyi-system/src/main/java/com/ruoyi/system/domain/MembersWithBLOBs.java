package com.ruoyi.system.domain;

public class MembersWithBLOBs extends Members {
    private String addon;

    private String interest;

    private String favTags;

    private String custom;

    private String remark;

    public String getAddon() {
        return addon;
    }

    public void setAddon(String addon) {
        this.addon = addon == null ? null : addon.trim();
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest == null ? null : interest.trim();
    }

    public String getFavTags() {
        return favTags;
    }

    public void setFavTags(String favTags) {
        this.favTags = favTags == null ? null : favTags.trim();
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom == null ? null : custom.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}