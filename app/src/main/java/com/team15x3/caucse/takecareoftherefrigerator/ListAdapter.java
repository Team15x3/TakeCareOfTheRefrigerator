package com.team15x3.caucse.takecareoftherefrigerator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.util.Log;
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
import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Food> lists = new ArrayList<Food>();
    private Bitmap FoodPic;
    private int layout;
    private Food curFood;
    private ImageView ivPicture;
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

       /* if(curFood.getExpirationDate() == 1){
            tvExpirationDate.setText(""+curFood.getExpirationDate()+" Day");
        }else if(curFood.getExpirationDate()>1) {
            tvExpirationDate.setText("" + curFood.getExpirationDate() + " Days");*/
        //

        setPictureOnList(view);
        return view;
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
