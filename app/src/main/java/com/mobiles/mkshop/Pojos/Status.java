package com.mobiles.mkshop.Pojos;

/**
 * Created by vaibhav on 3/7/15.
 */
public enum  Status {

    PENDING("Pending"),
    PROCESSING("Processing"),
    PNA("PNA"),
    RETURN("Return"),
    RETURNED("Returned"),
    DELIVERED("Delivered"),
    DONE("Done"),
    RECIEVED("Recieved");




    private String status;
    Status(String status){
        this.status = status;
    }
}
