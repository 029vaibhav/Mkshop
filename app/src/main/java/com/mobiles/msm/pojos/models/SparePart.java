package com.mobiles.msm.pojos.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.orm.SugarRecord;

/**
 * Created by vaibhav on 29/6/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SparePart extends SugarRecord {

    private String brand, type, compatibleMobile, description;
    private long quantity;
    private Long id;
    private String created;
    private String modified;

    public SparePart() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompatibleMobile() {
        return compatibleMobile;
    }

    public void setCompatibleMobile(String compatibleMobile) {
        this.compatibleMobile = compatibleMobile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


}
