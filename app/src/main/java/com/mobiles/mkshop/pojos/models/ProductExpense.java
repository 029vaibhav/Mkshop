package com.mobiles.mkshop.pojos.models;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.List;

/**
 * Created by vaibhav on 2/10/15.
 */
public class ProductExpense extends SugarRecord<ProductExpense> {


    public ProductExpense() {

    }

    private String serverId;
    int dueAmount;
    private String created;
    @Ignore
    private List<PaymentHistory> paymentHistories;
    @Ignore
    private List<PurchaseHistory> purchaseHistory;

    private String image;
    private String dealerName;
    private int totalAmt;
    private String note;


    public int getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(int dueAmount) {
        this.dueAmount = dueAmount;
    }

    public List<PaymentHistory> getPaymentHistories() {
        return paymentHistories;
    }

    public void setPaymentHistories(List<PaymentHistory> paymentHistories) {
        this.paymentHistories = paymentHistories;
    }


    public List<PaymentHistory> getEntries(String id) {

        return find(PaymentHistory.class, "dealer_id = ?", id);
    }

    public void setEntries(List<PaymentHistory> productExpenseSingleEntries) {

        for (int i = 0; i < productExpenseSingleEntries.size(); i++) {

            List<PaymentHistory> expenses = find(PaymentHistory.class, "server_id = ?", productExpenseSingleEntries.get(i).getServerId());
            if (expenses == null || expenses.size() == 0) {
                productExpenseSingleEntries.get(i).save();
            }
        }

        this.paymentHistories = productExpenseSingleEntries;
    }

    public List<PurchaseHistory> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(List<PurchaseHistory> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    public List<PurchaseHistory> getPurchaseEntries(String id) {

        return PurchaseHistory.find(PurchaseHistory.class, "dealer_id = ?", id);
    }

    public void setPurchaseEntries(List<PurchaseHistory> purchaseHistory) {

        for (int i = 0; i < purchaseHistory.size(); i++) {

            List<PurchaseHistory> expenses = find(PurchaseHistory.class, "server_id = ?", purchaseHistory.get(i).getServerId());
            if (expenses == null || expenses.size() == 0) {
                purchaseHistory.get(i).save();
            }
        }

        this.purchaseHistory = purchaseHistory;
    }


    public String getServerId() {
        return serverId;
    }

    public void setServerId(String id) {
        this.serverId = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public List<PaymentHistory> getData() {
        return paymentHistories;
    }

    public void setData(List<PaymentHistory> data) {
        this.paymentHistories = data;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public int getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(int totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductExpense that = (ProductExpense) o;

        return getServerId().equals(that.getServerId());

    }

    @Override
    public int hashCode() {
        return getServerId().hashCode();
    }
}
