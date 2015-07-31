package com.mobiles.mkshop.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by vaibhav on 18/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class AttendanceDates {

    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
