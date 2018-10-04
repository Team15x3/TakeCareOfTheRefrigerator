package com.team15x3.caucse.takecareoftherefrigerator;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Food {

    private String mFoodID;
    private String mFoodName;

    private String mBarcode;
    private String mFoodType;
    private String mThumbnailURL;

    private List<Nutrient> mMainNutrients;

    private int mExpirationDate;
    private int mCount;

    public Food(String foodID, String foodName, String barcode, String foodType, String thumbnailURL) {
        mFoodID = foodID;
        mFoodName = foodName;

        mBarcode = barcode;
        mFoodType = foodType;
        mThumbnailURL = thumbnailURL;

        // mCount = ;
        //mExpirationDate = ;
    }

    public void setFoodExpirationDate(int expirationDate) {
        mExpirationDate = expirationDate;
    }

    public void setFoodCount(int count) {
        mCount = count;
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

    public String getBarcode() { return mBarcode; }

    public int getExpirationDate() {
        return mExpirationDate;
    }
}

class Nutrient {

    private String mNutrientID;
    private String mNutrientName;

    private Number mServingAmount;
    private String mServingAmountUnit;

    private Number mRate;

    Nutrient(String nutrientID, String nutrientName, Number servingAmount,  String servingAmountUnit, Number rate) {
        mNutrientID   = nutrientID;
        mNutrientName = nutrientName;

        mServingAmount     = servingAmount;
        mServingAmountUnit = servingAmountUnit;

        mRate = rate;
    }

    public String getNutrientID() { return mNutrientID; }

    public String getNutrientName() { return mNutrientName; }

    public Number getServingAmount() { return mServingAmount; }

    public String getServingAmountUnit() { return mServingAmountUnit; }

    public Number getRate() { return mRate; }
}
