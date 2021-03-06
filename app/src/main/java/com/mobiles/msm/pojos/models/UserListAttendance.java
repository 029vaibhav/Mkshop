package com.mobiles.msm.pojos.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by vaibhav on 4/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserListAttendance {
    String username;

    String name, mobile, present, month, totalDay;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;

    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTotalDay() {
        return totalDay;
    }


    public void setTotalDay(String totalDay) {
        this.totalDay = totalDay;
    }

}
