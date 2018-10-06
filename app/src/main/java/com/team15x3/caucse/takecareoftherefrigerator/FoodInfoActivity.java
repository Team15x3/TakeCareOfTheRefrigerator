package com.team15x3.caucse.takecareoftherefrigerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FoodInfoActivity extends AppCompatActivity implements View.OnClickListener{
    Food food;
    Button btnBack, btnDelete, btnRevise;
    public static final int LIST_CHANGED = 220;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);
        //getWindow().setStatusBarColor(getResources().getColor(R.color.beige));

        food = (Food)getIntent().getSerializableExtra("food");
        SetInformationOfFood(food);

        btnBack = (Button)findViewById(R.id.btnBack);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnRevise = (Button)findViewById(R.id.btnRevise);

        btnBack.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnRevise.setOnClickListener(this);



    }


    private void SetInformationOfFood(Food food){




    }

    @Override
    public void onClick(View view) {
        if(view == btnBack){
            finish();
        }
        if(view == btnDelete){
            ArrayList<Food> foodlist= User.INSTANCE.getRefrigeratorList().get(User.INSTANCE.getCurrentRefrigerator()).getFoodList();
            Intent intent = getIntent();
            int num = intent.getIntExtra("list_number",-1);
            Log.d("index number",num+"");
            foodlist.remove(num);
            setResult(LIST_CHANGED,intent);
            finish();
        }
    }
}
