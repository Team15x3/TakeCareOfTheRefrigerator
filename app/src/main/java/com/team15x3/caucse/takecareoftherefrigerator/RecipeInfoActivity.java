package com.team15x3.caucse.takecareoftherefrigerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;

public class RecipeInfoActivity extends AppCompatActivity {
    private TextView tvIngredient,tvCooking,tvRecipeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);

        tvIngredient = (TextView)findViewById(R.id.tvIngredients);
        tvCooking = (TextView)findViewById(R.id.tvCooking);
        tvRecipeName = (TextView)findViewById(R.id.tvRecipeName);

        int idx = getIntent().getIntExtra("list_number",-1);
        Recipe curRecipe = TabRecipeFragment.recipeArrayList.get(idx);

        if(curRecipe.getRecpieName() != null) {
            tvRecipeName.setText(curRecipe.getRecpieName());
        }
        Iterator<Ingredient> ingredientIterator= curRecipe.getIngredientList().iterator();
        while(ingredientIterator.hasNext()){
            Ingredient curIngredient = ingredientIterator.next();
            tvIngredient.append(curIngredient.getIngredientName()+",");
        }

        Iterator<Cooking> cookingIterator = curRecipe.getCookingCourseList().iterator();
        while(cookingIterator.hasNext()){
            Cooking curCooking = cookingIterator.next();
            tvCooking.append(curCooking.getCookingCourse()+"\n\n");
        }
    }
}
