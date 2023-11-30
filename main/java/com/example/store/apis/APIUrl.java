package com.example.store.apis;

public interface APIUrl {

    String BASE_URL= "http://192.168.178.154/store/retrieve.php/";
    String SIGN_IN = "http://192.168.178.154/store/checkUser.php/";

    String SIGN_UP = "http://192.168.178.154/store/InsertUser.php/";
    String UPDATE_INFO = "http://192.168.178.154/store/updateUser.php/";

    String DELETE_USER = "http://192.168.178.154/store/deleteUser.php/";

    String REPORT_INFO = "http://192.168.178.154/store/InsertInfo.php/";

    String UP = "http://192.168.178.154/store/uploadImg.php/";
}
