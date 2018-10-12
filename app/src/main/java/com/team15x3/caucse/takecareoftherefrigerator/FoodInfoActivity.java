package com.team15x3.caucse.takecareoftherefrigerator;

import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
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

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

import static java.security.AccessController.getContext;

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

        int idx = getIntent().getIntExtra("list_number",-1);
        food = User.INSTANCE.getRefrigeratorList().get(User.INSTANCE.getCurrentRefrigerator()).getFoodList().get(idx);

        btnBack = (Button)findViewById(R.id.btnBack);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnRevise = (Button)findViewById(R.id.btnRevise);
        table = findViewById(R.id.table);

        SetInformationOfFood();

        btnBack.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnRevise.setOnClickListener(this);
    }

    private void SetInformationOfFood() {
        tvFoodName = findViewById(R.id.tvName);
        tvCountFood = findViewById(R.id.tvCountFood);
        tvExpirationDate = findViewById(R.id.tvExpirationDate);
        tvIngredients = findViewById(R.id.tvIngredients);
        tvAllergyIngredient = findViewById(R.id.tvAllergyIngredient);
        tvNutrientServing = findViewById(R.id.tvNutrientServing);

        tvFoodName.setText(food.getFoodName());
        tvCountFood.setText("Quantity : "+food.getCount());
        tvExpirationDate.setText("Exp date : "+food.getExpirationDate()) ;

        //setPicture(food);
        if(food.getMaterialList().size() != 0){
            for(int i = 0; i<food.getMaterialList().size()-1;i++){
                tvIngredients.append(food.getMaterialList().get(i).getMaterialName() +", ");
            }
            tvIngredients.append(food.getMaterialList().get(food.getMaterialList().size()-1).getMaterialName());
        }

        Iterator<Allergy> iter = food.getAllergyList().iterator();
        while(iter.hasNext()) {
            tvAllergyIngredient.append(iter.next().getMaterialName()+", ");
        }


 /*       if(food.getAllergyList().size()!=0){
            for(int i = 0; i<food.getAllergyList().size()-1; i++){
                tvAllergyIngredient.append(food.getAllergyList().get(i).getMaterialName()+", ");
            }
            tvAllergyIngredient.append(food.getAllergyList().get(food.getAllergyList().size()-1).getMaterialName());
        }*/
        tvNutrientServing.setText("Total Serving Amount ("+food.getMainNutrientServingMeasureAmount()+food.getMainNutrientServingMeasureUnit()+")");

        for(int i = 0 ; i<food.getMainNutrientsList().size();i++){
            TableRow tablerow = new TableRow(this);
            tablerow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT));

            TextView name = new TextView(this);
            name.setText(food.getMainNutrientsList().get(i).getNutrientName());
            name.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView servingAmount = new TextView(this);
            servingAmount.setText(food.getMainNutrientsList().get(i).getServingAmount() + food.getMainNutrientsList().get(i).getServingAmountUnit());
            servingAmount.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView rate = new TextView(this);
            rate.setText(food.getMainNutrientsList().get(i).getRate()+"%");
            rate.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            tablerow.addView(name);
            tablerow.addView(servingAmount);
            tablerow.addView(rate);
            table.addView(tablerow);
        }
    }

    @Override
    public void onClick(View view) {
        if(view == btnBack){ finish(); }
        if(view == btnDelete){ deletion(); }
        if(view == btnRevise){

            //ToDo
        }
    }

    private void deletion()  {

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


    private void setPicture(Food food){
        if(food.getThumbnailUrl().equals("")){
            Log.d("PICTURE_ADDRESS","NULL");
            ivFoodImage.setImageDrawable(getResources().getDrawable(R.drawable.empty_pic));
        }else if(food.getIsFromGallery() == true){
            Log.d("PICTURE_ADDRESS",food.getThumbnailUrl());
            File imgFile = new File(food.getThumbnailUrl());
            if (imgFile.exists()) {
                Log.d("PICTURE_ADDRESS","called!");
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                ivFoodImage.setImageBitmap(myBitmap);
            }
        }else{
            Picasso.with(this)
                    .load(food.getThumbnailUrl())
                    .into(ivFoodImage);
        }
    }
}
