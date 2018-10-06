package com.team15x3.caucse.takecareoftherefrigerator;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Food implements Serializable{

    private String mFoodID;
    private String mFoodName;
    private String mBacode;
    private int volume; //중량
    private String volumeUnit;
    private String foodType;
    private String picture;
    private ArrayList<foodMaterials> materials; //원재료
    private List<AllergyIngredient> allergy;
    private int mExpirationDate;
    private int mCount;
    private int servingAmount;//총 제공량
    private String servingAmountUnit; //제공량 단위
    private ArrayList<Nutrients> nutrients;

    public Food() {
        //mFoodID = ;
        //mFoodName = ;

        //mBacode = ;

        // mCount = ;

        //mExpirationDate = ;
    }

    public Food(String mFoodName, String picture, int mCount, int mExpirationDate){
        materials = new ArrayList<foodMaterials>();
        allergy = new ArrayList<AllergyIngredient>();
        nutrients = new ArrayList<Nutrients>();
        this.mFoodName = mFoodName;
        this.picture  = picture;
        this.mCount = mCount;
        this.mExpirationDate = mExpirationDate;
    }

    public void setNutrients(ArrayList<Nutrients> nutrients) {
        this.nutrients = nutrients;
    }

    public void setAllergy(List<AllergyIngredient> allergy) {
        this.allergy = allergy;
    }

    public void setServingAmount(int servingAmount) {
        this.servingAmount = servingAmount;
    }

    public void setServingAmountUnit(String servingAmountUnit) {
        this.servingAmountUnit = servingAmountUnit;
    }

    public void setVolumeUnit(String volumeUnit) {
        this.volumeUnit = volumeUnit;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public void setMaterials(ArrayList<foodMaterials> materials) {
        this.materials = materials;
    }

    public void setmBacode(String mBacode) {
        this.mBacode = mBacode;
    }

    public void setmCount(int mCount) {
        this.mCount = mCount;
    }

    public void setmExpirationDate(int mExpirationDate) {
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

    public String getServingAmountUnit() {
        return servingAmountUnit;
    }

    public ArrayList<Nutrients> getNutrients() {
        return nutrients;
    }

    public int getServingAmount() {
        return servingAmount;
    }

    public int getExpirationDate() {
        return mExpirationDate;
    }

    public ArrayList<foodMaterials> getMaterials() {
        return materials;
    }
    public List<AllergyIngredient> getAllergy() {
        return allergy;
    }
}



class foodMaterials{

    private String materialsStructure; //원재료 구조식
    private String materialName;    //원재료 이름

    public String getMaterialName() {
        return materialName;
    }

    public String getMaterialsStructure() {
        return materialsStructure;
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

class Nutrients{
    private int servingAmount;
    private String servingAmountUnit;
    private int rate;
    private String nutrientName;
    private String nutrientId;

    public int getServingAmount() {
        return servingAmount;
    }

    public String getServingAmountUnit() {
        return servingAmountUnit;
    }

    public String getNutrientName() {
        return nutrientName;
    }

    public String getNutrientId() {
        return nutrientId;
    }

    public int getRate() {
        return rate;
    }

    public void setServingAmountUnit(String servingAmountUnit) {
        this.servingAmountUnit = servingAmountUnit;
    }

    public void setServingAmount(int servingAmount) {
        this.servingAmount = servingAmount;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setNutrientName(String nutrientName) {
        this.nutrientName = nutrientName;
    }

    public void setNutrientId(String nutrientId) {
        this.nutrientId = nutrientId;
    }

}