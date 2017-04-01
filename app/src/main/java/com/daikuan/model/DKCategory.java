package com.daikuan.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by jason on 17/3/24.
 */

public class DKCategory extends BmobObject {
    private Integer order;
    private LoanItem loanItem;
    private Integer categoryId;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public LoanItem getLoanItem() {
        return loanItem;
    }

    public void setLoanItem(LoanItem loan) {
        this.loanItem = loanItem;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
