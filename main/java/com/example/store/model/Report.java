package com.example.store.model;

import com.google.gson.annotations.SerializedName;

public class Report {

    @SerializedName("description")
    private String description;
    @SerializedName("Date")
    private String Date;

    public Report(String description, String date) {
        this.description = description;
        this.Date = date;
    }


    public String getDescription() {
        return description;
    }

    public String getDate() {
        return Date;
    }

}
