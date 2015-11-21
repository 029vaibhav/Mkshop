package com.mobiles.mkshop.pojos.enums;

/**
 * Created by vaibhav on 21/11/15.
 */
public enum TransactionType {

    Purchase("Purchase"),
    Payment("Payment");


    private String productType;

    TransactionType(String productType) {
        this.productType = productType;
    }
}
