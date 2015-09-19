package com.mobiles.mkshop.pojos;


/**
 * Created by vaibhav on 7/7/15.
 */
//@JsonIgnoreProperties(ignoreUnknown = true)

public class Profile {

    String title,value;


    public Profile(String title,String value)
    {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getvalue() {
        return value;
    }

    public void setvalue(String value) {
        this.value = value;
    }
}
