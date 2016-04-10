package com.mobiles.mkshop.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vaibhav on 22/3/16.
 */
public class Utilities {

    public static String formatDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date dateTime = formatter.parse(date);
            SimpleDateFormat mdyFormat = new SimpleDateFormat("dd-MM-yyyy");
            String mdy = mdyFormat.format(dateTime);
            return mdy;
        } catch (ParseException e) {
            throw new RuntimeException();
        }
    }
}
