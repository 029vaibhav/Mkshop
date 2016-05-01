package com.mobiles.mkshop.pojos.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mobiles.mkshop.pojos.enums.PaymentType;

/**
 * Created by vaibhav on 2/8/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeExpense {

    String username, created, modified;
    PaymentType paymentType;
    int amount;
    Long ID;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


}
