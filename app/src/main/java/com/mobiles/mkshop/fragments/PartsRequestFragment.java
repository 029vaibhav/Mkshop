package com.mobiles.mkshop.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.melnykov.fab.FloatingActionButton;
import com.mobiles.mkshop.adapters.PartRequestAdapter;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.application.Myenum;
import com.mobiles.mkshop.pojos.PartsRequests;
import com.mobiles.mkshop.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class PartsRequestFragment extends Fragment {

    public static String TAG = "PartsRequestFragment";

    PartRequestAdapter partRequestAdapter;
    List<PartsRequests> partsRequestsList;
    MaterialDialog materialDialog;
    ListView listView;



    public static PartsRequestFragment newInstance() {
        PartsRequestFragment fragment = new PartsRequestFragment();
        return fragment;
    }

    public PartsRequestFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MkShop.SCRREN="PartsRequestFragment";
        ViewGroup viewGroup=(ViewGroup)inflater.inflate(R.layout.fragment_parts_request, container, false);


        partsRequestsList = new ArrayList<PartsRequests>();

        final EditText search = (EditText) viewGroup.findViewById(R.id.editsearch);

        materialDialog = new MaterialDialog.Builder(getActivity())
                .progress(false, 0)
                .content("please wait")
                .build();

         listView = (ListView) viewGroup.findViewById(R.id.repairlist);
        FloatingActionButton fab = (FloatingActionButton) viewGroup.findViewById(R.id.fab);
        fab.attachToListView(listView);

        new ListInitializer().execute();





        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = search.getText().toString().toLowerCase(Locale.getDefault());
                partRequestAdapter.Filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Myenum.INSTANCE.setRequestRepair(partsRequestsList.get(position));
                Fragment fragment = new PartsRequestListItemFragment();
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PartsRequestNewItemFragment();
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });


        return viewGroup;
    }


    private class ListInitializer extends AsyncTask<Void,Void,Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();



            materialDialog.show();


        }

        @Override
        protected Void doInBackground(Void... params) {
            Client.INSTANCE.getPartList(new Callback<List<PartsRequests>>() {
                @Override
                public void success(List<PartsRequests> partsRequestses, Response response) {
                    materialDialog.dismiss();
                    partsRequestsList = partsRequestses;
                    Myenum.INSTANCE.setPartsRequestsList(partsRequestsList);
                    partRequestAdapter = new PartRequestAdapter(getActivity(), partsRequestsList);
                    listView.setAdapter(partRequestAdapter);

                }

                @Override
                public void failure(RetrofitError error) {
                    materialDialog.dismiss();

                    if(error.getKind().equals(RetrofitError.Kind.NETWORK))
                    {
                        MkShop.toast(getActivity(), "Please check your internet connection");
                    }
                    else
                    {
                        MkShop.toast(getActivity(), "something went wrong");

                    }

                }
            });


            return null;
        }
    }
}
