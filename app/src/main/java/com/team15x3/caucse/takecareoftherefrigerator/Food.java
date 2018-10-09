package com.team15x3.caucse.takecareoftherefrigerator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Food {

    @SerializedName("foodId")
    @Expose
    private String mFoodID;

    @SerializedName("foodName")
    @Expose
    private String mFoodName;

    @SerializedName("volume")
    @Expose
    private Number mVolume;

    @SerializedName("volumeUnit")
    @Expose
    private String mVolumeUnit;

    @SerializedName("foodType")
    @Expose
    private String mFoodType;

    @SerializedName("barcode")
    @Expose
    private String mBarcode;

    @SerializedName("thumbnailUrl")
    @Expose
    private String mThumbnailUrl;

    @SerializedName("registerDate")
    @Expose
    private String mRegisterDate;

    @SerializedName("specificNutrientIncludeYn")
    @Expose
    private String mSpecificNutrientIncludeYN;

    @SerializedName("vendors")
    @Expose
    private String mVendors;

    @SerializedName("mainNutrientServingMeasureAmount")
    @Expose
    private Number mainNutrientServingMeasureAmount;

    @SerializedName("mainNutrientServingMeasureUnit")
    @Expose
    private String mainNutrientServingMeasureUnit;

    @SerializedName("mainNutrients")
    @Expose
    private ArrayList<Nutrient> mMainNutrients = new ArrayList<Nutrient>();

    private int mD_Day; /* d-day variable for expiration date */

    private int mExpirationDate;

    private int mCount;

    public Food(String foodID, String foodName) {
        mFoodID = foodID;
        mFoodName = foodName;

        mD_Day = 7; /* default value */
    }

    public void setFoodID(String foodId) { mFoodID = foodId; }

    public String getFoodID() { return mFoodID; }

    public void setVolumeUnit(String volumeUnit) { mVolumeUnit = volumeUnit; }

    public String getVolumeUnit() { return mVolumeUnit; }

    public void setRegisterDate(String registerDate) { mRegisterDate = registerDate; }

    public String getRegisterDate() { return mRegisterDate; }

    public void setThumbnailUrl(String thumbnailUrl) { mThumbnailUrl = thumbnailUrl; }

    public String getThumbnailUrl() { return mThumbnailUrl; }

    public void setMainNutrientServingMeasureAmount(Number mainNutrientServingMeasureAmount) { this.mainNutrientServingMeasureAmount = mainNutrientServingMeasureAmount; }

    public int getMainNutrientServingMeasureAmount() { return mainNutrientServingMeasureAmount.intValue(); }

    public void setMainNutrientServingMeasureUnit(String mainNutrientServingMeasureUnit) { this.mainNutrientServingMeasureUnit = mainNutrientServingMeasureUnit; }

    public String getMainNutrientServingMeasureUnit() { return mainNutrientServingMeasureUnit; }

    public void setVolume(Number volume) { mVolume = volume; }

    public int getVolume() { return mVolume.intValue(); }

    public void setFoodType(String foodType) { mFoodType = foodType; }

    public String getFoodType() { return mFoodType; }

    public void setBarcode(String barcode) { mBarcode = barcode; }

    public String getBarcode() { return mBarcode; }

    public void setMainNutrients(ArrayList<Nutrient> mainNutrients) { mMainNutrients = mainNutrients; }

    public ArrayList<Nutrient> getMainNutrients() { return mMainNutrients; }

    public void setVendors(String vendors) { mVendors = vendors; }

    public String getVendors() { return mVendors; }

    public void setFoodName(String foodName) { mFoodName = foodName; }

    public String getFoodName() { return mFoodName; }

    public void setCount(int count) { mCount = count; }

    public int getCount() { return mCount; }

    public void setExpirationDate(int expirationDate) { mExpirationDate = expirationDate; }

    public int getExpirationDate() { return mExpirationDate; }

    public void setD_Day(int d_day) { mD_Day = d_day; }

    public int getD_Day() { return mD_Day; }
}

class Nutrient {

    @SerializedName("nutrientId")
    @Expose
    private String nutrientID;

    @SerializedName("nutrientName")
    @Expose
    private String nutrientName;

    @SerializedName("rate")
    @Expose
    private Number rate;

    @SerializedName("servingAmountUnit")
    @Expose
    private String servingAmountUnit;

    @SerializedName("servingAmount")
    @Expose
    private Number servingAmount;

    public Nutrient(String nutrientID, String nutrientName, Number rate, String servingAmountUnit, Number servingAmount) {
        this.nutrientID = nutrientID;
        this.nutrientName = nutrientName;
        this.rate = rate;
        this.servingAmountUnit = servingAmountUnit;
        this.servingAmount = servingAmount;
    }

    public void setNutrientID(String nutrientID) {
        this.nutrientID = nutrientID;
    }

    public String getNutrientID() {
        return nutrientID;
    }

    public void setNutrientName(String nutrientName) {
        this.nutrientName = nutrientName;
    }

    public String getNutrientName() {
        return nutrientName;
    }

    public void setRate(Number rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate.intValue();
    }

    public void setServingAmount(Number servingAmount) {
        this.servingAmount = servingAmount;
    }

    public String getServingAmountUnit() {
        return servingAmountUnit;
    }

    public void setServingAmountUnit(String servingAmountUnit) { this.servingAmountUnit = servingAmountUnit; }

    public int getServingAmount() {
        return servingAmount.intValue();
    }
}