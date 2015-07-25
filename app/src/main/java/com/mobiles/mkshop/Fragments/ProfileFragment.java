package com.mobiles.mkshop.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.mobiles.mkshop.Adapters.ProfileAdapter;
import com.mobiles.mkshop.Application.Client;
import com.mobiles.mkshop.Application.MkShop;
import com.mobiles.mkshop.Pojos.LoginDetails;
import com.mobiles.mkshop.Pojos.Profile;
import com.mobiles.mkshop.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedFile;


public class ProfileFragment extends Fragment implements ImageChooserListener {


    SharedPreferences sharedPreferences;
    public static String TAG = "ProfileFragment";
    private FragmentActivity myContext;
    String username;
    private String mediaPath;
    ImageView header;
    private int chooserType;
    private ImageChooserManager imageChooserManager;
    MaterialDialog materialDialog;
    LoginDetails loginDetailsList;



    CollapsingToolbarLayout collapsingToolbar;
    RecyclerView recyclerView;
    int mutedColor = R.attr.colorPrimary;
    ProfileAdapter simpleRecyclerAdapter;

    public static ProfileFragment newInstance(String param1) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("username", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getArguments() != null ? getArguments().getString("username") : "";


    }

    @Override
    public void onAttach(Activity activity) {

        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        sharedPreferences = getActivity().getSharedPreferences("MKSHOP", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("DETAIL", null);
        Type type = new TypeToken<LoginDetails>() {
        }.getType();
        loginDetailsList = new Gson().fromJson(json, type);
        View viewGroup = inflater.inflate(R.layout.fragment_profile, container, false);
        materialDialog = new MaterialDialog.Builder(getActivity())
                .progress(true, 0)
                .cancelable(false)
                .build();

        collapsingToolbar = (CollapsingToolbarLayout) viewGroup.findViewById(R.id.collapsing_toolbar);

        header = (ImageView) viewGroup.findViewById(R.id.header);


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher);
        if(loginDetailsList.getPhoto().length()>2)
        Picasso.with(getActivity()).load(loginDetailsList.getPhoto().replace("\\", "")).into(header);
        else header.setImageBitmap(bitmap);




        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();

            }
        });

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {

                mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                collapsingToolbar.setContentScrimColor(mutedColor);
            }
        });

        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.scrollableview);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        List<Profile> listData = new ArrayList<Profile>();

        Profile profile = new Profile("name", "vaibhav");
        listData.add(profile);
        profile = new Profile("phone", "9016239078");
        listData.add(profile);
        profile = new Profile("email", "vaibs4007@rediff.com");
        listData.add(profile);

        new GetUserData().execute();


        return viewGroup;
    }

    private void chooseImage() {
        chooserType = ChooserType.REQUEST_PICK_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_PICK_PICTURE, true);
        imageChooserManager.setImageChooserListener(this);
        try {
            mediaPath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onImageChosen(final ChosenImage image) {


        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (image != null) {

                    materialDialog.show();
                    TypedFile typedFile = new TypedFile("multipart/form-data", new File(image.getFileThumbnail().toString()));
                    String username = MkShop.Username;

                    Client.INSTANCE.uploadImage("amrut", typedFile, username, new Callback<String>() {
                        @Override
                        public void success(String s, Response response) {
                            materialDialog.dismiss();
                            header.setImageURI(Uri.parse(new File(image
                                    .getFileThumbnail()).toString()));
                            MkShop.toast(getActivity(), "uploaded successfully");

                        }

                        @Override
                        public void failure(RetrofitError error) {

                            if (error != null) {
                                if (error.getKind().equals(RetrofitError.Kind.NETWORK))
                                    MkShop.toast(getActivity(), "check your internet connection");
                                else MkShop.toast(getActivity(), "something went wrong");

                            }

                        }
                    });


                }
            }
        });


    }

    @Override
    public void onError(final String s) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                MkShop.toast(ProfileFragment.this.getActivity(), s);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("On Activity Result", requestCode + "");
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (imageChooserManager == null) {
                imageChooserManager = new ImageChooserManager(this, requestCode, true);
                imageChooserManager.setImageChooserListener(this);
                imageChooserManager.reinitialize(mediaPath);
            }
            imageChooserManager.submit(requestCode, data);
        }
    }


    private class GetUserData extends AsyncTask<Void, Void, Void> {

        MaterialDialog dialog;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new MaterialDialog.Builder(getActivity())
                    .content("plese wait")
                    .progress(true, 0)
                    .show();
        }


        @Override
        protected Void doInBackground(Void... params) {

            Client.INSTANCE.getProfile(username, new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    String json = (new String(((TypedByteArray) response.getBody()).getBytes()));

                    dialog.dismiss();
                    json = json.replace(",", "},{").replace(":", ",").replace("{\"", "{\"title\":\"").replace("\",\"", "\",\"value\":\"");

                    Log.e("json", json);


                    Type listType = new TypeToken<List<Profile>>() {
                    }.getType();
                    List<Profile> yourList = new Gson().fromJson(json, listType);
//
                    simpleRecyclerAdapter = new ProfileAdapter(username, getActivity(), yourList);
                    recyclerView.setAdapter(simpleRecyclerAdapter);

                }

                @Override
                public void failure(RetrofitError error) {

                    MkShop.toast(getActivity(), error.getMessage());

                }
            });
            return null;
        }
    }
}
