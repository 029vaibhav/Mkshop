package com.mobiles.mkshop.pojos.models;

import com.orm.SugarRecord;

/**
 * Created by vaibhav on 3/10/15.
 */
public class PaymentHistory extends SugarRecord<PaymentHistory> {



    public PaymentHistory()
    {

    }

    private String amount;

    private String serverId;

    private String dealerId;

    private String created;

    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getServerId ()
    {
        return serverId;
    }

    public void setServerId(String serverId)
    {
        this.serverId = serverId;
    }

    public String getDealerId ()
    {
        return dealerId;
    }

    public void setDealerId (String dealerId)
    {
        this.dealerId = dealerId;
    }

    public String getCreated ()
    {
        return created;
    }

    public void setCreated (String created)
    {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentHistory that = (PaymentHistory) o;

        return getServerId().equals(that.getServerId());

    }

    @Override
    public int hashCode() {
        return getServerId().hashCode();
    }
}