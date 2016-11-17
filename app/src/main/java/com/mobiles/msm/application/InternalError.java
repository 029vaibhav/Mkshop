package com.mobiles.msm.application;

import java.io.IOException;

/**
 * Created by vaibhav on 9/4/16.
 */
public class InternalError extends IOException {

    public InternalError(String detailMessage) {
        super(detailMessage);
    }
}
