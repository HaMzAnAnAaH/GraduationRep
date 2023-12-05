package com.alaah.Road_Repair_System.model;

public class User {

    private String id;
    private String name;
    private String password;
    private String phone;

    public User(String id,String name, String password, String phone) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    public String getId(){return id;}

    public String getName() {return name;}

    public String getPassword() {return password;}

    public String getPhone() {return phone;}


}
