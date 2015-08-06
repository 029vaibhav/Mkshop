package com.mobiles.mkshop.pojos;

/**
 * Created by vaibhav on 2/8/15.
 */
public enum PaymentType {

    Salary("Salary"),
    Incentive("Incentive"),
    Product("Product");





    private String status;
    PaymentType(String status){
        this.status = status;
    }
}
