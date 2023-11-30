package com.example.store.model;

import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("name")
    String name;
    @SerializedName("price")
    String price;
    @SerializedName("img")
    String img;

    @SerializedName("address")
    String address;

    @SerializedName("Time")
    String Time;

    @SerializedName("action")
    String action;



    public Product(String name, String price, String img, String address, String Time, String action) {
        this.name = name;
        this.price = price;
        this.img = img;
        this.address = address;
        this.Time = Time;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getAddress() {
        return address;
    }

    public String getTime() {
        return Time;
    }

    public String getImg() {
        return img;
    }

    public String getAction() {
        return action;
    }
}

