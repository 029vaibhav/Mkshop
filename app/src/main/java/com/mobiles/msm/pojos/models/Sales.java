package com.mobiles.msm.pojos.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mobiles.msm.pojos.enums.ProductType;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by vaibhav on 30/6/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Sales {

    long id;
    String brand, model, quantity, price, accessoryType, username, path, customerName, mobile, imei, name, created;
    ProductType productType;
    DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
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

    public String getCreated() {
        if (created != null) {
            DateTime dateTime = formatter.parseDateTime(created);
            return dateTime.toString("yyyy-MM-dd HH:mm:ss");
        }
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImei() {
        return imei;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImei(String imei) {
        this.imei = imei;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
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

    @Override
    public boolean equals(Object o) {
        Sales sales = (Sales) o;
        if (this.getUsername().equalsIgnoreCase(sales.getUsername()))
            return true;
        return false;
    }

}
