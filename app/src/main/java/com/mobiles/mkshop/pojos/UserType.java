package com.mobiles.mkshop.pojos;

/**
 * Created by vaibhav on 19/7/15.
 */
public enum UserType {

    ADMIN("Admin"),
    RECEPTIONIST("Receptionist"),
    SALESMAN("Salesman"),
    TECHNICIAN("Technician");





    private String usertype;
    UserType(String usertype){
        this.usertype = usertype;
    }
}
