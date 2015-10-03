package com.mobiles.mkshop.pojos;

import java.util.List;

import retrofit.mime.TypedFile;

/**
 * Created by vaibhav on 2/10/15.
 */
public class ProductExpense {

    private String id;

    int dueAmount;

    private String amount;

    private String created;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
