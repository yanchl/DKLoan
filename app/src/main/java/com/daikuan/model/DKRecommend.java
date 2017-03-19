package com.daikuan.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by jason on 17/3/19.
 */

public class DKRecommend extends BmobObject {
    private Integer order;
    private LoanItem loan;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }


    public LoanItem getLoan() {
        return loan;
    }

    public void setLoan(LoanItem loan) {
        this.loan = loan;
    }
}
