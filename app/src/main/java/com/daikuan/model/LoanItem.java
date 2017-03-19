package com.daikuan.model;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;

/**
 * Created by jason on 17/3/19.
 */

public class LoanItem extends BmobObject {
    private String tag;
    private Integer order;
    private String name;
    private String iconUrl;
    private String loanUrl;
    private Boolean isMagrinTop;
    private String desc;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getLoanUrl() {
        return loanUrl;
    }

    public void setLoanUrl(String loanUrl) {
        this.loanUrl = loanUrl;
    }

    public Boolean getMagrinTop() {
        return isMagrinTop;
    }

    public void setMagrinTop(Boolean magrinTop) {
        isMagrinTop = magrinTop;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean hasTag(){
        return tag != null;
    }
}
