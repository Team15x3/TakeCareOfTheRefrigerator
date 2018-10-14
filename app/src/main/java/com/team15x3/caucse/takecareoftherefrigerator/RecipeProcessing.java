package com.team15x3.caucse.takecareoftherefrigerator;

import android.os.AsyncTask;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class RecipeProcessing extends AsyncTask<Void, Void, ArrayList<Recipe>> {

    private ArrayList<Food> mFoodList;
    private ArrayList<Food> mRecipeList;

    private FoodProcessing mFoodProcessing;

    public RecipeProcessing(ArrayList<Food> foodList, FoodProcessing foodProcessing) {
        mFoodList = foodList;
        mFoodProcessing = foodProcessing;
    }

    public FoodProcessing getFoodProcessing() { return mFoodProcessing; }

    public void tempRecipeAlgorithm() {

        /* find recipes ID with ingredient name in 레시피+재료정보.json */

        /* find recipe information with recipe ID in 레시피_기본정보.json*/

        /* find recipe courses with recipe ID in 레시피_과정정보.json */
    }

    public ArrayList<Recipe> recipeAlgorithmByExpirationDate() {
        ArrayList<Recipe> recipe_list = new ArrayList<Recipe>();
        ArrayList<Food> expiration_food_list = mFoodProcessing.getFoodListNearExpirationDate();

        for (int i = 0; i < expiration_food_list.size(); i++) {
            expiration_food_list.get(i).setFoodName("쌀");

            Recipe recipe = new Recipe();
            recipe.setRecipeID("A");
            recipe_list.add(recipe);
        }

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

    @Override
    protected ArrayList<Recipe> doInBackground(Void... voids) {
        return recipeAlgorithmByExpirationDate();
    }
}


