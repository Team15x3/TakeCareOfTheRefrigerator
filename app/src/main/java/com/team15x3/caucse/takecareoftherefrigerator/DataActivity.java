package com.team15x3.caucse.takecareoftherefrigerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.widget.ArrayAdapter;

import android.widget.ListView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ChildEventListener;


import java.util.ArrayList;

public class DataActivity extends AppCompatActivity {


    private ArrayAdapter<String> dataAdapter;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    public String Cooking_no="COOKING_NO";
    public String[] array1 =new String[3021];
    public String data1="";
    public String data2="";
    public int data3;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);


        listView = (ListView) findViewById(R.id.listView);

        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());

        listView.setAdapter(dataAdapter);




        databaseReference = firebaseDatabase.getReference("Recipe"+"/"+"Recipe2"+"/"+"data");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataAdapter.clear();
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    for(int i=0;i<537;i++) {
                        data1 = dataSnapshot.child(String.valueOf(i)).child("RECIPE_ID").getValue().toString();

                        if(Integer.parseInt(data1)==2) {

                            data3=Integer.parseInt(data1);
                            FindRecipe1();
                            Log.d("칼로리",dataSnapshot.child(String.valueOf(i)).toString());
                            Log.d("칼로리",dataSnapshot.child(String.valueOf(i)).child("CALORIE").getValue().toString());
                            Log.d("칼로리",dataSnapshot.child(String.valueOf(i)).child("COOKING_TIME").getValue().toString());
                            Log.d("칼리",dataSnapshot.child(String.valueOf(i)).child("RECIPE_NM_KO").getValue().toString());
                            dataAdapter.add(dataSnapshot.child(String.valueOf(i)).child("RECIPE_NM_KO").getValue().toString());
                            dataAdapter.notifyDataSetChanged();
                            listView.setSelection(dataAdapter.getCount() - 1);
                            dataAdapter.add(dataSnapshot.child(String.valueOf(i)).child("SUMRY").getValue().toString());
                            dataAdapter.notifyDataSetChanged();
                            listView.setSelection(dataAdapter.getCount() - 1);
                            dataAdapter.add(dataSnapshot.child(String.valueOf(i)).child("CALORIE").getValue().toString());
                            dataAdapter.notifyDataSetChanged();
                            listView.setSelection(dataAdapter.getCount() - 1);
                            dataAdapter.add(dataSnapshot.child(String.valueOf(i)).child("COOKING_TIME").getValue().toString());
                            dataAdapter.notifyDataSetChanged();
                            listView.setSelection(dataAdapter.getCount() - 1);
                            dataAdapter.add(dataSnapshot.child(String.valueOf(i)).child("IRDNT_CODE").getValue().toString());
                            dataAdapter.notifyDataSetChanged();
                            listView.setSelection(dataAdapter.getCount() - 1);
                            dataAdapter.add(dataSnapshot.child(String.valueOf(i)).child("LEVEL_NM").getValue().toString());
                            dataAdapter.notifyDataSetChanged();
                            listView.setSelection(dataAdapter.getCount() - 1);
                            dataAdapter.add(dataSnapshot.child(String.valueOf(i)).child("TY_NM").getValue().toString());
                            dataAdapter.notifyDataSetChanged();
                            listView.setSelection(dataAdapter.getCount() - 1);



/*
                            TextView(dataSnapshot.child(String.valueOf(i)).child("COOKING_TIME").getValue().toString());
                            textView.setText("CALORIE="+dataSnapshot.child(String.valueOf(i)).child("CALORIE").getValue().toString());
                            textView.setText("COOKING_TIME="+dataSnapshot.child(String.valueOf(i)).child("COOKING_TIME").getValue().toString());
                            textView.setText("RECIPE_NAME="+dataSnapshot.child(String.valueOf(i)).child("RECIPE_NM_KO").getValue().toString());*/

                            Log.d("777777",data1);
                            Log.d("888888",String.valueOf(data3));

                            data1="end";
                        }
                        if(data1=="end")
                            break;
                    }
                    Log.d("222222222221", data1);
                    //String data2 = databaseReference.orderByChild("users").toString();
                    // child 내에 있는 데이터만큼 반복합니다.
                    //String data = dataSnapshot.getChildren().toString();
                    // Log.d("ooooo",data);
                    //Log.d("Users", data1);
                    // Log.d("ooooo",data2);
                    if(data1!=null)
                        break;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });



    }
    public void FindRecipe1()
    {
        databaseReference = firebaseDatabase.getReference("Recipe"+"/"+"Recipe1"+"/"+"data");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                int count1=10000;
                int count2;
                int temp=100;
                Log.d("data1",data1);
                Log.d("data1",String.valueOf(data3));
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    for(int i=0;i<3022;i++){
                        data2 = dataSnapshot.child(String.valueOf(i)).child("RECIPE_ID").getValue().toString();
                        if(Integer.parseInt(data2)==data3)
                        {
                            count1=0;
                            Log.d("qwqwqw", dataSnapshot.child(String.valueOf(i)).child("COOKING_DC").getValue().toString()+"/");

                        }
                        count1++;
                        if(count1==2)
                        {
                            data2="end";
                        }
                        if(data2=="end")
                            break;
                    }
                    if(data2=="end")
                        break;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
