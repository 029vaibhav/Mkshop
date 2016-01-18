package com.mobiles.mkshop.utils;

import com.mobiles.mkshop.pojos.enums.UserType;
import com.mobiles.mkshop.pojos.models.MenuMaker;
import com.mobiles.mkshop.pojos.models.MenuMakerNameClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaibhav on 19/12/15.
 */
public class DataFeeder {

    public static MenuMaker getMenu() {
        MenuMaker menuMaker = new MenuMaker();
        menuMaker.setRole(UserType.ADMIN.name());

        List<MenuMakerNameClass> menuMakerNameClasses = new ArrayList<>();
        menuMakerNameClasses.add(new MenuMakerNameClass("Sales Report", "SalesReport"));
        menuMakerNameClasses.add(new MenuMakerNameClass("Service Report", "ServiceReport"));
        menuMakerNameClasses.add(new MenuMakerNameClass("Part Requested", "PartsRequestFragment"));
        menuMakerNameClasses.add(new MenuMakerNameClass("User Data", "UserListFragment"));
        menuMakerNameClasses.add(new MenuMakerNameClass("Leader Board", "LeaderBoardFragment"));
        menuMakerNameClasses.add(new MenuMakerNameClass("Revenue Comparator", "RevenueCompatorFragment"));
        menuMakerNameClasses.add(new MenuMakerNameClass("Send Notification", "SendNotificationFragment"));
        menuMakerNameClasses.add(new MenuMakerNameClass("Incentive", "Incentive"));
        menuMakerNameClasses.add(new MenuMakerNameClass("Expense Manager", "ExpenseManagerFragment"));
        menuMakerNameClasses.add(new MenuMakerNameClass("Wifi", "GeoPointsFragment"));

        menuMaker.setMenu(menuMakerNameClasses);

        return menuMaker;


    }
}
