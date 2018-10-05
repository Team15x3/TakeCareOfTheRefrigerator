package com.team15x3.caucse.takecareoftherefrigerator;

import java.io.Serializable;
import java.util.List;

public class Food implements Serializable{

    private String mFoodID;
    private String mFoodName;
    private String mBacode;
    private int volume; //중량
    private String volumeUnit;
    private String foodType;
    private String picture;
    private List<AllergyIngredient> allergy;
    private int mExpirationDate;
    private int mCount;

    public Food() {
        //mFoodID = ;
        //mFoodName = ;

        //mBacode = ;

        // mCount = ;

        //mExpirationDate = ;
    }

    public Food(String mFoodName, String picture, int mCount, int mExpirationDate){
        this.mFoodName = mFoodName;
        this.picture  = picture;
        this.mCount = mCount;
        this.mExpirationDate = mExpirationDate;
    }

    public String getPicture() {
        return picture;
    }

    public boolean updateFoodName(String foodID, String foodName) {
        if (mFoodID == foodID) {
            mFoodName = foodName;
            return true;
        }
        return false;
    }

    public int getmCount() {
        return mCount;
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


class AllergyIngredient{
    private int materialId;
    private String materialName;

    public int getMaterialId() {
        return materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }
}
