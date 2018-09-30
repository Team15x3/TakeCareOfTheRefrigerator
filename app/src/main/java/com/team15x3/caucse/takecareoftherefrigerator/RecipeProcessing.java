package com.team15x3.caucse.takecareoftherefrigerator;

import java.util.ArrayList;

public class RecipeProcessing {

    private ArrayList<Food> mFoodList;
    private ArrayList<Food> mRecipeList;

    private FoodProcessing mFoodProcessing;

    public RecipeProcessing(ArrayList<Food> foodList, FoodProcessing foodProcessing) {
        mFoodList = foodList;
        mFoodProcessing = foodProcessing;
    }

    public void tempRecipeAlgorithm() {

        /* find recipes ID with ingredient name in 레시피+재료정보.json */

        /* find recipe informations with recipe ID in 레시피_기본정보.json*/

        /* find recipe courses with recipe ID in 레시피_과정정보.json */
    }

    public ArrayList<Recipe> recipieAlgorithmByExpirationDate() {
        ArrayList<Recipe> recipe_list = new ArrayList<Recipe>();
        ArrayList<Food> expiration_food_list = mFoodProcessing.getFoodListNearExpirationDate();

        // search recipe from food list


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


