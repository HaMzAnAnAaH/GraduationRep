package com.example.store.model;

import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @SerializedName("user")
    private User user;

    @SerializedName("data")
    private Report data;

    public Result(Boolean error, String message, User user, Report data) {
        this.error = error;
        this.message = message;
        this.user = user;
        this.data = data;
    }



    public Result(Boolean error, String message){
        this.error = error;
        this.message = message;

    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public Report getReport() { return data; }
}
