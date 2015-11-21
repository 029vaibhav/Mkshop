package com.mobiles.mkshop.pojos.models;

/**
 * Created by vaibhav on 20/11/15.
 */
public class DealerPaymentInfo {

    int totalAmount, paidAmount;

    public DealerPaymentInfo(int totalAmount, int paidAmount) {
        this.totalAmount = totalAmount;
        this.paidAmount = paidAmount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(int paidAmount) {
        this.paidAmount = paidAmount;
    }
}
