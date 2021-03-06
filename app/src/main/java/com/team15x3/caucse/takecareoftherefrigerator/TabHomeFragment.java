package com.team15x3.caucse.takecareoftherefrigerator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class TabHomeFragment extends Fragment implements View.OnClickListener {
    private final int INSERT_REQUEST = 11;
    private final int SHOW_INFORMATION_REQUEST = 12;
    private ListView FoodList;
    private ImageView ivEmptyFoodList;
    private View view;
    private ArrayList<Refrigerator> friger;
    private ArrayList<Food> data ,list;
    private ImageView ivHomeImage;
    private static ListAdapter adapter;
    private Button btnInsert,btnMenu;
    private String myBarcode;
    private EditText edtSearch;
    private TextView tvName;
    private ArrayList<Integer> findIndex = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tab_home_fragment, container, false);
        FoodList =  view.findViewById(R.id.lvFoodList);

        friger = User.INSTANCE.getRefrigeratorList();
        btnInsert = view.findViewById(R.id.btnInsert);
        edtSearch= view.findViewById(R.id.edtSearch);
        edtSearch.setInputType(0);
        tvName = view.findViewById(R.id.tvName);
        ivEmptyFoodList = (ImageView) view.findViewById(R.id.ivEmptyFoodList);

        btnMenu = view.findViewById(R.id.btnMenu);

        edtSearch.setOnClickListener(this);
        btnInsert.setOnClickListener(this);
        btnMenu.setOnClickListener(this);

        if (friger.isEmpty()) {
            friger = User.INSTANCE.getRefrigeratorList();
            friger.add(new Refrigerator("My Friger"));
        }
        setFoodList();

        return view;
    }



    public void setFoodList() {

        try {
            if(friger.isEmpty()){

            }
             tvName.setText(User.INSTANCE.getRefrigeratorList().get(User.INSTANCE.getCurrentRefrigerator()).getName());

            //data =실제 데이터, list=복사본
            data  = friger.get(User.INSTANCE.getCurrentRefrigerator()).getFoodList();
            list = new ArrayList<Food>();
            list.addAll(data);

            if (data.isEmpty()) {
                ivEmptyFoodList.setVisibility(View.VISIBLE);
                FoodList.setVisibility(View.INVISIBLE);
                ivEmptyFoodList.setImageDrawable(getResources().getDrawable(R.drawable.empty));
            } else {
                ivEmptyFoodList.setVisibility(View.INVISIBLE);
                FoodList.setVisibility(View.VISIBLE);
                Log.d("success","get food list");
                adapter = new ListAdapter(view.getContext(), R.layout.food_list, list);
                FoodList.setAdapter(adapter);

                //item click listener
                FoodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ShowFoodInformation(list.get(i),i);
                    }
                });

                //search
                edtSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                    @Override
                    public void afterTextChanged(Editable editable) {

                        String text = edtSearch.getText().toString();
                        search(text);
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
            case R.id.btnInsert: {
                Intent intent = new Intent(view.getContext(),InsertFoodActivity.class);
                startActivityForResult(intent,INSERT_REQUEST);
                break;
            }
            case R.id.edtSearch:{
                edtSearch.setInputType(1);
                InputMethodManager mgr = (InputMethodManager) view.getContext().getSystemService(view.getContext().INPUT_METHOD_SERVICE);
                mgr.showSoftInput(edtSearch, InputMethodManager.SHOW_IMPLICIT);
                break;
            }

            case R.id.btnMenu: {
                // friger


                show_dialog();


                // User.INSTANCE.setCurrentRefrigerator(); // input current index
            }

        }
    }

    private void show_dialog() {

        final List<String> refri_ListItems = new ArrayList<String>();

        for (int i = 0; i < friger.size(); i++) {
            refri_ListItems.add(friger.get(i).getName());
        }

        final CharSequence[] items =  refri_ListItems.toArray(new String[ refri_ListItems.size()]);


        final List SelectedItems  = new ArrayList();
        int defaultItem = 0;
        SelectedItems.add(defaultItem);

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("AlertDialog Title");
        builder.setSingleChoiceItems(items, defaultItem,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SelectedItems.clear();
                        SelectedItems.add(which);
                    }
                });
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String msg="";

                        if (!SelectedItems.isEmpty()) {
                            int index = (int) SelectedItems.get(0);
                            msg = refri_ListItems.get(index);

                            User.INSTANCE.setCurrentRefrigerator(index);
                            setFoodList();

                            // index 값을 변화!!
                        }
                        Toast.makeText(view.getContext(),
                                "Items Selected.\n"+ msg , Toast.LENGTH_LONG)
                                .show();
                    }
                });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }


    private void search(String text){
        findIndex.clear();
        list.clear();
        if(text.length() == 0){
            list.addAll(data);
        }else{
            for(int i = 0; i<data.size();i++){

                if(data.get(i).getFoodName().toLowerCase().contains(text)){
                    list.add(data.get(i));
                    findIndex.add(i);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void ShowFoodInformation(Food food, int listnumber){
        Intent intent = new Intent(view.getContext(),FoodInfoActivity.class);
        if(edtSearch.getText().toString().length()== 0){
            intent.putExtra("list_number",listnumber);
        }else{
            intent.putExtra("list_number",findIndex.get(listnumber));
        }
        startActivityForResult(intent, SHOW_INFORMATION_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("ON_ACTIVITY_CALLED",requestCode + "METHOD CALLED" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SHOW_INFORMATION_REQUEST && resultCode == FoodInfoActivity.LIST_CHANGED){
            Log.d("DELETE_CEHCK","CALL!");
            setFoodList();
            edtSearch.setText(edtSearch.getText().toString());
        }
        if(requestCode == INSERT_REQUEST && resultCode == RESULT_OK){
            Log.d("ONACTIVIY_RESULT_CALLED","OnActivityResult, insert_request");
            setFoodList();
        }
    }

}
