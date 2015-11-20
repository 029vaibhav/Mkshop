package com.mobiles.mkshop.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.mobiles.mkshop.R;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.pojos.ProductExpense;

import java.io.ByteArrayOutputStream;
import java.io.File;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;


public class RegisterProductExpenseFragment extends Fragment implements ImageChooserListener {
    // TODO: Rename parameter arguments, choose names that match

    public static String TAG = "RegisterProductExpenseFragment";

    MaterialDialog materialDialog;
    ImageView header;
    CollapsingToolbarLayout collapsingToolbar;
    Bitmap bitmap;
    private ImageChooserManager imageChooserManager;
    private String mediaPath;
    EditText dealerName, totalAmt, nowPaying, note;
    Button submit;
    TypedFile typedFile;
    String image;


    public static RegisterProductExpenseFragment newInstance() {
        RegisterProductExpenseFragment fragment = new RegisterProductExpenseFragment();
        return fragment;
    }

    public RegisterProductExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        MkShop.SCRREN = "RegisterProductExpenseFragment";
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_register_product_expense, container, false);

        materialDialog = new MaterialDialog.Builder(getActivity())
                .progress(true, 0)
                .cancelable(false)
                .build();

        dealerName = (EditText) viewGroup.findViewById(R.id.dealer_edit);
        totalAmt = (EditText) viewGroup.findViewById(R.id.total_edit);
        nowPaying = (EditText) viewGroup.findViewById(R.id.amt_paying_now);
        note = (EditText) viewGroup.findViewById(R.id.note);
        submit = (Button) viewGroup.findViewById(R.id.submit);


        collapsingToolbar = (CollapsingToolbarLayout) viewGroup.findViewById(R.id.collapsing_toolbar);

        header = (ImageView) viewGroup.findViewById(R.id.header);


        bitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_mkshop);


        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                askimageoption();


            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (dealerName.getText().length() == 0) {
                    MkShop.toast(getActivity(), "please enter dealer name");
                } else if (totalAmt.getText().length() == 0) {
                    MkShop.toast(getActivity(), "please enter total amt");
                } else if (nowPaying.getText().length() == 0) {
                    MkShop.toast(getActivity(), "please enter now paying amt");
                } else if (image == null) {
                    MkShop.toast(getActivity(), "please select bill image");
                } else {
                    ProductExpense expenseEntity = new ProductExpense();
                    expenseEntity.setTotalAmt(totalAmt.getText().toString());
                    expenseEntity.setDealerName(dealerName.getText().toString());
                    expenseEntity.setNote("" + note.getText());
                    expenseEntity.setAmount(nowPaying.getText().toString());
                    expenseEntity.setImage(image);

                    materialDialog.show();
                    Client.INSTANCE.productPurchase(MkShop.AUTH, expenseEntity, new Callback<String>() {
                        @Override
                        public void success(String s, Response response) {

                            if (materialDialog != null && materialDialog.isShowing())
                                materialDialog.dismiss();
                            MkShop.toast(getActivity(), s);
                        }

                        @Override
                        public void failure(RetrofitError error) {

                            if (materialDialog != null && materialDialog.isShowing())
                                materialDialog.dismiss();
                            MkShop.toast(getActivity(), error.getMessage());
                        }
                    });
                }

            }
        });


        return viewGroup;
    }

    private void askimageoption() {

        new MaterialDialog.Builder(getActivity())
                .items(R.array.image_option)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(final MaterialDialog dialog, View view, int which, CharSequence text) {

                        switch (which) {
                            case 0: //view profile
                                if (dialog != null && dialog.isShowing())
                                    dialog.dismiss();
                                takePicture();
                                break;
                            case 1: // view attendance
                                if (dialog != null && dialog.isShowing())
                                    dialog.dismiss();
                                chooseImage();
                                break;

                        }


                    }
                })
                .show();


    }

    private void takePicture() {
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_CAPTURE_PICTURE, true);
        imageChooserManager.setImageChooserListener(this);
        try {
            mediaPath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void onImageChosen(final ChosenImage chosenImage) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {


                if (chosenImage != null) {
                    typedFile = new TypedFile("multipart/form-data", new File(chosenImage.getFileThumbnail().toString()));
                    header.setImageURI(Uri.parse(new File(chosenImage
                            .getFileThumbnail()).toString()));

                    Bitmap bitmap = ((BitmapDrawable) header.getDrawable()).getBitmap();

                    image = Base64.encodeToString(getBytesFromBitmap(bitmap),
                            Base64.NO_WRAP);


//                    image = chosenImage.getFileThumbnail().getBytes();
                }
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

    @Override
    public void onError(String s) {

    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

}
