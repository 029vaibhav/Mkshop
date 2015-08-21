package com.mobiles.mkshop.pojos;

import com.orm.SugarRecord;

/**
 * Created by arvind on 21/8/15.
 */
public class BrandModelList extends SugarRecord<BrandModelList>{


    String brand,modelNo,accessoryType,type;
    long serverId;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModelNo() {
        return modelNo;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
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
}
