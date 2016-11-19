package com.mobiles.msm.pojos.models;


import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mobiles.msm.pojos.enums.ProductType;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by vaibhav on 23/7/15.
 */
@SimpleSQLTable(table = "Product", provider = "ProductProvider")

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    @SimpleSQLColumn("brand")
    private String brand;
    @SimpleSQLColumn("model")
    private String model;
    @SimpleSQLColumn("sim")
    private String sim;
    @SimpleSQLColumn("screenSize")
    private String screenSize;
    @SimpleSQLColumn("displayType")
    private String displayType;
    @SimpleSQLColumn("os")
    private String os;
    @SimpleSQLColumn("iMemory")
    private String iMemory;
    @SimpleSQLColumn("eMemory")
    private String eMemory;
    @SimpleSQLColumn("fCamera")
    private String fCamera;
    @SimpleSQLColumn("bCamera")
    private String bCamera;
    @SimpleSQLColumn("wlan")
    private String wlan;
    @SimpleSQLColumn("bluetooth")
    private String bluetooth;
    @SimpleSQLColumn("nfc")
    private String nfc;
    @SimpleSQLColumn("infrared")
    private String infrared;
    @SimpleSQLColumn("radio")
    private String radio;
    @SimpleSQLColumn("battery")
    private String battery;
    @SimpleSQLColumn("price")
    private int price;
    @SimpleSQLColumn("type")
    private String type;
    @SimpleSQLColumn("accessoryType")
    private String accessoryType;
    @SimpleSQLColumn("created")
    private String created;
    @SimpleSQLColumn("modified")
    private String modified;
    @SimpleSQLColumn("id")
    private Long id;

    public Product() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getIMemory() {
        return iMemory;
    }

    public void setIMemory(String iMemory) {
        this.iMemory = iMemory;
    }

    public String getEMemory() {
        return eMemory;
    }

    public void setEMemory(String eMemory) {
        this.eMemory = eMemory;
    }

    public String getFCamera() {
        return fCamera;
    }

    public void setFCamera(String fCamera) {
        this.fCamera = fCamera;
    }

    public String getBCamera() {
        return bCamera;
    }

    public void setBCamera(String bCamera) {
        this.bCamera = bCamera;
    }

    public String getWlan() {
        return wlan;
    }

    public void setWlan(String wlan) {
        this.wlan = wlan;
    }

    public String getBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(String bluetooth) {
        this.bluetooth = bluetooth;
    }

    public String getNfc() {
        return nfc;
    }

    public void setNfc(String nfc) {
        this.nfc = nfc;
    }

    public String getInfrared() {
        return infrared;
    }

    public void setInfrared(String infrared) {
        this.infrared = infrared;
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccessoryType() {
        return accessoryType;
    }

    public void setAccessoryType(String accessoryType) {
        this.accessoryType = accessoryType;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
