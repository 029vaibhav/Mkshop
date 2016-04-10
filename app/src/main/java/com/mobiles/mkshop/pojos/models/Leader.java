package com.mobiles.mkshop.pojos.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by vaibhav on 4/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Leader {

    User user;
    List<Sales> product;
    List<ServiceCenterEntity> technicals;

    public List<ServiceCenterEntity> getTechnicals() {
        return technicals;
    }

    public void setTechnicals(List<ServiceCenterEntity> technicals) {
        this.technicals = technicals;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Sales> getProduct() {
        return product;
    }

    public void setProduct(List<Sales> product) {
        this.product = product;
    }
}
