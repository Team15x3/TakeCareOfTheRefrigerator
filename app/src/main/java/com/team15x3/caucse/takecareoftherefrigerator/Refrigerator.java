package com.team15x3.caucse.takecareoftherefrigerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class Refrigerator {
    private ArrayList<Food>  mFoodList;

    private FoodProcessing   mFoodProcess;
    private RecipeProcessing mRecipePrcess;

    public Refrigerator() {
        mFoodList     = new ArrayList<Food>();
        mFoodProcess  = new FoodProcessing();
        mRecipePrcess = new RecipeProcessing(mFoodList, mFoodProcess);
    }

    public void addFood(Food food) {
        mFoodList.add(food);
        this.sortFoodListByExpirationDate();
    }

    public void sortFoodListByExpirationDate() {
        AscendingExpirationDate ascending_expiration_date = new AscendingExpirationDate();
        Collections.sort(mFoodList, ascending_expiration_date);
    }

    public ArrayList<Food> getFoodList() {
        return mFoodList;
    }

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

        if (o1.getExpirationDate() > o2.getExpirationDate()) { return 1; }
        else if (o1.getExpirationDate() < o2.getExpirationDate()) { return -1; }
        else { return 0; }
    }
}
