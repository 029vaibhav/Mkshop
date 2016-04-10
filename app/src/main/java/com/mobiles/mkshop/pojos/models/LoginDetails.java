package com.mobiles.mkshop.pojos.models;

import java.util.List;

/**
 * Created by vaibhav on 24/7/15.
 */
public class LoginDetails {

    User user;
    List<Product> products;
    Location location;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
