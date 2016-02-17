package com.mobiles.mkshop.pojos.models;

import com.orm.SugarRecord;

/**
 * Created by vaibhav on 21/11/15.
 */
public class PurchaseHistory extends SugarRecord  {


    public PurchaseHistory() {
    }


    String totalAmt, image, created, note, serverId, dealerName;

    public String getTotalAmt() {
        return totalAmt;
    }


    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
