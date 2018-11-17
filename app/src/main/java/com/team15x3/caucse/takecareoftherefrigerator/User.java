package com.team15x3.caucse.takecareoftherefrigerator;

import java.util.ArrayList;

public class User {

    private String mID;
    private String mPassword;
    private ArrayList<Refrigerator> mRefrigeratorList;

    public String UserName;
    public String profileImageUrl;
    public String uid;
    public String pushToken;
    public String comment;

    private int defaultRefrigerator;
    private int currentRefrigerator;

    private ArrayList<Recipe> scrapeList;

    //To make only one instance, I define private constructor
    public static final User INSTANCE = new User();

    public User() {
        mRefrigeratorList = new ArrayList<Refrigerator>();
        defaultRefrigerator = 0;
        currentRefrigerator = defaultRefrigerator;

        scrapeList = new ArrayList<Recipe>();

        mID = null;
        mPassword = null;
    }


    public int getCurrentRefrigerator() {
        return currentRefrigerator;
    }

    public void setCurrentRefrigerator(int currentRefrigerator) {
        this.currentRefrigerator = currentRefrigerator;
    }

    public void setDefaultRefrigerator(int defaultRefrigerator) {
        this.defaultRefrigerator = defaultRefrigerator;
    }

    public void addScrape(Recipe recipe) {
        scrapeList.add(recipe);
    }

    public String getID() { return mID; }

    public String getPssword() { return mPassword; }

    public ArrayList<Refrigerator> getRefrigeratorList() { return mRefrigeratorList; }

    public void addRefrigerator(Refrigerator refri) {
        mRefrigeratorList.add(refri);
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public void setmRefrigeratorList(ArrayList<Refrigerator> mRefrigeratorList) {
        this.mRefrigeratorList = mRefrigeratorList;
    }

    public int getDefaultRefrigerator() {
        return defaultRefrigerator;
    }
}
