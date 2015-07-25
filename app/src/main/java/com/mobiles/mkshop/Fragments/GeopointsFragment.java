package com.mobiles.mkshop.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobiles.mkshop.R;


public class GeopointsFragment extends Fragment {


    public static GeopointsFragment newInstance() {
        GeopointsFragment fragment = new GeopointsFragment();
        return fragment;
    }

    public GeopointsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_geopoints, container, false);
        return viewGroup;
    }


}
