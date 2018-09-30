package com.team15x3.caucse.takecareoftherefrigerator;

import java.util.ArrayList;

public class User {

    private String mID;
    private String mPassword;

    private ArrayList<Refrigerator> mRefrigeratorList;

    public User() {
       // mID = ;
        //mPassword = ;

       // mRefrigeratorList = new ArrayList<Refrigerator>();
    }

    public String getID() { return mID; }

    public String getPssword() { return mPssword; }

    public ArrayList<Refrigerator> getRefrigeratorList() { return mRefrigeratorList; }
}
