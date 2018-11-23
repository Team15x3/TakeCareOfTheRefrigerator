package com.team15x3.caucse.takecareoftherefrigerator;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;


public class HomeActivity extends FragmentActivity {

    BottomBar bottomBar;
    private TabHomeFragment tabHomeFragment;
    private TabRecipeFragment tabRecipeFragment;
    private TabSettingFragment tabSettingFragment;
    private TabScrapeFragment tabFriendFragment;

    private ArrayList<Refrigerator> friger;
    private long backKeyPressedTime = 0;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();
        data();
      /*  Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(HomeActivity.this, R.color.transparent));
*/


        friger = User.INSTANCE.getRefrigeratorList();

        tabHomeFragment = new TabHomeFragment();
        tabRecipeFragment = new TabRecipeFragment();
        tabSettingFragment = new TabSettingFragment();
        tabFriendFragment = new TabScrapeFragment();

        bottomBar = (BottomBar)findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                FragmentTransaction transaction  = getSupportFragmentManager().beginTransaction();

                switch(tabId){
                    case R.id.tab_home : transaction.replace(R.id.contentContainer, tabHomeFragment).commit();
                        break;
                    case R.id.tab_recipe: transaction.replace(R.id.contentContainer, tabRecipeFragment).commit();
                        break;
                    case R.id.tab_setting: transaction.replace(R.id.contentContainer, tabSettingFragment).commit();
                        break;
                    case R.id.tab_friend: transaction.replace(R.id.contentContainer, tabFriendFragment).commit();
                        break;

                    default: break;
                }
            }
        });


        SimpleDateFormat simple_date_format = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(HomeActivity.this, Broadcast.class);



        PendingIntent sender = PendingIntent.getBroadcast(HomeActivity.this, 0, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, 11, 11);

        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

