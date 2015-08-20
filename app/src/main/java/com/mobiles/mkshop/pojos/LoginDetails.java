package com.mobiles.mkshop.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by vaibhav on 24/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class LoginDetails {

    String name,photo,username,role;
    Location location;
    List<BrandModelList> productList;

    public List<BrandModelList> getProductList() {
        return productList;
    }

    public void setProductList(List<BrandModelList> productList) {
        this.productList = productList;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }



}
