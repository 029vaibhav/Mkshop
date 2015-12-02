package com.mobiles.mkshop.pojos.models;

import com.orm.SugarRecord;

/**
 * Created by arvind on 21/8/15.
 */
public class BrandModelList extends SugarRecord<BrandModelList> {


    String brand, modelNo, accessoryType, type;
    long serverId;

    public BrandModelList() {

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


    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public long getServerId() {
        return serverId;
    }

    public void setServerId(long serverId) {
        this.serverId = serverId;
    }

    public String getAccessoryType() {
        return accessoryType;
    }

    public void setAccessoryType(String accessoryType) {
        this.accessoryType = accessoryType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BrandModelList that = (BrandModelList) o;

        if (!getBrand().equals(that.getBrand())) return false;
        if (!getModelNo().equals(that.getModelNo())) return false;
        if (getAccessoryType() != null ? !getAccessoryType().equals(that.getAccessoryType()) : that.getAccessoryType() != null)
            return false;
        return getType().equals(that.getType());

    }

    @Override
    public int hashCode() {
        int result = getBrand().hashCode();
        result = 31 * result + getModelNo().hashCode();
        result = 31 * result + (getAccessoryType() != null ? getAccessoryType().hashCode() : 0);
        result = 31 * result + getType().hashCode();
        return result;
    }
}
