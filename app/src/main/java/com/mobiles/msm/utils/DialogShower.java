package com.mobiles.msm.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by vaibhav on 17/2/16.
 */
public class DialogShower {

    ProgressDialog progressDialog;

    public DialogShower(Context context) {
        progressDialog = new ProgressDialog(context);
    }

    public void showProgressDialog() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.setMessage("please wait");
            progressDialog.show();
        }
    }

    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
