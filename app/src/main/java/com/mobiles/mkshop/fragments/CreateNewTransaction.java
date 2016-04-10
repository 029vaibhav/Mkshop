package com.mobiles.mkshop.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ChosenImages;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.mobiles.mkshop.R;
import com.mobiles.mkshop.activities.NavigationMenuActivity;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.pojos.models.DealerInfo;
import com.mobiles.mkshop.pojos.models.Purchase;

import java.io.ByteArrayOutputStream;
import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateNewTransaction extends Fragment implements ImageChooserListener {
    // TODO: Rename parameter arguments, choose names that match

    public static String TAG = "CreateNewTransaction";

    ProgressDialog materialDialog;
    ImageView header;
    Bitmap bitmap;
    private ImageChooserManager imageChooserManager;
    private String mediaPath;
    EditText dealerNameEditText, totalAmt, note;
    Long dealerId;
    Button submit;
    RequestBody typedFile;
    String image;


    public static CreateNewTransaction newInstance(Long dealerId) {
        CreateNewTransaction fragment = new CreateNewTransaction();
        Bundle args = new Bundle();
        if (dealerId != null)
            args.putLong("dealerName", dealerId);
        fragment.setArguments(args);
        return fragment;
    }

    public CreateNewTransaction() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dealerId = getArguments() != null ? getArguments().getLong("dealerName") : null;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        MkShop.SCRREN = "CreateNewTransaction";
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_register_product_expense, container, false);

        init(viewGroup);


        if (dealerId != null) {

            DealerInfo dealerInfo = DealerInfo.find(DealerInfo.class, "server_id = ? ", String.valueOf(dealerId)).get(0);
            dealerNameEditText.setText(dealerInfo.getDealerName());
            dealerNameEditText.setEnabled(false);
        }

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askImageOption();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOnclick();
            }
        });


        return viewGroup;
    }

    private void submitOnclick() {


        if (dealerNameEditText.getText().length() == 0) {
            MkShop.toast(getActivity(), "please enter dealer name");
        } else if (totalAmt.getText().length() == 0) {
            MkShop.toast(getActivity(), "please enter total amt");
        } else {
            final Purchase purchase = new Purchase();
            purchase.setAmount(Integer.parseInt(totalAmt.getText().toString()));
            purchase.setDealerId(dealerId);
            purchase.setNote("" + note.getText());
            purchase.setImage(image);

            materialDialog.show();
            Call<Purchase> purchaseCall = Client.INSTANCE.productPurchase(MkShop.AUTH, purchase);
            purchaseCall.enqueue(new Callback<Purchase>() {
                @Override
                public void onResponse(Call<Purchase> call, Response<Purchase> response) {

                    if (materialDialog != null && materialDialog.isShowing())
                        materialDialog.dismiss();
                    int backStackEntryCount = getFragmentManager().getBackStackEntryCount();
                    for (int i = 0; i < backStackEntryCount; i++) {
                        getFragmentManager().popBackStack();
                    }
                    Fragment fragment = ExpenseManagerFragment.newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    MkShop.toast(getActivity(), "successfully purchased");

                }

                @Override
                public void onFailure(Call<Purchase> call, Throwable t) {

                    if (materialDialog != null && materialDialog.isShowing())
                        materialDialog.dismiss();
                    MkShop.toast(getActivity(), t.getMessage());

                }
            });
        }
    }

    private void init(ViewGroup viewGroup) {


        materialDialog = NavigationMenuActivity.materialDialog;

        dealerNameEditText = (EditText) viewGroup.findViewById(R.id.dealer_edit);
        totalAmt = (EditText) viewGroup.findViewById(R.id.total_edit);
        note = (EditText) viewGroup.findViewById(R.id.note);
        submit = (Button) viewGroup.findViewById(R.id.submit);
        header = (ImageView) viewGroup.findViewById(R.id.header);


        bitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_mkshop);
    }

    private void askImageOption() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select");
        builder.setItems(R.array.image_option, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                switch (item) {
                    case 0: //view profile
                        dialog.dismiss();
                        takePicture();
                        break;
                    case 1: // view attendance
                        dialog.dismiss();
                        chooseImage();
                        break;

                }


            }
        });
        AlertDialog alert = builder.create();
        alert.show();

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
                    typedFile = RequestBody.create(MediaType.parse("multipart/form-data"), new File(chosenImage.getFileThumbnail().toString()));
                    header.setImageURI(Uri.parse(new File(chosenImage
                            .getFileThumbnail()).toString()));
//
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

    @Override
    public void onImagesChosen(ChosenImages chosenImages) {

        onImageChosen(chosenImages.getImage(0));

    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

}
