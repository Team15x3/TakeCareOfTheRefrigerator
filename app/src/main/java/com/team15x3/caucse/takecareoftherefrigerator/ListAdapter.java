package com.team15x3.caucse.takecareoftherefrigerator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        ImageView ivPicture = (ImageView)view.findViewById(R.id.ivPictureOfFood);
        TextView tvName = (TextView)view.findViewById(R.id.tvNameOfFood);
        TextView tvNumberOfFood = (TextView)view.findViewById(R.id.tvCountFood);
        TextView tvExpirationDate = (TextView)view.findViewById(R.id.tvExpirationDate);

        Food curFood = (Food)getItem(pos);
        final String imageUrl = curFood.getPicture();
        tvName.setText(curFood.getFoodName());
        tvNumberOfFood.setText("count : "+curFood.getmCount());

        if(curFood.getExpirationDate() == 1){
            tvExpirationDate.setText(""+curFood.getExpirationDate()+" Day");
        }else if(curFood.getExpirationDate()>1) {
            tvExpirationDate.setText("" + curFood.getExpirationDate() + " Days");
        }
        if(imageUrl.equals("")){
            ivPicture.setImageDrawable(view.getResources().getDrawable(R.drawable.empty_pic));
        }else {
            //set image using url
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(imageUrl);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
                        connection.connect();

                        InputStream input = connection.getInputStream();
                        FoodPic = BitmapFactory.decodeStream(input);
                    } catch (IOException ex) {

                    }
                }
            };
            thread.start();
            try {
                thread.join();
                ivPicture.setImageBitmap(FoodPic);
            } catch (InterruptedException e) {

            }
        }
        return view;
    }


}
