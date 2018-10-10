package com.team15x3.caucse.takecareoftherefrigerator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class FoodProcessing {

    private ArrayList<Food> mFoodList;

    public FoodProcessing(ArrayList<Food> foodList) {
        mFoodList = foodList;
    }

    public ArrayList<Food> getFoodListNearExpirationDate() {
        ArrayList<Food> food_list = new ArrayList<Food>();

        SimpleDateFormat simple_date_format = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        int current_time = Integer.parseInt(simple_date_format.format(new Date()));

        Iterator<Food> iterator = mFoodList.iterator();
        while (iterator.hasNext()) {
            Food food = iterator.next();

            if (food.getExpirationDate() - current_time < food.getD_Day() ) {
                food_list.add(food);
            }
        }

        return food_list;
    }
}
