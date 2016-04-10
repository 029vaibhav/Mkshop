package com.mobiles.mkshop.adapters;

import android.graphics.Color;
import android.util.Log;
//
//import com.squareup.timessquare.CalendarCellDecorator;
//import com.squareup.timessquare.CalendarCellView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by vaibhav on 9/7/15.
 */
public class Decorators/* implements CalendarCellDecorator*/ {

    List<String> dateArrayList;
    DateFormat df;


    public Decorators(List<String> dateArrayList) {
        this.dateArrayList = dateArrayList;
        df = new SimpleDateFormat("yyyy-MM-dd");
    }


   /* @Override
    public void decorate(CalendarCellView cellView, Date date) {

        String reportDate = df.format(date);

        if (dateArrayList.contains(reportDate)) {
            Log.e("dates-matched",date.toString());
            cellView.setBackgroundColor(Color.parseColor("#501abc9c"));
        }
        else {
            cellView.setBackgroundColor(Color.WHITE);
        }

    }*/
}
