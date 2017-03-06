package com.daikuan.model;

import android.support.annotation.DrawableRes;

import com.daikuan.view.Banner;

/**
 * Author:  WQ
 * Version: v0.0.1
 * Date:    2017/3/5
 * Modification History:
 * Why & What modified:
 */

public class LoanerItem {

    private String name;

    @DrawableRes
    private int resIconId;

    private String content1, content2, content3, content4, content5;

    private String tag;

    private String url;

    private String umengEventID;

    public String getUmengEventID() {
        return umengEventID;
    }

    public LoanerItem setUmengEventID(String umengEventID) {
        this.umengEventID = umengEventID;
        return this;
    }

    public LoanerItem(){
        content3 = "参考日利率 :";
    }
    public String getName() {
        return name;
    }

    public LoanerItem setName(String name) {
        this.name = name;
        return this;
    }

    public int getResIconId() {
        return resIconId;
    }

    public LoanerItem setResIconId(int resIconId) {
        this.resIconId = resIconId;
        return this;
    }

    public String getContent1() {
        return content1;
    }

    public LoanerItem setContent1(String content1) {
        this.content1 = content1;
        return this;
    }

    public String getContent2() {
        return content2;
    }

    public LoanerItem setContent2(String content2) {
        this.content2 = content2;
        return this;
    }

    public String getContent3() {
        return content3;
    }

    public LoanerItem setContent3(String content3) {
        this.content3 = content3;
        return this;
    }

    public String getContent4() {
        return content4;
    }

    public LoanerItem setContent4(String content4) {
        this.content4 = content4;
        return this;
    }

    public String getContent5() {
        return content5;
    }

    public LoanerItem setContent5(String content5) {
        this.content5 = content5;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public boolean hasTag(){
        return tag != null;
    }

    public LoanerItem setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public LoanerItem setUrl(String url) {
        this.url = url;
        return this;
    }
}
