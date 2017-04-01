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
    private String content1;
    private String content2;
    private String content3;
    private String content4;

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

    public String getContent4() {
        return content4;
    }

    public void setContent4(String content4) {
        this.content4 = content4;
    }

    public String getContent3() {
        return content3;
    }

    public void setContent3(String content3) {
        this.content3 = content3;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }
}
