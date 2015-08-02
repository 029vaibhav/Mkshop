package com.mobiles.mkshop.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobiles.mkshop.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CommissionReport.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CommissionReport#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommissionReport extends Fragment {

    public static CommissionReport newInstance(String param1, String param2) {

        CommissionReport fragment = new CommissionReport();

        return fragment;
    }

    public CommissionReport() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_commission_report, container, false);



        return viewGroup;
    }


}
