package com.team15x3.caucse.takecareoftherefrigerator;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Data2Activity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    public int i = 0;
    public String data1;
    public String data2;
    public String data3;
    public int j = 0;
    public int ii=0;
    final ArrayList<String> list = new ArrayList<>();
    final ArrayList<String> list2 = new ArrayList<>();
    final ArrayList<String> list3 = new ArrayList<>();
    final ArrayList<String> list4 = new ArrayList<>();
    final ArrayList<String> list5 = new ArrayList<>();
    final ArrayList<String> list6 = new ArrayList<>();
    String[] array1 = new String[10];
    public String zz = "";
    public String z1 = "";
    public String zzz="";
    public String z2="";
    public String zzzz="";
    public String z3="";
    public String data4;
    public String abend="";
    public String data5="";
    public String data6="";
    int num2=0;
    public String endcc="";
    public String foodname="굴";
    public String endbb="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data2);

        Log.d("시작1", "ㅇㅇ");
        aa(0);
        ab();
        Log.d("끝1", "ㄴㅇㄹㅇㄴㄹ");

    }

    public String aa(int a) {
        databaseReference = firebaseDatabase.getReference("Recipe" + "/" + "Recipe3" + "/" + "data");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    Log.d("데이터", "sdfdsf");
                    data1 = dataSnapshot.child(String.valueOf(i)).child("IRDNT_NM").getValue().toString();

                    if (data1.equals(foodname)) {
                        data2 = dataSnapshot.child(String.valueOf(i)).child("RECIPE_ID").getValue().toString();
                        Log.d("data22",data2);
                        //bb();
                        list.add( dataSnapshot.child(String.valueOf(i)).child("IRDNT_SN").getValue().toString());
                        //list2.add(dataSnapshot.child(String.valueOf(i)).child("RECIPE_ID").getValue().toString());
                        Log.d("쌀", dataSnapshot.child(String.valueOf(i)).child("IRDNT_SN").getValue().toString());
                        list.add( dataSnapshot.child(String.valueOf(i)).child("IRDNT_TY_NM").getValue().toString());
                        Log.d("쌀", dataSnapshot.child(String.valueOf(i)).child("IRDNT_TY_NM").getValue().toString());
                        list.add( dataSnapshot.child(String.valueOf(i)).child("RECIPE_ID").getValue().toString());
                        Log.d("쌀", dataSnapshot.child(String.valueOf(i)).child("RECIPE_ID").getValue().toString());
                        list.add( dataSnapshot.child(String.valueOf(i)).child("IRDNT_CPCTY").getValue().toString());
                        Log.d("쌀", dataSnapshot.child(String.valueOf(i)).child("IRDNT_CPCTY").getValue().toString());
                        list.add( dataSnapshot.child(String.valueOf(i)).child("IRDNT_TY_CODE").getValue().toString());
                        Log.d("쌀", dataSnapshot.child(String.valueOf(i)).child("IRDNT_TY_CODE").getValue().toString());

                        Log.d("쌀2", data2);

                        Log.d("쌀3", list.toString());
                        zz = list.toString();
                        Log.d("쌀4", zz);

                        save();
                        list.clear();
                    }
                    i++;
                    if (i == 6105) {
                        break;
                    }
                }
                Log.d("쌀5", zz);
                z1 = zz;
                Log.d("쌀6", z1);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Log.d("쌀7", z1);
        return z1;

    }

    public String save()
    {
        z1 = zz;
        Log.d("세이브",z1);

        return z1;
    }
    public void ab()
    {


        num2=0;
        databaseReference = firebaseDatabase.getReference("Recipe"+"/"+"Recipe3"+"/"+"data");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("j다",String.valueOf(num2));
                Log.d("ababab","abab");
                Log.d("data5",data5);
                Log.d("abend2",abend);
                Log.d("num2",String.valueOf(num2));

                for(DataSnapshot dd:dataSnapshot.getChildren())
                {

                    String enddata= dataSnapshot.child(String.valueOf(num2)).child("RN").getValue().toString();

                    Log.d("num2다2",String.valueOf(num2));
                    if (dataSnapshot.child(String.valueOf(num2)).child("IRDNT_NM").getValue().toString().equals(foodname)) {
                        list3.add(dataSnapshot.child(String.valueOf(num2)).child("RECIPE_ID").getValue().toString());

                    }
                    if(Integer.parseInt(enddata)==6103)
                    {
                        bb();
                        //cc();
                        break;
                    }
                    num2++;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void bb()
    {
        databaseReference = firebaseDatabase.getReference("Recipe"+"/"+"Recipe2"+"/"+"data");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int cc=0;
                int count =10000;
                Log.d("123123","123");
                for(DataSnapshot messageData:dataSnapshot.getChildren())
                {

                    //if(dataSnapshot.child(String.valueOf(j)).child("RECIPE_ID").getValue()!=null) {
                    data3 = dataSnapshot.child(String.valueOf(j)).child("RECIPE_ID").getValue().toString();

                    Log.d("data3",data3);
                    for(int i=0;i<list3.size();i++)
                        Log.d("리스트3",list3.get(i));
                    if(data3.equals(list3.get(cc)))
                    {
                        count =0;
                        list5.add( dataSnapshot.child(String.valueOf(j)).child("RECIPE_ID").getValue().toString());
                        list5.add( dataSnapshot.child(String.valueOf(j)).child("RECIPE_NM_KO").getValue().toString());
                        list5.add( dataSnapshot.child(String.valueOf(j)).child("SUMRY").getValue().toString());
                        list5.add( dataSnapshot.child(String.valueOf(j)).child("NATION_CODE").getValue().toString());
                        list5.add( dataSnapshot.child(String.valueOf(j)).child("NATION_NM").getValue().toString());
                        list5.add( dataSnapshot.child(String.valueOf(j)).child("TY_CODE").getValue().toString());
                        list5.add( dataSnapshot.child(String.valueOf(j)).child("TY_NM").getValue().toString());
                        list5.add( dataSnapshot.child(String.valueOf(j)).child("COOKING_TIME").getValue().toString());
                        list5.add( dataSnapshot.child(String.valueOf(j)).child("CALORIE").getValue().toString());
                        list5.add( dataSnapshot.child(String.valueOf(j)).child("QNT").getValue().toString());
                        list5.add( dataSnapshot.child(String.valueOf(j)).child("LEVEL_NM").getValue().toString());
                                /*if(dataSnapshot.child(String.valueOf(j)).child("IRDNT_CODE").getValue()!=null)
                                list5.add( dataSnapshot.child(String.valueOf(j)).child("IRDNT_CODE").getValue().toString());
                                if(dataSnapshot.child(String.valueOf(j)).child("PC_NM").getValue()!=null)
                                list5.add( dataSnapshot.child(String.valueOf(j)).child("PC_NM").getValue().toString());*/
                        list5.add( dataSnapshot.child(String.valueOf(j)).child("IMG_URL").getValue().toString());
                        list5.add( dataSnapshot.child(String.valueOf(j)).child("DET_URL").getValue().toString());

                        zzz = list5.toString();
                        save2();

                        list5.clear();
                        cc++;
                    }



                    j++;
                    if(list3.size()==cc) {
                        Log.d("cc값3",String.valueOf(cc));
                        endbb = "end";
                    }
                    if(endbb.equals("end")) {
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void cc()
    {

        databaseReference = firebaseDatabase.getReference("Recipe"+"/"+"Recipe1"+"/"+"data");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int dd=0;
                int count =10000;

                for(DataSnapshot aa:dataSnapshot.getChildren())
                {


                    data6 = dataSnapshot.child(String.valueOf(ii)).child("RECIPE_ID").getValue().toString();
                    if(data6.equals(list3.get(dd)))
                    {
                        count=0;

                        list4.add( dataSnapshot.child(String.valueOf(ii)).child("RECIPE_ID").getValue().toString());
                        list4.add( dataSnapshot.child(String.valueOf(ii)).child("COOKING_NO").getValue().toString());
                        list4.add( dataSnapshot.child(String.valueOf(ii)).child("COOKING_DC").getValue().toString());
                            /*list4.add( dataSnapshot.child(String.valueOf(ii)).child("STRE_STEP_IMAGE_URL").getValue().toString());
                            list4.add( dataSnapshot.child(String.valueOf(ii)).child("STEP_TIP").getValue().toString());*/
                        //Log.d("히히히히히",list4.get(dd));
                        zzzz = list4.toString();

                        save3();

                        list4.clear();

                    }


                    if(count==1)
                        dd++;
                    count++;
                    ii++;
                    if(list3.size()==dd)
                        endcc="end";
                    if(endcc.equals("end")) {
                        break;
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public String save2()
    {
        z2 = zzz;
        Log.d("세이브2",z2);

        return list5.toString();



    }
    public String save3()
    {
        z3 = zzzz;
        Log.d("세이브3",list4.toString());


        return list4.toString();
    }

    public void pp()
    {
        String a = save2();
        Log.d("세이브2값",a);
    }
}
