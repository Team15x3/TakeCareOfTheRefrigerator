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

    public Food(String foodID, String foodName) {
        mFoodID = foodID;
        mFoodName = foodName;
    }

    public void setFoodID(String foodId) { mFoodID = foodId; }

    public String getFoodID() { return mFoodID; }

    public void setVolumeUnit(String volumeUnit) { mVolumeUnit = volumeUnit; }

    public String getVolumeUnit() { return mVolumeUnit; }

    public void setRegisterDate(String registerDate) { mRegisterDate = registerDate; }

    public String getRegisterDate() { return mRegisterDate; }

    public Object getThumbnailUrl() { return mThumbnailUrl; }

    public void setThumbnailUrl(String thumbnailUrl) { mThumbnailUrl = thumbnailUrl; }

    public void setMainNutrientServingMeasureAmount(Number mainNutrientServingMeasureAmount) { this.mainNutrientServingMeasureAmount = mainNutrientServingMeasureAmount; }

    public Object getMainNutrientServingMeasureAmount() { return mainNutrientServingMeasureAmount; }

    public Object getMainNutrientServingMeasureUnit() { return mainNutrientServingMeasureUnit; }

    public void setMainNutrientServingMeasureUnit(String mainNutrientServingMeasureUnit) { this.mainNutrientServingMeasureUnit = mainNutrientServingMeasureUnit; }

    public Number getVolume() { return mVolume; }

    public void setVolume(Number volume) { mVolume = volume; }

    public String getFoodType() { return mFoodType; }

    public void setFoodType(String foodType) { mFoodType = foodType; }

    public String getBarcode() { return mBarcode; }

    public void setBarcode(String barcode) { mBarcode = barcode; }

    public Object getMainNutrients() { return mMainNutrients; }

    public void setMainNutrients(ArrayList<Nutrient> mainNutrients) { mMainNutrients = mainNutrients; }

    public String getVendors() { return mVendors; }

    public void setVendors(String vendors) { mVendors = vendors; }

    public String getFoodName() { return mFoodName; }

    public void setFoodName(String foodName) { mFoodName = foodName; }
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

    public void setNutrientName(String nutrientName) {
        this.nutrientName = nutrientName;
    }

    public void setRate(Number rate) {
        this.rate = rate;
    }

    public void setServingAmount(Number servingAmount) {
        this.servingAmount = servingAmount;
    }

    public String getNutrientID() {
        return nutrientID;
    }

    public void setServingAmountUnit(String servingAmountUnit) {
        this.servingAmountUnit = servingAmountUnit;
    }

    public String getNutrientName() {
        return nutrientName;
    }

    public Number getRate() {
        return rate;
    }

    public String getServingAmountUnit() {
        return servingAmountUnit;
    }

    public Number getServingAmount() {
        return servingAmount;
    }
}