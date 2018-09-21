package com.team15x3.caucse.takecareoftherefrigerator;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FoodLists {
    @SerializedName("data")
    private List<FoodList> foodLists;

    public List<FoodList> getFoodList(){
        return this.foodLists;
    }

    public int getSize(){
        return foodLists.size();
    }

    public void setFoodList(FoodLists f){
        this.foodLists = f.foodLists;

    }
}
