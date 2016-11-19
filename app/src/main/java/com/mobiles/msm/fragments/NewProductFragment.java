package com.mobiles.msm.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mobiles.msm.R;
import com.mobiles.msm.activities.NavigationMenuActivity;
import com.mobiles.msm.application.Client;
import com.mobiles.msm.application.MyApplication;
import com.mobiles.msm.contentprovider.ProductHelper;
import com.mobiles.msm.pojos.enums.ProductType;
import com.mobiles.msm.pojos.models.Product;
import com.mobiles.msm.utils.JsoupUtilities;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewProductFragment extends Fragment {

    EditText brandEditText, modelEditText, simEditText, screenSizeEditText, displayTypeEditText, osEditText, iMemoryEditText, radioEditText, gcmEditText;
    EditText eMemoryEditText, fCameraEditText, bCameraEditText, wlanEditText, bluetoothEditText, nfcEditText, infraredEditText, priceEditText, batteryEditText;
    Button gcmButton, submit;
    private Object dataFromWebsite;
    ProgressDialog progressDialog;

    public NewProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup inflate = (ViewGroup) inflater.inflate(R.layout.fragment_product, container, false);

        initViews(inflate);
        return inflate;
    }

    private void initViews(ViewGroup v) {

        brandEditText = (EditText) v.findViewById(R.id.brand);
        modelEditText = (EditText) v.findViewById(R.id.model);
        radioEditText = (EditText) v.findViewById(R.id.radio);
        simEditText = (EditText) v.findViewById(R.id.sim);
        screenSizeEditText = (EditText) v.findViewById(R.id.screenSize);
        displayTypeEditText = (EditText) v.findViewById(R.id.displayType);
        osEditText = (EditText) v.findViewById(R.id.os);
        iMemoryEditText = (EditText) v.findViewById(R.id.iMemory);
        eMemoryEditText = (EditText) v.findViewById(R.id.eMemory);
        fCameraEditText = (EditText) v.findViewById(R.id.fCamera);
        bCameraEditText = (EditText) v.findViewById(R.id.bCamera);
        wlanEditText = (EditText) v.findViewById(R.id.wlan);
        bluetoothEditText = (EditText) v.findViewById(R.id.bluetooth);
        nfcEditText = (EditText) v.findViewById(R.id.nfc);
        infraredEditText = (EditText) v.findViewById(R.id.infrared);
        priceEditText = (EditText) v.findViewById(R.id.price);
        batteryEditText = (EditText) v.findViewById(R.id.battery);
        gcmEditText = (EditText) v.findViewById(R.id.gsm_link);
        gcmButton = (Button) v.findViewById(R.id.gsm_submit);
        submit = (Button) v.findViewById(R.id.form_submit);
        progressDialog = NavigationMenuActivity.materialDialog;

        gcmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromWebsite();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateMobile();

            }
        });
    }

    private void validateMobile() {

        final Product product = convertEditTextToProducts();
        if (product.getBrand() == null || product.getBrand().length() == 0) {
            MyApplication.toast(getActivity(), "Brand cannot be empty");
        } else if (product.getModel() == null || product.getModel().length() == 0) {
            MyApplication.toast(getActivity(), "Model cannot be empty");
        } else if (product.getPrice() == 0) {
            MyApplication.toast(getActivity(), "Price of a mobile cannot be 0");
        } else {
            progressDialog.show();
            product.setType(ProductType.Mobile.name());
            Call<Product> productCall = Client.INSTANCE.createProduct(MyApplication.AUTH, product);
            productCall.enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {

                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        MyApplication.toast(getActivity(), "Successfully registered a new Mobile");
                        ProductHelper.createProduct(getActivity().getContentResolver(),response.body());
                    }

                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {

                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (t.getMessage() != null) {
                        MyApplication.toast(getActivity(), t.getMessage());
                    } else {
                        MyApplication.toast(getActivity(), "some unknown error occurred");
                    }
                }
            });
        }


    }

    private Product convertEditTextToProducts() {

        Product product = new Product();
        if (brandEditText.getText() != null && brandEditText.getText().length() > 0) {
            product.setBrand(brandEditText.getText().toString().trim().toUpperCase());
        }
        if (modelEditText.getText() != null && modelEditText.getText().length() > 0) {
            product.setModel(modelEditText.getText().toString().trim().toUpperCase());
        }
        if (radioEditText.getText() != null && radioEditText.getText().length() > 0) {
            product.setRadio(radioEditText.getText().toString().trim().toUpperCase());
        }
        if (simEditText.getText() != null && simEditText.getText().length() > 0) {
            product.setSim(simEditText.getText().toString().trim().toUpperCase());
        }
        if (screenSizeEditText.getText() != null && screenSizeEditText.getText().length() > 0) {
            product.setScreenSize(screenSizeEditText.getText().toString().trim().toUpperCase());
        }
        if (displayTypeEditText.getText() != null && displayTypeEditText.getText().length() > 0) {
            product.setDisplayType(displayTypeEditText.getText().toString().trim().toUpperCase());
        }
        if (osEditText.getText() != null && osEditText.getText().length() > 0) {
            product.setOs(osEditText.getText().toString().trim().toUpperCase());
        }
        if (iMemoryEditText.getText() != null && iMemoryEditText.getText().length() > 0) {
            product.setIMemory(iMemoryEditText.getText().toString().trim().toUpperCase());
        }
        if (eMemoryEditText.getText() != null && eMemoryEditText.getText().length() > 0) {
            product.setEMemory(eMemoryEditText.getText().toString().trim().toUpperCase());
        }
        if (fCameraEditText.getText() != null && fCameraEditText.getText().length() > 0) {
            product.setFCamera(fCameraEditText.getText().toString().trim().toUpperCase());
        }
        if (bCameraEditText.getText() != null && bCameraEditText.getText().length() > 0) {
            product.setBCamera(bCameraEditText.getText().toString().trim().toUpperCase());
        }
        if (wlanEditText.getText() != null && wlanEditText.getText().length() > 0) {
            product.setWlan(wlanEditText.getText().toString().trim().toUpperCase());
        }
        if (bluetoothEditText.getText() != null && bluetoothEditText.getText().length() > 0) {
            product.setBluetooth(bluetoothEditText.getText().toString().trim().toUpperCase());
        }
        if (nfcEditText.getText() != null && nfcEditText.getText().length() > 0) {
            product.setNfc(nfcEditText.getText().toString().trim().toUpperCase());
        }
        if (priceEditText.getText() != null && priceEditText.getText().length() > 0) {
            product.setPrice(Integer.parseInt(priceEditText.getText().toString()));
        }
        if (batteryEditText.getText() != null && batteryEditText.getText().length() > 0) {
            product.setBattery(batteryEditText.getText().toString().trim().toUpperCase());
        }

        return product;
    }

    private void updateUI(Product product) {

        if (product.getBrand() != null)
            brandEditText.setText(product.getBrand());
        if (product.getModel() != null)
            modelEditText.setText(product.getModel());
        if (product.getRadio() != null)
            radioEditText.setText(product.getRadio());
        if (product.getSim() != null)
            simEditText.setText(product.getSim());
        if (product.getScreenSize() != null)
            screenSizeEditText.setText(product.getScreenSize());
        if (product.getDisplayType() != null)
            displayTypeEditText.setText(product.getDisplayType());
        if (product.getOs() != null)
            osEditText.setText(product.getOs());
        if (product.getIMemory() != null)
            iMemoryEditText.setText(product.getIMemory());
        if (product.getEMemory() != null)
            eMemoryEditText.setText(product.getEMemory());
        if (product.getFCamera() != null)
            fCameraEditText.setText(product.getFCamera());
        if (product.getBCamera() != null)
            bCameraEditText.setText(product.getBCamera());
        if (product.getWlan() != null)
            wlanEditText.setText(product.getWlan());
        if (product.getBluetooth() != null)
            bluetoothEditText.setText(product.getBluetooth());
        if (product.getNfc() != null)
            nfcEditText.setText(product.getNfc());
        if (product.getInfrared() != null)
            infraredEditText.setText(product.getInfrared());
        if (product.getBattery() != null)
            batteryEditText.setText(product.getBattery());

    }

    public void getDataFromWebsite() {


        JsoupUtilities jsoupUtilities = new JsoupUtilities();
        String gsmArenaLink = gcmEditText.getText().toString();
        if (gsmArenaLink != null && gsmArenaLink.contains("gsmarena.com")) {
            progressDialog.show();
            HashMap<String, HashMap<String, String>> data = jsoupUtilities.getData(gsmArenaLink);
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
            if (data != null) {
                String brand = "", model = "";
                String replace = null;
                if (gsmArenaLink.contains("http://m.gsmarena.com/")) {
                    replace = gsmArenaLink.replace("http://m.gsmarena.com/", "");
                } else if (gsmArenaLink.contains("http://www.gsmarena.com/")) {
                    replace = gsmArenaLink.replace("http://m.gsmarena.com/", "");
                }
                if (replace != null) {
                    String[] split = replace.split("-");
                    if (split.length > 1) {
                        String brandModel = split[0];
                        String[] split1 = brandModel.split("_");
                        if (split1.length > 1) {
                            brand = split1[0];
                            for (int i = 1; i < split1.length; i++) {
                                model = model + " " + split1[i];
                            }
                        }
                    }
                }
                Product product = new Product();
                product.setBrand(brand);
                product.setModel(model);
                HashMap<String, String> display = data.get("Display");
                if (display != null) {
                    product.setDisplayType(display.get("Type"));
                    product.setScreenSize(display.get("Size"));
                }
                HashMap<String, String> camera = data.get("Camera");
                if (camera != null) {
                    product.setBCamera(camera.get("Secondary"));
                    product.setFCamera(camera.get("Primary"));
                }
                HashMap<String, String> comms = data.get("Comms");
                if (comms != null) {
                    product.setBluetooth(comms.get("Bluetooth"));
                    product.setRadio(comms.get("Radio"));
                    product.setNfc(comms.get("NFC"));
                    product.setWlan(comms.get("WLAN"));
                }
                HashMap<String, String> memory = data.get("Memory");
                if (memory != null) {
                    product.setEMemory(memory.get("Card slot"));
                    product.setIMemory(memory.get("Internal"));
                }
                HashMap<String, String> battery = data.get("Battery");
                if (battery != null)
                    product.setBattery(battery.get("Talk time"));

                HashMap<String, String> platform = data.get("Platform");
                if (platform != null)
                    product.setOs(platform.get("OS"));
                HashMap<String, String> body = data.get("Body");
                if (body != null)
                    product.setSim(body.get("SIM"));
                updateUI(product);
            }
        } else {
            MyApplication.toast(getActivity(), "Sorry we are not able to get data from given url");
        }
    }
}
