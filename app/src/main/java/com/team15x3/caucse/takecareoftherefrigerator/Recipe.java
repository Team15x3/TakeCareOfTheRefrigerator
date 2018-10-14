package com.team15x3.caucse.takecareoftherefrigerator;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;

public class Recipe {

    private String mRecipeID;
    private String mRecipeName;

    private String mQuantity;
    private String mCookingTime;
    private String mDifficulty;
    private String mImageURL;
    private String mSummary;
    private String mCalorie;

    private ArrayList<Cooking> mCookingCourseList = new ArrayList<Cooking>();
    private ArrayList<Ingredient> mIngredientList = new ArrayList<Ingredient>();

    public Recipe() {}

    public String getImageURL() {
        return mImageURL;
    }

    public void setRecipeID(String recipeID) { mRecipeID = recipeID; }

    public void setRecipeName(String recipeName) { mRecipeName = recipeName; }

    public void setSummary(String summary) { mSummary = summary; }

    public void setCookingTime(String cookingTime) { mCookingTime = cookingTime; }

    public void setQuantity(String quantity) { mQuantity = quantity; }

    public void setDifficulty(String difficulty) { mDifficulty = difficulty; }

    public void setImageURL(String imageURL) { mImageURL = imageURL; }

    public void addCookingCourse(Cooking cooking) {
        mCookingCourseList.add(cooking);
        this.sortCookingList();
    }

    public void addIngredient(Ingredient ingredient) {
        mIngredientList.add(ingredient);
        this.sortIngredientList();
    }

    public void sortCookingList() {
        Collections.sort(mCookingCourseList);
    }

    public void sortIngredientList() {
        Collections.sort(mIngredientList);
    }

    public void searchRecipe(String ingredientName) {

    }

    public String getRecipeID() {
        return mRecipeID;
    }

    public String getRecpieName() {
        return mRecipeName;
    }

    public ArrayList<Cooking> getCookingCourseList() { return mCookingCourseList; }

    public void setIngredientList(ArrayList<Ingredient> ingredientList) { mIngredientList = ingredientList; }

    public ArrayList<Ingredient> getIngredientList() { return mIngredientList; }
}

class Ingredient implements Comparable<Ingredient> {

    private String mRecipeID;
    private String mOrderNumber;
    private String mIngredientName;
    private String mCapacity;

    private String mTypeID;
    private String mTypeName;

    public Ingredient(String recipeID, String ingredient_name, String ingredient_volume, String ingredient_order_number, String ingredient_type_name, String ingredient_type_code) {
        mRecipeID = recipeID;
        mIngredientName = ingredient_name;
        mCapacity = ingredient_volume;
        mOrderNumber = ingredient_order_number;

        mTypeID = ingredient_type_code;
        mTypeName = ingredient_type_name;
    }

    public void searchIngredient(String ingredientName) {

    }

    public String getmRecipeID() {
        return mRecipeID;
    }

    public String getOrderNumber() {
        return mOrderNumber;
    }

    public String getIngredientName() {
        return mIngredientName;
    }

    public String getCapacity() {
        return mCapacity;
    }

    public String getTypeID() {
        return mTypeID;
    }

    public String getTypeName() {
        return mTypeName;
    }

    @Override
    public int compareTo(@NonNull Ingredient o) {

        return 1;

       /* if (this.getOrderNumber() > o.getOrderNumber()) { return 1; }
        else if (this.getOrderNumber() < o.getOrderNumber()) { return -1; }
        else { return 0; }*/
    }
}

class Cooking implements Comparable<Cooking> {

    private String mRecipeID;
    private String mOrderNumber;
    private String mCookingCourse;
    private String mImageURL;
    private String mTip;

    public Cooking(String recipe_id, String cooking_no, String cooking_dc, String image_url, String tip) {
        mRecipeID = recipe_id;
        mOrderNumber = cooking_no;
        mCookingCourse = cooking_dc;
        mImageURL = image_url;
        mTip = tip;
    }

    public String getRecipeID() {
        return mRecipeID;
    }

    public String getOrderNumber() {
        return mOrderNumber;
    }

    public String getCookingCourse() {
        return mCookingCourse;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public String getTip() { return mTip; }

    @Override
    public int compareTo(@NonNull Cooking o) {

        return 1;


       /* if (this.getOrderNumber() > o.getOrderNumber()) { return 1; }
        else if (this.getOrderNumber() < o.getOrderNumber()) { return -1; }
        else { return 0; }*/
    }
}