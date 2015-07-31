package com.mobiles.mkshop.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by vaibhav on 30/6/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Sales {

    long id;
    String type,brand,modelNo,quantity,price,accessoryType, username,path,customerName,mobile,imei;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setType(String productType) {
        this.type = productType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModel(String model) {
        this.modelNo = model;
    }



    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }


    public String getAccessoryType() {
        return accessoryType;
    }

    public void setAccessoryType(String accessoryType) {
        this.accessoryType = accessoryType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }



}
