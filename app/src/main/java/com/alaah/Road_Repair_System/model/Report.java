package com.alaah.Road_Repair_System.model;

import com.google.gson.annotations.SerializedName;

public class Report {

    @SerializedName("description")
    String description;
    @SerializedName("Date")
    String Date;

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
