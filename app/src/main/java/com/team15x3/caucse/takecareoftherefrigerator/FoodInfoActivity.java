package com.team15x3.caucse.takecareoftherefrigerator;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FoodInfoActivity extends AppCompatActivity implements View.OnClickListener{
    private Food food;
    private Button btnBack, btnDelete, btnRevise;
    private TextView tvFoodName,tvCountFood,tvExpirationDate,tvIngredients,tvAllergyIngredient,tvNutrientServing;
    private ImageView ivFoodImage;
    private TableLayout table;
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
        table = findViewById(R.id.table);

        btnBack.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnRevise.setOnClickListener(this);



    }


    private void SetInformationOfFood(Food food){
        tvFoodName = findViewById(R.id.tvName);
        tvCountFood = findViewById(R.id.tvCountFood);
        tvExpirationDate = findViewById(R.id.tvExpirationDate);
        tvIngredients = findViewById(R.id.tvIngredients);
        tvAllergyIngredient = findViewById(R.id.tvAllergyIngredient);
        tvNutrientServing = findViewById(R.id.tvNutrientServing);

        //tvFoodName.setText(food.getFoodName());
        /*tvCountFood.setText("Quantity : "+food.getmCount());
        tvExpirationDate.setText("Expiration date : "+food.getExpirationDate()) ;

        if(food.getMaterials().size() != 0){
            for(int i = 0; i<food.getMaterials().size()-1;i++){
                tvIngredients.append(food.getMaterials().get(i).getMaterialName() +", ");
            }
            tvIngredients.append(food.getMaterials().get(food.getMaterials().size()-1).getMaterialName());
        }

        if(food.getAllergy().size()!=0){
            for(int i = 0; i<food.getAllergy().size()-1; i++){
                tvAllergyIngredient.append(food.getAllergy().get(i).getMaterialName()+", ");
            }
            tvAllergyIngredient.append(food.getAllergy().get(food.getAllergy().size()-1).getMaterialName());
        }
        tvNutrientServing.setText("Total Serving Amount ("+food.getServingAmount()+food.getServingAmountUnit()+")");

        for(int i = 0 ; i<food.getNutrients().size();i++){
            final TableRow tablerow = new TableRow(this);
            tablerow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT));

            final TextView name = new TextView(this);
            name.setText(food.getNutrients().get(i).getNutrientName());
            name.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));


            final TextView servingAmount = new TextView(this);
            servingAmount.setText(food.getNutrients().get(i).getServingAmount() + food.getNutrients().get(i).getServingAmountUnit());
            servingAmount.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            final TextView rate = new TextView(this);
            if(food.getNutrients().get(i).getRate() == 0) {
                rate.setText("-");
            }else{
                rate.setText(food.getNutrients().get(i).getRate() + "%");
            }
            rate.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));


            tablerow.addView(name);
            tablerow.addView(servingAmount);
            tablerow.addView(rate);
            table.addView(tablerow);

        }

        */
    }

    @Override
    public void onClick(View view) {
        if(view == btnBack){ finish(); }
        if(view == btnDelete){ deletion(); }
        if(view == btnRevise){

            //ToDo
        }
    }



    private void deletion(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm deletion")
                .setMessage("Are you sure you want to delete this food?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ArrayList<Food> foodlist= User.INSTANCE.getRefrigeratorList().get(User.INSTANCE.getCurrentRefrigerator()).getFoodList();
                        Intent intent = getIntent();
                        int num = intent.getIntExtra("list_number",-1);
                        Log.d("index number",num+"");
                        foodlist.remove(num);
                        setResult(LIST_CHANGED,intent);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });


        AlertDialog dialog = builder.create();
        dialog.show();

    }



}
