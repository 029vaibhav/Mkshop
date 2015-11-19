package com.mobiles.mkshop.pojos;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Created by vaibhav on 3/10/15.
 */
public class ProductExpenseSingleEntry  {



    public ProductExpenseSingleEntry()
    {

    }

    private String amount;

    private String serverId;

    private String dealerId;

    private String created;

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

        ProductExpenseSingleEntry that = (ProductExpenseSingleEntry) o;

        return getServerId().equals(that.getServerId());

    }

    @Override
    public int hashCode() {
        return getServerId().hashCode();
    }
}