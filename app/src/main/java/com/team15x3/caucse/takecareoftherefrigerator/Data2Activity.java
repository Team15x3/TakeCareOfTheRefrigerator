package com.team15x3.caucse.takecareoftherefrigerator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

import java.net.Inet4Address;
import java.util.ArrayList;

public class Data2Activity extends AppCompatActivity {
    private ArrayAdapter<String> dataAdapter;
    EditText editText;
    Button button;
    public String foodname="";
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    public String data1="";
    public String data2="";
    public String data3="";
    public String data5="";
    public String data6="";
    public int data4;
    public int foodcode;
    public int recipeid;
    public String recipe1end="";
    public String recipe3end="";
    public String recipe2end="";
    private ListView listView;
    public int a;
    public int num1;
    public int num2=0;
    public int num3=0;
    public int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data2);
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(dataAdapter);
        editText=(EditText)findViewById(R.id.edt1);
        button = (Button)findViewById(R.id.btn1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataAdapter.clear();
                foodname = editText.getText().toString();
                Log.d("FOOD",foodname);
                num2=0;
                num3=0;
                if(foodname!="")
                {
                    Recipe3(0);
                    Log.d("food1",foodname);
                }
            }
        });
    }
    public void Recipe3(int b) {

        databaseReference = firebaseDatabase.getReference("Recipe" + "/" + "Recipe3" + "/" + "data");
        num1=b;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("레시피3", "ㅇㄴㄹㅇㄴㄹㅇㄴㄹㅇㄴㄹㄴ");
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    String enddata= dataSnapshot.child(String.valueOf(num1)).child("RN").getValue().toString();
                       data1=dataSnapshot.child(String.valueOf(num1)).child("IRDNT_NM").getValue().toString();
                       Log.d("레시피3333",String.valueOf(num1));
                    Log.d("레시피3", "짜짜짜짜짜짜짜짜");
                    Log.d("레시피3", String.valueOf(num1));
                    Log.d("레시피3", dataSnapshot.child(String.valueOf(num1)).child("RECIPE_ID").getValue().toString());
                    Log.d("레시피3",foodname);
                    Log.d("레시피3333",data1);
                    Log.d("레시피3",recipe3end);
                        if(data1.equals(foodname))
                        {
                            Log.d("음식기록",dataSnapshot.child(String.valueOf(num1)).child("RECIPE_ID").getValue().toString());
                            foodcode=Integer.parseInt(dataSnapshot.child(String.valueOf(num1)).child("RECIPE_ID").getValue().toString());

                            FindRecipe2(num2);
                            Log.d("이동했나","ㅇㅇㅇㅇ");
                            recipe3end="end";
                        }
                    if(recipe3end.equals("end")||Integer.parseInt(enddata)==6104)
                        break;
                        else
                    {
                        recipe3end="";
                    }
                    num1++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    public void pp()
    {
        Log.d("ppppp","pppppp");
    }
    public void FindRecipe2(int bb)
    {
        databaseReference = firebaseDatabase.getReference("Recipe"+"/"+"Recipe2"+"/"+"data");
        num2=bb;

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
               // dataAdapter.clear();

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    Log.d("레시피2","sdfsdfsfsdfdsf");
                    recipe3end="abcd";
                        Log.d("레시피2",String.valueOf(foodcode));
                        data4=Integer.parseInt(dataSnapshot.child(String.valueOf(num2)).child("RECIPE_ID").getValue().toString());

                        Log.d("레시피77",String.valueOf(data4));
                        Log.d("레시피33333",recipe2end);
                        if(foodcode== data4)
                        {
                            Log.d("dlehd",String.valueOf(num2));
                            Log.d("이동했니","ㅇㅇ");
                            recipeid = Integer.parseInt(dataSnapshot.child(String.valueOf(num2)).child("RECIPE_ID").getValue().toString());
                            dataAdapter.add(dataSnapshot.child(String.valueOf(num2)).child("RECIPE_NM_KO").getValue().toString());
                            dataAdapter.notifyDataSetChanged();
                            listView.setSelection(dataAdapter.getCount() - 1);
                            dataAdapter.add(dataSnapshot.child(String.valueOf(num2)).child("SUMRY").getValue().toString());
                            dataAdapter.notifyDataSetChanged();
                            listView.setSelection(dataAdapter.getCount() - 1);
                            dataAdapter.add(dataSnapshot.child(String.valueOf(num2)).child("CALORIE").getValue().toString());
                            dataAdapter.notifyDataSetChanged();
                            listView.setSelection(dataAdapter.getCount() - 1);
                            dataAdapter.add(dataSnapshot.child(String.valueOf(num2)).child("COOKING_TIME").getValue().toString());
                            dataAdapter.notifyDataSetChanged();
                            listView.setSelection(dataAdapter.getCount() - 1);
/*                            dataAdapter.add(dataSnapshot.child(String.valueOf(num2)).child("COOKING_TIME").getValue().toString());
                            dataAdapter.notifyDataSetChanged();
                            listView.setSelection(dataAdapter.getCount() - 1);
                            dataAdapter.add(dataSnapshot.child(String.valueOf(num2)).child("IRDNT_CODE").getValue().toString());
                            dataAdapter.notifyDataSetChanged();
                            listView.setSelection(dataAdapter.getCount() - 1);*/
                            dataAdapter.add(dataSnapshot.child(String.valueOf(num2)).child("LEVEL_NM").getValue().toString());
                            dataAdapter.notifyDataSetChanged();
                            listView.setSelection(dataAdapter.getCount() - 1);
                            dataAdapter.add(dataSnapshot.child(String.valueOf(num2)).child("TY_NM").getValue().toString());
                            dataAdapter.notifyDataSetChanged();
                            listView.setSelection(dataAdapter.getCount() - 1);
                            num2++;
                            FindRecipe1(num3);
                            recipe2end="end";
                        }
                        Log.d("아이",String.valueOf(num2));

                    if(recipe2end.equals("end")||data4==195453)
                        break;
                    num2++;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void FindRecipe1(int bbb)
    {
        databaseReference = firebaseDatabase.getReference("Recipe"+"/"+"Recipe1"+"/"+"data");

            num3=0;
            num2++;
        count =10000;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("data3",data1);
                Log.d("data1",String.valueOf(recipeid));
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    recipe2end="abcd";
                    recipe1end="abcd";
                    data6 = dataSnapshot.child(String.valueOf(num3)).child("RECIPE_ID").getValue().toString();
                    if (Integer.parseInt(data6) == recipeid) {
                        count =0;
                        Log.d("qwqwqw", dataSnapshot.child(String.valueOf(num3)).child("COOKING_DC").getValue().toString() + "/");
                        dataAdapter.add(dataSnapshot.child(String.valueOf(num3)).child("COOKING_DC").getValue().toString());

                    }
                    if(count==2)
                    {
                        recipe1end = "end";
                    }

                    if (recipe1end.equals("end")||Integer.parseInt(data6)==195453) {
                        Log.d("레시피333",recipe3end);
                        Recipe3(num1 + 1);
                        num3++;
                        break;
                    }
                    num3++;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
