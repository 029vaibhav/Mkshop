package com.mobiles.mkshop.pojos;


import java.util.List;

/**
 * Created by vaibhav on 23/7/15.
 */
//@JsonIgnoreProperties(ignoreUnknown = true)

public class Product {

    String infrared,brand, modelNo, sim, screenSize, displayType, os, iMemory, eMemory, fCamera, bCamera, bluetooth, wlan,nfc,radio, price, battery;

    List<String> path;

    public List<String> getPath() {
        return path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public String getInfrared() {
        return infrared;
    }

    public void setInfrared(String infrared) {
        this.infrared = infrared;
    }

    public String getBrand() {

        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String model) {
        this.modelNo = model;
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

    public void setOs(String platform) {
        this.os = platform;
    }

    public String getiMemory() {
        return iMemory;
    }

    public void setiMemory(String iMemory) {
        this.iMemory = iMemory;
    }

    public String geteMemory() {
        return eMemory;
    }

    public void seteMemory(String eMemory) {
        this.eMemory = eMemory;
    }

    public String getfCamera() {
        return fCamera;
    }

    public void setfCamera(String fCamera) {
        this.fCamera = fCamera;
    }

    public String getbCamera() {
        return bCamera;
    }

    public void setbCamera(String bCamera) {
        this.bCamera = bCamera;
    }

    public String getBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(String bluetooth) {
        this.bluetooth = bluetooth;
    }

    public String getWlan() {
        return wlan;
    }

    public void setWlan(String wifi) {
        this.wlan = wifi;
    }

    public String getNfc() {
        return nfc;
    }

    public void setNfc(String nfc) {
        this.nfc = nfc;
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }
}
