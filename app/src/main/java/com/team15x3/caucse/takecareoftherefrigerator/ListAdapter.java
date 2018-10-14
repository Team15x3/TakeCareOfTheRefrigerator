package com.team15x3.caucse.takecareoftherefrigerator;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Ref;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Food> lists = new ArrayList<Food>();
    private Bitmap FoodPic;
    private int layout;
    private Food curFood;
    private ImageView ivPicture;
    final Calendar calendar = Calendar.getInstance();
    TextView tvName,tvNumberOfFood, tvExpirationDate;


    public ListAdapter(Context context, int layout, ArrayList<Food> data){
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.lists = data;
        this.layout = layout;

    }
    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final int pos = position;
        final Context context = viewGroup.getContext();

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.food_list,viewGroup,false);
        }

        ivPicture = (ImageView)view.findViewById(R.id.ivPictureOfFood);
        tvName = (TextView)view.findViewById(R.id.tvNameOfFood);
        tvNumberOfFood = (TextView)view.findViewById(R.id.tvCountFood);
        tvExpirationDate = (TextView)view.findViewById(R.id.tvExpirationDate);

        curFood = (Food)getItem(pos);
        tvName.setText(curFood.getFoodName());
        tvNumberOfFood.setText("Quantity : "+curFood.getCount());

        setPictureOnList(view);
        setExpirationdate(curFood,view);

        return view;
    }


    private void setExpirationdate(Food curFood,View view){

        try {
            SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);

//            Date tempDate = new Date();
            String str= Integer.toString(calendar.get(Calendar.YEAR)*10000 + (calendar.get(Calendar.MONTH) + 1)*100 + calendar.get(Calendar.DAY_OF_MONTH));
            Date curDate = simple.parse(str);
            Date expdate = simple.parse(curFood.getExpirationDate());

            long calDate = expdate.getTime() - curDate.getTime();
            long calDateDays = calDate/(24*60*60*1000);
            calDateDays = Math.abs(calDateDays);

            if(calDate<0){
                tvExpirationDate.setText("-"+calDateDays+" Day");
                tvExpirationDate.setTextColor(ContextCompat.getColor(view.getContext(),R.color.wine));
            }else{
                tvExpirationDate.setText(calDateDays+" Day");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setPictureOnList(View view){
        if( curFood.getThumbnailUrl() == null || curFood.getThumbnailUrl().isEmpty()){
            Log.d("PICTURE_ADDRESS","NULL");
            ivPicture.setImageDrawable(view.getResources().getDrawable(R.drawable.empty_pic));
        }else if(curFood.getIsFromGallery() == true){
            Log.d("PICTURE_ADDRESS",curFood.getThumbnailUrl());
            File imgFile = new File(curFood.getThumbnailUrl());
            if (imgFile.exists()) {
                Log.d("PICTURE_ADDRESS","called!");
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                ivPicture.setImageBitmap(myBitmap);
            }
        }else{
            Picasso.with(view.getContext())
                    .load(curFood.getThumbnailUrl())
                    .into(ivPicture);
        }
    }


}

class RecipeAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private ArrayList<Recipe> lists = new ArrayList<>();
    private Bitmap FoodPic;
    private int layout;
    private Recipe curRecipe;
    private ImageView ivRecipeImage;
    TextView tvRecipeName;

    public RecipeAdapter(Context context, int layout, ArrayList<Recipe> data){
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.lists = data;
        this.layout = layout;

    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final int pos = position;
        final Context context = viewGroup.getContext();

        if(view == null){
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.recipe_list,viewGroup,false);
        }

        ivRecipeImage = (ImageView)view.findViewById(R.id.ivRecipeImage);
        tvRecipeName = (TextView)view.findViewById(R.id.tvRecipeName);

        curRecipe = (Recipe)getItem(pos);
        tvRecipeName.setText(curRecipe.getRecpieName());


        /*HttpURLConnection connection = null;
        if( curRecipe.getImageURL() != null &&!curRecipe.getImageURL().equals("")) {
            try {
                URL url = new URL(curRecipe.getImageURL());
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                ivRecipeImage.setImageBitmap(myBitmap);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (connection != null) connection.disconnect();
            }
        }*/
        return view;
    }
}