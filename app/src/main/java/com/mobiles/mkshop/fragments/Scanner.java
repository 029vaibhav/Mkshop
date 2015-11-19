package com.mobiles.mkshop.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;
import com.mobiles.mkshop.interfaces.ScannerCallback;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by vaibhav on 15/11/15.
 */
public class Scanner extends DialogFragment implements ZXingScannerView.ResultHandler {

    ScannerCallback scannerCallback;
    private ZXingScannerView mScannerView;
    private static final String FLASH_STATE = "FLASH_STATE";
    private static final String AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE";
    private static final String SELECTED_FORMATS = "SELECTED_FORMATS";
    private static final String CAMERA_ID = "CAMERA_ID";
    private boolean mFlash;
    private boolean mAutoFocus;
    private int mCameraId = -1;


    public void setCallBack(ScannerCallback scannerCallback) {
        this.scannerCallback = scannerCallback;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        mScannerView = new ZXingScannerView(getActivity());
        if (state != null) {
            mFlash = state.getBoolean(FLASH_STATE, false);
            mAutoFocus = state.getBoolean(AUTO_FOCUS_STATE, true);
            mCameraId = state.getInt(CAMERA_ID, -1);


        } else {
            mFlash = false;
            mCameraId = -1;

        }
        return mScannerView;
    }


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setHasOptionsMenu(true);
    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera(mCameraId);
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
    }


    @Override
    public void handleResult(Result rawResult) {


        scannerCallback.setIMEI(rawResult.getText());
    }


    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();

    }

}
