package com.mobiles.msm.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ChosenImages;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.mobiles.msm.R;
import com.mobiles.msm.activities.NavigationMenuActivity;
import com.mobiles.msm.adapters.ProfileAdapter;
import com.mobiles.msm.application.Client;
import com.mobiles.msm.application.MyApplication;
import com.mobiles.msm.pojos.models.Profile;
import com.mobiles.msm.pojos.models.User;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment implements ImageChooserListener {


    public static String TAG = "ProfileFragment";
    String username;
    private String mediaPath;
    ImageView header;
    private ImageChooserManager imageChooserManager;
    ProgressDialog materialDialog;


    CollapsingToolbarLayout collapsingToolbar;
    RecyclerView recyclerView;
    ProfileAdapter simpleRecyclerAdapter;
    Bitmap bitmap;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View viewGroup = inflater.inflate(R.layout.fragment_profile, container, false);
        materialDialog = NavigationMenuActivity.materialDialog;

        collapsingToolbar = (CollapsingToolbarLayout) viewGroup.findViewById(R.id.collapsing_toolbar);

        header = (ImageView) viewGroup.findViewById(R.id.header);


        bitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_mkshop);


        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();

            }
        });


        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.scrollableview);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);


        new GetUserData().execute();


        return viewGroup;
    }

    private void chooseImage() {
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
                    RequestBody typedFile = RequestBody.create(MediaType.parse("multipart/form-data"), new File(image.getFileThumbnail().toString()));
//                    Client.INSTANCE.uploadImage(MkShop.AUTH, username, typedFile, "", new Callback<String>() {
//                        @Override
//                        public void success(String s, Response response) {
//                            if (materialDialog != null && materialDialog.isShowing())
//                                materialDialog.dismiss();
//                            header.setImageURI(Uri.parse(new File(image
//                                    .getFileThumbnail()).toString()));
//                            MkShop.toast(getActivity(), "uploaded successfully");
//
//                        }
//
//                        @Override
//                        public void failure(RetrofitError error) {
//
//                            if (materialDialog != null && materialDialog.isShowing())
//                                materialDialog.dismiss();
//
//                            if (error != null) {
//                                if (t instanceof IOException)
//                                    MkShop.toast(getActivity(), "check your internet connection");
//                                else MkShop.toast(getActivity(), "something went wrong");
//
//                            }
//
//                        }
//                    });


                }
            }
        });


    }

    @Override
    public void onError(final String s) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                MyApplication.toast(ProfileFragment.this.getActivity(), s);
            }
        });
    }

    @Override
    public void onImagesChosen(ChosenImages chosenImages) {
        onImageChosen(chosenImages.getImage(0));
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

        ProgressDialog dialog;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }


        @Override
        protected Void doInBackground(Void... params) {

            Client.INSTANCE.getProfile(MyApplication.AUTH, username).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (materialDialog != null && materialDialog.isShowing())
                        materialDialog.dismiss();
                    List<Profile> profiles1 = convertUserToList(response.body());
                    if (response.body().getPhoto() != null && response.body().getPhoto().length() > 2)
                        Picasso.with(getActivity()).load(response.body().getPhoto()).into(header);
                    else header.setImageBitmap(bitmap);
//
                    simpleRecyclerAdapter = new ProfileAdapter(username, getActivity(), profiles1);
                    recyclerView.setAdapter(simpleRecyclerAdapter);

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    if (materialDialog != null && materialDialog.isShowing())
                        materialDialog.dismiss();
                    MyApplication.toast(getActivity(), t.getMessage());

                }
            });
            return null;
        }
    }

    private List<Profile> convertUserToList(User response) {

        List<Profile> profiles = new ArrayList<>();

        Profile profile = new Profile("name", response.getName());
        profiles.add(profile);
        profile = new Profile("address", response.getAddress());
        profiles.add(profile);
        profile = new Profile("email", response.getEmail());
        profiles.add(profile);
        profile = new Profile("photo", response.getPhoto());
        profiles.add(profile);
        profile = new Profile("qualification", response.getQualification());
        profiles.add(profile);
        profile = new Profile("username", response.getUsername());
        profiles.add(profile);
        profile = new Profile("password", response.getPassword());
        profiles.add(profile);
        profile = new Profile("role", response.getRole());
        profiles.add(profile);
        profile = new Profile("mobile", response.getMobile());
        profiles.add(profile);

        return profiles;
    }
}
