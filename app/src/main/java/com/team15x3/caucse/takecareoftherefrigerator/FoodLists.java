package com.team15x3.caucse.takecareoftherefrigerator;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FoodLists {
    @SerializedName("data")
    private List<FoodList> foodLists = new ArrayList<>();

    public FoodList getFood(int i){
        return foodLists.get(i);
    }

    public int getSize(){
        return foodLists.size();
    }

}
