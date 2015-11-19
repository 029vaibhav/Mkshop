package com.mobiles.mkshop.pojos;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by vaibhav on 2/10/15.
 */
public class ProductExpense {


    public ProductExpense() {

    }

    private String serverId;

    int dueAmount;

    private String amount;

    private String created;

    private String modifiedDate;


    private List<ProductExpenseSingleEntry> productExpenseSingleEntries;

    private String image;

    public int getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(int dueAmount) {
        this.dueAmount = dueAmount;
    }

    private String dealerName;

    private String totalAmt;

    private String note;


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List<ProductExpenseSingleEntry> getProductExpenseSingleEntries() {
        return productExpenseSingleEntries;
    }

    public void setProductExpenseSingleEntries(List<ProductExpenseSingleEntry> productExpenseSingleEntries) {
        this.productExpenseSingleEntries = productExpenseSingleEntries;
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

    public List<ProductExpenseSingleEntry> getData() {
        return productExpenseSingleEntries;
    }

    public void setData(List<ProductExpenseSingleEntry> data) {
        this.productExpenseSingleEntries = data;
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

    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
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
