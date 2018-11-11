package com.team15x3.caucse.takecareoftherefrigerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class Refrigerator {
    private String mName;

    private ArrayList<Food>   mFoodList;
    private ArrayList<Recipe> mRecipeList;

    private FoodProcessing    mFoodProcess;
    private RecipeProcessing  mRecipeProcess;

    public Refrigerator(String name) {
        mName = name;

        mFoodList     = new ArrayList<Food>();
        mRecipeList   = new ArrayList<Recipe>();

        mFoodProcess  = new FoodProcessing(mFoodList);
        mRecipeProcess = new RecipeProcessing(mFoodList, mFoodProcess);
    }

    public String getName() {
        return mName;
    }

    public void addFood(Food food) {
        mFoodList.add(food);
        this.sortFoodListByExpirationDate();
    }

    public void sortFoodListByExpirationDate() {
        AscendingExpirationDate ascending_expiration_date = new AscendingExpirationDate();
        Collections.sort(mFoodList, ascending_expiration_date);
    }

    public void setRecipieList(ArrayList<Recipe> recipieList) { mRecipeList = recipieList; }

    public ArrayList<Food> getFoodList() {
        return mFoodList;
    }

    public ArrayList<Recipe> getRecipeList() { return mRecipeList; };

    public RecipeProcessing getRecipeProcess() { return mRecipeProcess; }

    public FoodProcessing getFoodProcess() { return mFoodProcess; }


    // 같은 food에 대해서 여러개가 있을 때 어떻게 할지 결정 안 함.

    public void deleteFood(Food o1) {
        Iterator<Food> iter = this.mFoodList.iterator();
        while(iter.hasNext()) {
            Food food = iter.next();

            if (o1.getFoodID().equals(food.getFoodID())) {
                iter.remove();
            }
        }
    }
}

class AscendingExpirationDate implements Comparator<Food> {
    @Override
    public int compare(Food o1, Food o2) {
        SimpleDateFormat simple_date_format = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        try {
            Date o1_date = simple_date_format.parse(o1.getExpirationDate());
            Date o2_date = simple_date_format.parse(o2.getExpirationDate());

            if (o1_date.getTime() > o2_date.getTime()) { return 1; }
            else if (o1_date.getTime() < o2_date.getTime()) { return 1; }
            else { return 0; }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }
}

