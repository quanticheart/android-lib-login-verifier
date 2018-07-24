package com.master.killercode.loginverifier.ListActivity;

import android.app.Activity;
import android.os.Bundle;

import java.io.Serializable;

public class UserModel implements Serializable {

    public static String getDataUser = "getdatauser";

    public static UserModel getData(Activity activity) {
        UserModel userModel = new UserModel();
        Bundle bundle = activity.getIntent().getExtras();
        if (bundle != null && bundle.containsKey(getDataUser)) {
            userModel = (UserModel) bundle.get(getDataUser);
        }
        return userModel;
    }

    private String id;
    private String user;
    private String userName;
    private String pass;
    private String dateLog;
    private String dateLogout;
    private String typeIntent;

    UserModel() {
        id = "";
        user = "";
        userName = "";
        pass = "";
        dateLog = "";
        dateLogout = "";
        typeIntent = "";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTypeIntent() {
        return typeIntent;
    }

    public void setTypeIntent(String typeIntent) {
        this.typeIntent = typeIntent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDateLog() {
        return dateLog;
    }

    public void setDateLog(String dateLog) {
        this.dateLog = dateLog;
    }

    public String getDateLogout() {
        return dateLogout;
    }

    public void setDateLogout(String dateLogout) {
        this.dateLogout = dateLogout;
    }

    @Override
    public String toString() {
        return getId();
    }
}
