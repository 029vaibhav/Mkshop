package com.mobiles.mkshop.pojos.models;

/**
 * Created by vaibhav on 19/12/15.
 */
public class MenuMakerNameClass {


    String menuName;
    String classes;

    public MenuMakerNameClass(String menuName, String classes) {
        this.menuName = menuName;
        this.classes = classes;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getMenuName() {

        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}
