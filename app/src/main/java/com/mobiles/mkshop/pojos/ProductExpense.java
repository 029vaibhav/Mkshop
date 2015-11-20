package com.mobiles.mkshop.pojos;

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

    private String amount;

    private String created;
    @Ignore
    private List<ProductExpenseSingleEntry> productExpenseSingleEntries;

    private String image;

    private String dealerName;

    private String totalAmt;

    private String note;

    public String getAmount() {
        return amount;
    }


    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(int dueAmount) {
        this.dueAmount = dueAmount;
    }

    public List<ProductExpenseSingleEntry> getProductExpenseSingleEntries() {
        return productExpenseSingleEntries;
    }

    public void setProductExpenseSingleEntries(List<ProductExpenseSingleEntry> productExpenseSingleEntries) {
        this.productExpenseSingleEntries = productExpenseSingleEntries;
    }


    public List<ProductExpenseSingleEntry> getEntries(String id) {

        return ProductExpenseSingleEntry.find(ProductExpenseSingleEntry.class, "dealer_id = ?", id);
    }

    public void setEntries(List<ProductExpenseSingleEntry> productExpenseSingleEntries) {

        for (int i = 0; i < productExpenseSingleEntries.size(); i++) {

            List<ProductExpenseSingleEntry> expenses = ProductExpenseSingleEntry.find(ProductExpenseSingleEntry.class, "server_id = ?", productExpenseSingleEntries.get(i).getServerId());
            if (expenses == null || expenses.size() == 0) {
                productExpenseSingleEntries.get(i).save();
            }
        }

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
