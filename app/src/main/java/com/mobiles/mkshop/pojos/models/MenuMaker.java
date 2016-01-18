package com.mobiles.mkshop.pojos.models;

import java.util.List;

/**
 * Created by vaibhav on 19/12/15.
 */
public class MenuMaker {

    String role;
    List<MenuMakerNameClass> menu;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<MenuMakerNameClass> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuMakerNameClass> menu) {
        this.menu = menu;
    }
}
