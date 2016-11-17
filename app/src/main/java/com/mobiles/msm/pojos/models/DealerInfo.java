package com.mobiles.msm.pojos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.orm.SugarRecord;
@JsonIgnoreProperties(ignoreUnknown = true)

public class DealerInfo extends SugarRecord {

    String dealerName;
    private Long serverId;
    private long totalAmount;
    private long payedAmount;

    public DealerInfo() {
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getPayedAmount() {
        return payedAmount;
    }

    public void setPayedAmount(long payedAmount) {
        this.payedAmount = payedAmount;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }
}
