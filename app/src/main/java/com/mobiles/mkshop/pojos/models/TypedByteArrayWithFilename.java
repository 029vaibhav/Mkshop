package com.mobiles.mkshop.pojos.models;

import retrofit.mime.TypedByteArray;

/**
 * Created by vaibhav on 2/10/15.
 */
public class TypedByteArrayWithFilename extends TypedByteArray {

    private String fileName;

    public TypedByteArrayWithFilename(String mimeType, byte[] bytes, String fileName) {
        super(mimeType, bytes);
        this.fileName = fileName;
    }

    @Override
    public String fileName() {
        return fileName;
    }
}
