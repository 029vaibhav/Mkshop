package com.mobiles.mkshop.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.R;


public class IsAvailableFragment extends Fragment {



    public static IsAvailableFragment newInstance() {
        IsAvailableFragment fragment = new IsAvailableFragment();

        return fragment;
    }

    public IsAvailableFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MkShop.SCRREN="IsAvailableFragment";
        return inflater.inflate(R.layout.fragment_is_available, container, false);
    }


}
