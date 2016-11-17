package com.mobiles.msm.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobiles.msm.R;
import com.mobiles.msm.application.Client;
import com.mobiles.msm.application.MyApplication;
import com.mobiles.msm.pojos.models.AttendanceDates;
//import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CalendarFragment extends Fragment {

    public static String TAG = "CalendarFragment";

//    private CalendarPickerView calendar;
    ArrayList<String> dates;
    String username;


    public static CalendarFragment newInstance(String param1) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString("username", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getArguments() != null ? getArguments().getString("username") : "";


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_calendar, container, false);

//        calendar = (CalendarPickerView) viewGroup.findViewById(R.id.calendar_view);
//        calendar.init(DateTime.now().minusYears(1).toDate(), DateTime.now().plusDays(2).toDate())
//                .inMode(CalendarPickerView.SelectionMode.MULTIPLE);
//        new GetAttendanceList().execute();


        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);


//        dates = new ArrayList<String>();
//
//        for(int i=1;i<12;i++)
//        {
//
//            DateTime dateTime = new DateTime(DateTime.now().getYear(),i,
//                    i,0,0);
//            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            Date date = null;
//            try {
//                date = format.parse(dateTime.toString("yyyy-MM-dd"));
//                Log.e("hi",date.toString());
//                dates.add(dateTime.toString("yyyy-MM-dd"));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//
//
//        }


//        calendar.setDecorators(Collections.<CalendarCellDecorator>emptyList());
//        calendar.init(new Date(), nextYear.getTime()) //
//                .inMode(CalendarPickerView.SelectionMode.MULTIPLE) //
//                .withSelectedDates(dates);

        return viewGroup;
    }


    private class GetAttendanceList extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            Call<List<AttendanceDates>> userAttendance = Client.INSTANCE.getUserAttendance(MyApplication.AUTH, username);
            userAttendance.enqueue(new Callback<List<AttendanceDates>>() {
                @Override
                public void onResponse(Call<List<AttendanceDates>> call, Response<List<AttendanceDates>> response) {

                    List<AttendanceDates> body = response.body();
                    List<String> dates = new ArrayList<String>();
                    for (int i = body.size() - 1; i >= 0; i--) {
                        dates.add(body.get(i).getDate());
                    }
                }

                @Override
                public void onFailure(Call<List<AttendanceDates>> call, Throwable t) {

                }
            });

            return null;
        }
    }
}
