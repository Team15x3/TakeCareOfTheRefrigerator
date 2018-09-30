package com.team15x3.caucse.takecareoftherefrigerator;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;

public class Recipe {

    private int mRecipeID;
    private String mRecipeName;

    private String mTypeID;
    private String mTypeName;

    private int mCookingTime;

    private String mDifficulty;

    private String mImageURL;

    private ArrayList<Cooking> mCookingCourseList;
    private ArrayList<Ingredient> mIngredientList;

    public Recipe() {
        //mRecipeID =
        //mRecipeName =
        mIngredientList = new ArrayList<Ingredient>();
    }

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

    public int getRecipeID() {
        return mRecipeID;
    }

    public String getRecpieName() {
        return mRecipeName;
    }

    public ArrayList<Cooking> getCookingCourseList() {
        return mCookingCourseList;
    }

    public ArrayList<Ingredient> getIngredientList() {
        return mIngredientList;
    }
}

class Ingredient implements Comparable<Ingredient> {

    private String mRecipeID;
    private int    mOrderNumber;
    private String mIngredientName;
    private double mCapacity;

    private String mTypeID;
    private String mTypeName;

    public Ingredient() {
        //mRecipeID = ;
        //mIngredientName = ;
        //mCapacity = ;
        //mOrderNumber =;

        //mTypeID = ;
        //mTypeName = ;
    }

    public void searchIngredient(String ingredientName) {

    }

    public String getmRecipeID() {
        return mRecipeID;
    }

    public int getOrderNumber() {
        return mOrderNumber;
    }

    public String getIngredientName() {
        return mIngredientName;
    }

    public double getCapacity() {
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

        if (this.getOrderNumber() > o.getOrderNumber()) { return 1; }
        else if (this.getOrderNumber() < o.getOrderNumber()) { return -1; }
        else { return 0; }
    }
}

class Cooking implements Comparable<Cooking> {

    private String mRecipeID;
    private int    mOrderNumber;
    private String mCookingCourse;
    private String mImageURL;

    public Cooking() {
        //mRecipeID = ;
        //mOrderNumber = ;
        //mCookingCourse = ;
        //mImageURL = ;
    }

    public String getRecipeID() {
        return mRecipeID;
    }

    public int getOrderNumber() {
        return mOrderNumber;
    }

    public String getCookingCourse() {
        return mCookingCourse;
    }

    public String getImageURL() {
        return mImageURL;
    }

    @Override
    public int compareTo(@NonNull Cooking o) {

        if (this.getOrderNumber() > o.getOrderNumber()) { return 1; }
        else if (this.getOrderNumber() < o.getOrderNumber()) { return -1; }
        else { return 0; }
    }
}