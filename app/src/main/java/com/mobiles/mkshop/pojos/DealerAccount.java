package com.mobiles.mkshop.pojos;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by vaibhav on 16/11/15.
 */
public class DealerAccount extends SugarRecord<DealerAccount> {

    String dealerName, dealerId;
    List<ProductExpense> productExpenses;

    public DealerAccount() {

    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public List<ProductExpense> getProductExpenses() {
        return productExpenses;
    }

    public void setProductExpenses(List<ProductExpense> productExpenses) {
        this.productExpenses = productExpenses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DealerAccount that = (DealerAccount) o;

        return getDealerId().equals(that.getDealerId());

    }

    @Override
    public int hashCode() {
        return getDealerId().hashCode();
    }
}
