package com.team15x3.caucse.takecareoftherefrigerator;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class TabHomeFragment extends Fragment implements View.OnClickListener {
    private ListView FoodList;
    private ImageView ivEmptyFoodList;
    private View view;
    private ArrayList<Refrigerator> friger;
    private  ArrayList<Food> data;
    private ImageView ivHomeImage;
    private static ListAdapter adapter;
    private Button btnInsert;
    private String myBarcode;
    private EditText edtSearch;
    private Button btnSearch;
    private TextView tvName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tab_home_fragment, container, false);
        FoodList = (ListView) view.findViewById(R.id.lvFoodList);

        friger = User.INSTANCE.getRefrigeratorList();
        btnInsert = (Button)view.findViewById(R.id.btnInsert);
        edtSearch= (EditText)view.findViewById(R.id.edtSearch);
        edtSearch.setInputType(0);
        btnSearch = (Button)view.findViewById(R.id.btnSearch);
        tvName = (TextView)view.findViewById(R.id.tvName);

        edtSearch.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnInsert.setOnClickListener(this);

        if (friger.isEmpty()) {
            friger = User.INSTANCE.getRefrigeratorList();

            friger.add(new Refrigerator("My Friger"));

            friger.get(0).addFood(new Food("ex1","",2,2));
            friger.get(0).addFood(new Food("ex2","",2,2));

            setFoodList();
        } else {
            setFoodList();
        }
        return view;
    }



    private void setFoodList() {

        try {
             tvName.setText(User.INSTANCE.getRefrigeratorList().get(User.INSTANCE.getCurrentRefrigerator()).getName());
            data = friger.get(User.INSTANCE.getCurrentRefrigerator()).getFoodList();
            if (data.isEmpty()) {
                ivEmptyFoodList = (ImageView) view.findViewById(R.id.ivEmptyFoodList);
                ivEmptyFoodList.setImageDrawable(getResources().getDrawable(R.drawable.empty));
            } else {
                Log.d("success","get food list");
                adapter = new ListAdapter(view.getContext(), R.layout.food_list, data);
                FoodList.setAdapter(adapter);

                FoodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        FoodInfoDialog dialog = new FoodInfoDialog(getContext());
                        dialog.callFunction(data.get(i));
                    }
                });
            }
        } catch (NullPointerException e) {
            Log.d("nullpointException ", "data is null");
            return;
        } catch (Exception e) {
            Log.d("exception", "???");
        }


    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()){
            //두가지 선택지를 보여주는 다이얼로그(바코드, 수동입력)
            case R.id.btnInsert: {
                FoodInsertDialog dialog = new FoodInsertDialog(getContext());
                dialog.callFunction();
                break;
            }
            case R.id.edtSearch:{
                edtSearch.setInputType(1);
                InputMethodManager mgr = (InputMethodManager) view.getContext().getSystemService(view.getContext().INPUT_METHOD_SERVICE);
                mgr.showSoftInput(edtSearch, InputMethodManager.SHOW_IMPLICIT);
                break;
            }
            case R.id.btnSearch:

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode,data);
        myBarcode = result.getContents(); //get barcode number
        Toast.makeText(getContext(),myBarcode,Toast.LENGTH_SHORT).show();
        //api parsing , get information
    }
}
