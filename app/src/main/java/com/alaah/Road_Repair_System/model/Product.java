package com.alaah.Road_Repair_System.model;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("ID")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("description")
    String descrip;
    @SerializedName("img")
    String img;
    @SerializedName("date")
    String Dat;
    @SerializedName("address")
    String add;
    @SerializedName("Location")
    String loc;
    @SerializedName("assign")
    String ass;
    @SerializedName("Status")
    String stat;

    public Product(int id, String name, String img, String loc, String ass, String stat,String descrip, String add) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.loc = loc;
        this.ass = ass;
        this.stat = stat;
        this.descrip=descrip;
        this.add=add;
    }

    public Integer getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getLoc() {
        return loc;
    }

    public String getAss() {
        return ass;
    }

    public String getStat() {
        return stat;
    }

    public String getDescrip() {
        return descrip;
    }

    public String getAdd() {
        return add;
    }
}
