package com.team15x3.caucse.takecareoftherefrigerator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Food implements Serializable {

    @SerializedName("foodId")
    @Expose
    private String mFoodID;

    @SerializedName("foodName")
    @Expose
    private String mFoodName;

    @SerializedName("volume")
    @Expose
    private int mVolume;

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
    private int mainNutrientServingMeasureAmount;

    @SerializedName("mainNutrientServingMeasureUnit")
    @Expose
    private String mainNutrientServingMeasureUnit;

    @SerializedName("mainNutrients")
    @Expose
    private ArrayList<Nutrient> mMainNutrients = new ArrayList<Nutrient>();

    @SerializedName("allergyIngredient")
    @Expose
    private ArrayList<Allergy> mAllergyList = new ArrayList<Allergy>();

    @SerializedName("foodMaterials")
    @Expose
    private ArrayList<Material> mMaterialList = new ArrayList<Material>();

    @SerializedName("foodClassifyName")
    @Expose
    private String mFoodClassifyName;

    private int mD_Day; /* d-day variable for expiration date */

    private String mSellByDate; /* sell by date */

    private String mUseByDate;

    private int mCount;

    private boolean IsUriFromGallery;

    public Food() {}

    public Food(String foodID, String foodName) {
        mFoodID = foodID;
        mFoodName = foodName;

        mD_Day = 7; /* default value */
    }

    public void setIsFromGallery( boolean f){IsUriFromGallery = f;}
    public boolean getIsFromGallery(){return IsUriFromGallery;}

    public void setFoodID(String foodId) { mFoodID = foodId; }

    public String getFoodID() { return mFoodID; }

    public void setVolumeUnit(String volumeUnit) { mVolumeUnit = volumeUnit; }

    public String getVolumeUnit() { return mVolumeUnit; }

    public void setRegisterDate(String registerDate) { mRegisterDate = registerDate; }

    public String getRegisterDate() { return mRegisterDate; }

    public String getThumbnailUrl() { return mThumbnailUrl; }

    public void setThumbnailUrl(String thumbnailUrl) { mThumbnailUrl = thumbnailUrl; }

    public void setMainNutrientServingMeasureAmount(int mainNutrientServingMeasureAmount) { this.mainNutrientServingMeasureAmount = mainNutrientServingMeasureAmount; }

    public int getMainNutrientServingMeasureAmount() {
        try {
            return mainNutrientServingMeasureAmount; //.intValue();
        } catch(NumberFormatException e) {
            e.printStackTrace();
            return 0;
        } catch(NullPointerException e){
            e.printStackTrace();
            return 0;
        }
    }

    public String getMainNutrientServingMeasureUnit() { return mainNutrientServingMeasureUnit; }

    public void setMainNutrientServingMeasureUnit(String mainNutrientServingMeasureUnit) { this.mainNutrientServingMeasureUnit = mainNutrientServingMeasureUnit; }

    public int getVolume() {
        try {
            return mVolume; //.intValue();
        } catch(NumberFormatException e) {
            e.printStackTrace();
            return 0;
        } catch(NullPointerException e){
            e.printStackTrace();
            return 0;
        }
    }

    public void setVolume(int volume) { mVolume = volume; }

    public String getFoodType() { return mFoodType; }

    public void setFoodType(String foodType) { mFoodType = foodType; }

    public String getBarcode() { return mBarcode; }

    public void setBarcode(String barcode) { mBarcode = barcode; }

    public ArrayList<Nutrient> getMainNutrientsList() { return mMainNutrients; }

    public void setMainNutrientsList(ArrayList<Nutrient> mainNutrients) { mMainNutrients = mainNutrients; }

    public String getVendors() { return mVendors; }

    public void setVendors(String vendors) { mVendors = vendors; }

    public String getFoodName() { return mFoodName; }

    public void setFoodName(String foodName) { mFoodName = foodName; }

    public ArrayList<Allergy> getAllergyList() { return mAllergyList; }

    public void setAllergyList(ArrayList<Allergy> allergyList) { mAllergyList = allergyList;}

    public ArrayList<Material> getMaterialList() { return mMaterialList; }

    public void setMaterialList(ArrayList<Material> materialList) { mMaterialList = materialList;}

    public int getCount() { return mCount; }

    public void setCount(int count) { mCount = count; }

    public String getSellByDate() { return mSellByDate; }

    public void setSellByDate(String expirationDate) { mSellByDate = expirationDate; }

    public String getUseByDate(){return mUseByDate;}

    public void setUseByDate(String use){mUseByDate = use;}

    public int getD_Day() { return mD_Day; }

    public void setD_Day(int d_day) { mD_Day = d_day; }

    public String getFoodClassifyName() { return mFoodClassifyName; }

    public void setFoodClassifyName(String foodClassifyName) { mFoodClassifyName = foodClassifyName; }



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
    private int rate;

    @SerializedName("servingAmountUnit")
    @Expose
    private String servingAmountUnit;

    @SerializedName("servingAmount")
    @Expose
    private int servingAmount = 0;

    public Nutrient(String nutrientID, String nutrientName, int rate, String servingAmountUnit, int servingAmount) {
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

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setServingAmount(int servingAmount) {
        this.servingAmount = servingAmount;
    }

    public String getNutrientID() {
        return nutrientID;
    }

    public void setServingAmountUnit(String servingAmountUnit) { this.servingAmountUnit = servingAmountUnit; }

    public String getNutrientName() {
        return nutrientName;
    }

    public int getRate() {
        try {
            return rate; // .intValue();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        } catch(NullPointerException e){
            e.printStackTrace();
            return 0;
        }
    }

    public String getServingAmountUnit() { return servingAmountUnit; }

    public int getServingAmount() {
        try {
            return servingAmount; // .intValue();
        } catch(NumberFormatException e) {
            e.printStackTrace();
            return 0;
        } catch(NullPointerException e){
            e.printStackTrace();
            return 0;
        }
    }
}

class Allergy {

    @SerializedName("materialId")
    @Expose
    private int mMaterialID;

    @SerializedName("materialName")
    @Expose
    private String mMaterialName;

    public void setMaterialID(int materialID) { mMaterialID = materialID; }

    public int getMaterialID() {
        try {
            return mMaterialID; //.intValue();
        } catch(NumberFormatException e) {
            e.printStackTrace();
            return 0;
        } catch(NullPointerException e){
            e.printStackTrace();
            return 0;
        }
    }

    public void setMaterialName(String materialName) { mMaterialName = materialName; }

    public String getMaterialName() { return mMaterialName; }
}

class Material {

    @SerializedName("materialStructure")
    @Expose
    private String mMaterialStructure;

    @SerializedName("materialName")
    @Expose
    private String mMaterialName;

    public void setMaterialID(String materialStructure) { mMaterialStructure = materialStructure; }

    public String getMaterialID() { return mMaterialStructure; }

    public void setMaterialName(String materialName) { mMaterialName = materialName; }

    public String getMaterialName() { return mMaterialName; }
}

