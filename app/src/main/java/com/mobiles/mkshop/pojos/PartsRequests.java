package com.mobiles.mkshop.pojos;


/**
 * Created by vaibhav on 29/6/15.
 */
//@JsonIgnoreProperties(ignoreUnknown = true)

public class PartsRequests {


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDeliveryDate(String deliveryDate) {

        this.deliveryDate = deliveryDate;
    }

    String customerName, createdDate, part, deliveryDate,status;
    String price, mobileNo;
    int id;
}
