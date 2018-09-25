package com.team15x3.caucse.takecareoftherefrigerator;


public class Food {

    private String mFoodID;
    private String mFoodName;

    private String mBacode;

    private int mExpirationDate;

    public Food() {

    }

    public String getFoodID() {
        return mFoodID;
    }

    public int getExpirationDate() {
        return mExpirationDate;
    }
}
