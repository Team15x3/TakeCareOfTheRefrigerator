package com.team15x3.caucse.takecareoftherefrigerator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FoodInfoActivity extends AppCompatActivity {
    Food food;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);
        //getWindow().setStatusBarColor(getResources().getColor(R.color.beige));

        food = (Food)getIntent().getSerializableExtra("food");
        SetInformationOfFood(food);


    }


    private void SetInformationOfFood(Food food){




    }
}
