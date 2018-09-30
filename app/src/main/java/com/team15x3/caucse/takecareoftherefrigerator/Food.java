package com.team15x3.caucse.takecareoftherefrigerator;

public class Food {

    private String mFoodID;
    private String mFoodName;

    private String mBacode;

    private int mExpirationDate;

    private int mCount;

    public Food() {
        //mFoodID = ;
        //mFoodName = ;

        //mBacode = ;

        // mCount = ;

        //mExpirationDate = ;
    }

    public boolean updateFoodName(String foodID, String foodName) {
        if (mFoodID == foodID) {
            mFoodName = foodName;
            return true;
        }
        return false;
    }


    public String getFoodID() {
        return mFoodID;
    }

    public String getFoodName() { return mFoodName; }

    public String getBacode() { return mBacode; }

    public int getExpirationDate() {
        return mExpirationDate;
    }
}
