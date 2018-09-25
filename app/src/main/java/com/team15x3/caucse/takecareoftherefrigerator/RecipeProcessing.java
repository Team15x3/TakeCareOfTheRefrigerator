package com.team15x3.caucse.takecareoftherefrigerator;

import java.util.ArrayList;

public class RecipeProcessing {

    private ArrayList<Food> mFoodList;
    private FoodProcessing mFoodProcessing;

    public RecipeProcessing(ArrayList<Food> foodList, FoodProcessing foodProcessing) {
        mFoodList = foodList;
        mFoodProcessing = foodProcessing;
    }

    public ArrayList<Recipe> recipieAlgorithmByExpirationDate() {
        ArrayList<Recipe> recipe_list = new ArrayList<Recipe>();
        ArrayList<Food> expiration_food_list = mFoodProcessing.getFoodListNearExpirationDate();



        return recipe_list;
    }

    public ArrayList<Recipe> recipeAlgorithmByRecommendation() {
        ArrayList<Recipe> recipe_list = new ArrayList<Recipe>();

        return recipe_list;
    }

    public ArrayList<Recipe> recipeAlgorithmBy() {
        ArrayList<Recipe> recipe_list = new ArrayList<Recipe>();

        return recipe_list;
    }
}


