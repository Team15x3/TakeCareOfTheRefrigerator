package com.team15x3.caucse.takecareoftherefrigerator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;

public class RecipeInfoActivity extends AppCompatActivity {
    private TextView tvIngredient,tvCooking,tvRecipeName;

    private Button btnScrape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);

        tvIngredient = (TextView)findViewById(R.id.tvIngredients);
        tvCooking = (TextView)findViewById(R.id.tvCooking);
        tvRecipeName = (TextView)findViewById(R.id.tvRecipeName);

        btnScrape = (Button)findViewById(R.id.btnScrape);

        int idx = getIntent().getIntExtra("list_number",-1);
        if(idx == -1){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            setResult(-1);
        }
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

        btnScrape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idx = getIntent().getIntExtra("list_number",-1);
                if(idx == -1) {
                    setResult(-1);
                }

                if(User.INSTANCE.getScrapeList().contains(TabRecipeFragment.recipeArrayList.get(idx))){
                    Toast.makeText(getApplicationContext(),"already exist",Toast.LENGTH_SHORT).show();
                }else{
                    User.INSTANCE.addScrape(TabRecipeFragment.recipeArrayList.get(idx));
                    Toast.makeText(getApplicationContext(),"complete!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
