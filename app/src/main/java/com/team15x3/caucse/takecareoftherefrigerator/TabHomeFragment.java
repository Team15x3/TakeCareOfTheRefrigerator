package com.team15x3.caucse.takecareoftherefrigerator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TabHomeFragment extends Fragment {
    private ListView FoodList;
    private ImageView ivEmptyFoodList;
    private View view;
    private ArrayList<Refrigerator> friger;
    private  ArrayList<Food> data;
    private ImageView ivHomeImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tab_home_fragment, container, false);
        FoodList = (ListView) view.findViewById(R.id.lvFoodList);

        friger = User.INSTANCE.getRefrigeratorList();
        friger.add(new Refrigerator());

        friger.get(0).addFood(new Food("ex1","",2,2));
        friger.get(0).addFood(new Food("ex1","",2,2));
        /*
        friger.get(0).addFood(new Food("ex1","",2,2));
        friger.get(0).addFood(new Food("ex1","",2,2));
        friger.get(0).addFood(new Food("ex1","",2,2));
        friger.get(0).addFood(new Food("ex1","",2,2));
        */


        if (friger.isEmpty()) {
            Toast.makeText(view.getContext(), "냉장고를 만들어보세요!", Toast.LENGTH_SHORT).show();
            //팝업창
        } else {
            setFoodList();
        }
        return view;
    }

    private void setFoodList() {

        try {
            data = friger.get(User.INSTANCE.getDefaultRefrigerator()).getFoodList();
            if (data.isEmpty()) {
                ivEmptyFoodList = (ImageView) view.findViewById(R.id.ivEmptyFoodList);
                ivEmptyFoodList.setImageDrawable(getResources().getDrawable(R.drawable.empty));
            } else {
                Log.d("success","get food list");
                ListAdapter adapter = new ListAdapter(view.getContext(), R.layout.food_list, data);
                FoodList.setAdapter(adapter);
            }
        } catch (NullPointerException e) {
            Log.d("nullpointException ", "data is null");
            return;
        } catch (Exception e) {
            Log.d("exception", "???");
        }


    }

}
