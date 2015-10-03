package com.mobiles.mkshop.pojos;

/**
 * Created by vaibhav on 3/10/15.
 */
public class ProductExpenseSingleEntry {

    private String amount;

    private String id;

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

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
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


}