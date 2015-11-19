package com.mobiles.mkshop.pojos;


import java.util.List;

/**
 * Created by vaibhav on 4/7/15.
 */
//@JsonIgnoreProperties(ignoreUnknown = true)

public class Leader {

    String name;
    String quantity;
    String price;
    String role;
    String username;

    List<ProductDetails>productDetail;

    int id;
    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {

        this.name = name;

    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }


    public String getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ProductDetails> getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(List<ProductDetails> productDetail) {
        this.productDetail = productDetail;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
