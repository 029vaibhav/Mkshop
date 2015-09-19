package com.mobiles.mkshop.pojos;


/**
 * Created by vaibhav on 28/6/15.
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
public class RepairPojo {


    int id;
    String model, brand, problem,modifiedDate, deliveryDate,place;
    String jobNo;
    String created;
    String status;
    String price, username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }



    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String modelNo) {
        this.model = modelNo;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String date) {
        this.created = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
