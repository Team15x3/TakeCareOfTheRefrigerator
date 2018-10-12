package com.team15x3.caucse.takecareoftherefrigerator;

import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

        tvFoodName = findViewById(R.id.tvName);
        tvCountFood = findViewById(R.id.tvCountFood);
        tvExpirationDate = findViewById(R.id.tvExpirationDate);
        ivFoodImage = findViewById(R.id.ivFoodImage);
        tvIngredients = findViewById(R.id.tvIngredients);
        tvAllergyIngredient = findViewById(R.id.tvAllergyIngredient);
        tvNutrientServing = findViewById(R.id.tvNutrientServing);

        tvFoodName.setText(food.getFoodName());
        tvCountFood.setText("Quantity : "+food.getCount());

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String string = date.format(food.getExpirationDate());

        tvExpirationDate.setText(string);
        setPicture(food);

        SetInformationOfFood(food, tvIngredients, tvAllergyIngredient, tvNutrientServing, table,this);

        btnBack.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnRevise.setOnClickListener(this);
    }

    public static void  SetInformationOfFood(Food food, TextView tvIngredients, TextView tvAllergyIngredient, TextView tvNutrientServing, TableLayout table, Context context) {

        Iterator<Material> materials = food.getMaterialList().iterator();
        while(materials.hasNext()){
            tvIngredients.append(materials.next().getMaterialName() +", ");
        }

        Iterator<Allergy> iter = food.getAllergyList().iterator();
        while(iter.hasNext()) {
            tvAllergyIngredient.append(iter.next().getMaterialName()+", ");
        }
        tvNutrientServing.setText("Total Serving Amount ("+food.getMainNutrientServingMeasureAmount()+food.getMainNutrientServingMeasureUnit()+")");

        Iterator<Nutrient> nutrientIterator = food.getMainNutrientsList().iterator();
        while(nutrientIterator.hasNext()){
            Nutrient f = nutrientIterator.next();
            TableRow tablerow = new TableRow(context);
            tablerow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT));

            TextView name = new TextView(context);
            name.setText(f.getNutrientName()+"   ");
            name.setTextSize(20);
            name.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView servingAmount = new TextView(context);
            servingAmount.setText(f.getServingAmount() + f.getServingAmountUnit().toLowerCase()+"   ");
            servingAmount.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            servingAmount.setTextSize(20);

            TextView rate = new TextView(context);
            rate.setText(f.getRate()+"%");
            rate.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            rate.setTextSize(20);

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
        if(food.getThumbnailUrl() == null || food.getThumbnailUrl().equals("")){
            Log.d("PICTURE_ADDRESS","NULL");
            ivFoodImage.setImageDrawable(getResources().getDrawable(R.drawable.empty_pic));
            return;
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