/*
        for (int i = 0; i < friger.size(); i++) {
            ArrayList<Food> foodList = friger.get(i).getFoodList();
            for (int j = 0; j < foodList.size(); j++) {
                Food food = foodList.get(j);
                try {
                    Date expiration_date = simple_date_format.parse(food.getUseByDate());
                    calendar.setTime(expiration_date);


                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

        }*/
    }



    public void initFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.contentContainer, tabHomeFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로 버튼을 한번 더 누르시면 종료됩니다.",Toast.LENGTH_SHORT).show();
            return;
        }

        if(System.currentTimeMillis() <= backKeyPressedTime+2000){
            finishAffinity();
            System.runFinalization();
            System.exit(0);
        }
    }
    public void data()
    {
        final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    if(myUid.equals(snapshot.getKey())) {
                        final Iterator<DataSnapshot> iter2 = snapshot.child("grouprefriList").getChildren().iterator();

                        Iterator<DataSnapshot> iter = snapshot.child("refriList").getChildren().iterator();
                        for (long j = 0; j < snapshot.child("refriList").getChildrenCount(); j++) {
                            while (iter.hasNext()) {
                                DataSnapshot data = iter.next();
                                Refrigerator refri = new Refrigerator(data.getKey());
                                for (int i = 0; i < User.INSTANCE.getRefrigeratorList().size(); i++) {
                                    if (User.INSTANCE.getRefrigeratorList().get(i).getName().equals(refri.getName())) {
                                        User.INSTANCE.setCurrentRefrigerator(i);
                                        break;
                                    }
                                }

                                Iterator<DataSnapshot> iterator = data.getChildren().iterator();
                                while (iterator.hasNext()) {
                                    DataSnapshot food_data = iterator.next();
                                    Food newFood = food_data.getValue(Food.class);

                                    boolean isFood = false;
                                    if (User.INSTANCE.getRefrigeratorList().get(User.INSTANCE.getCurrentRefrigerator()).getFoodList().size() != 0) {
                                        for (int i = 0; i < User.INSTANCE.getRefrigeratorList().get(User.INSTANCE.getCurrentRefrigerator()).getFoodList().size(); i++) {
                                            Food exFood = User.INSTANCE.getRefrigeratorList().get(User.INSTANCE.getCurrentRefrigerator()).getFoodList().get(i);

                                            if (newFood.getFoodID() == null && newFood.getFoodName().equals(exFood.getFoodName())) {
                                                if (newFood.getUseByDate() == null) {
                                                    isFood = true;
                                                    break;
                                                } else if (exFood.getUseByDate() != null && newFood.getUseByDate().equals(exFood.getUseByDate())) {
                                                    isFood = true;
                                                    break;
                                                }
                                            } else if (newFood.getFoodID() != null && newFood.getFoodID().equals(exFood.getFoodID())) {
                                                if (newFood.getUseByDate() == null) {
                                                    isFood = true;
                                                    break;
                                                } else if (exFood.getUseByDate() != null && newFood.getUseByDate().equals(exFood.getUseByDate())) {
                                                    isFood = true;
                                                    break;
                                                }
                                            }
                                        }

                                        if (!isFood) {
                                            User.INSTANCE.getRefrigeratorList().get(User.INSTANCE.getCurrentRefrigerator()).getFoodList().add(newFood);
                                        }

                                    } else {
                                        User.INSTANCE.getRefrigeratorList().get(User.INSTANCE.getCurrentRefrigerator()).getFoodList().add(newFood);
                                    }
                                }
                            }

                        }
                        //그룹냉장고 추가
                        if (snapshot.child("grouprefriList").getKey() != null) {
                            while (iter2.hasNext()) {
                                final DataSnapshot data2 = iter2.next();

                                String a = data2.getKey();

                                if (snapshot.child("grouprefriList").child(a).child("own").getValue() != null) {
                                    final String ownUid = snapshot.child("grouprefriList").child(a).child("own").getValue().toString();
                                    User.INSTANCE.getRefrigeratorList().clear();
                                    FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                                                if (ownUid.equals(snapshot1.getKey())) {

                                                    for (long j = 0; j < snapshot1.child("refriList").child(data2.getKey().toString()).getChildrenCount(); j++) {

                                                        Iterator<DataSnapshot> iter = snapshot1.child("refriList").getChildren().iterator();


                                                        while (iter.hasNext()) {

                                                            DataSnapshot data = iter.next();
                                                            if (snapshot1.child("refriList").child(data2.getKey().toString()).getKey().equals(data.getKey())) {

                                                                Refrigerator refri = new Refrigerator(data.getKey());
                                                                User.INSTANCE.addRefrigerator(refri);

                                                                for (int i = 0; i < User.INSTANCE.getRefrigeratorList().size(); i++) {
                                                                    if (User.INSTANCE.getRefrigeratorList().get(i).getName().equals(data2.getKey())) {
                                                                        User.INSTANCE.setCurrentRefrigerator(i);
                                                                        // Log.d("???????????",User.INSTANCE.getRefrigeratorList().get(i).getName());
                                                                        break;
                                                                    }

                                                                }

                                                                Iterator<DataSnapshot> iterator = data.getChildren().iterator();
                                                                while (iterator.hasNext()) {
                                                                    DataSnapshot food_data = iterator.next();
                                                                    Food newFood = food_data.getValue(Food.class);

                                                                    boolean isFood = false;
                                                                    if (User.INSTANCE.getRefrigeratorList().get(User.INSTANCE.getCurrentRefrigerator()).getFoodList().size() != 0) {
                                                                        for (int i = 0; i < User.INSTANCE.getRefrigeratorList().get(User.INSTANCE.getCurrentRefrigerator()).getFoodList().size(); i++) {
                                                                            Food exFood = User.INSTANCE.getRefrigeratorList().get(User.INSTANCE.getCurrentRefrigerator()).getFoodList().get(i);

                                                                            if (newFood.getFoodID() == null && newFood.getFoodName().equals(exFood.getFoodName())) {
                                                                                if (newFood.getUseByDate() == null) {
                                                                                    isFood = true;
                                                                                    break;
                                                                                } else if (exFood.getUseByDate() != null && newFood.getUseByDate().equals(exFood.getUseByDate())) {
                                                                                    isFood = true;
                                                                                    break;
                                                                                }
                                                                            } else if (newFood.getFoodID() != null && newFood.getFoodID().equals(exFood.getFoodID())) {
                                                                                if (newFood.getUseByDate() == null) {
                                                                                    isFood = true;
                                                                                    break;
                                                                                } else if (exFood.getUseByDate() != null && newFood.getUseByDate().equals(exFood.getUseByDate())) {
                                                                                    isFood = true;
                                                                                    break;
                                                                                }
                                                                            }
                                                                        }

                                                                        if (!isFood) {
                                                                            User.INSTANCE.getRefrigeratorList().get(User.INSTANCE.getCurrentRefrigerator()).getFoodList().add(newFood);
                                                                        }

                                                                    } else {
                                                                        User.INSTANCE.getRefrigeratorList().get(User.INSTANCE.getCurrentRefrigerator()).getFoodList().add(newFood);
                                                                    }

                                                                }
                                                                break;

                                                            }
                                                        }
                                                    }
                                                    break;
                                                }
                                            }
                                        }


                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                    break;
                                }

                            }
                            break;
                        }

                        tabHomeFragment.setFoodList();

                        break;

                    }

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}