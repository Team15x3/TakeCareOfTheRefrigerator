package com.team15x3.caucse.takecareoftherefrigerator;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeActivity extends FragmentActivity {

    BottomBar bottomBar;
    private TabHomeFragment tabHomeFragment;
    private TabRecipeFragment tabRecipeFragment;
    private TabSettingFragment tabSettingFragment;
    private TabFriendFragment tabFriendFragment;

    private ArrayList<Refrigerator> friger;
    private long backKeyPressedTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

      /*  Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(HomeActivity.this, R.color.transparent));
*/

        friger = User.INSTANCE.getRefrigeratorList();

        tabHomeFragment = new TabHomeFragment();
        tabRecipeFragment = new TabRecipeFragment();
        tabSettingFragment = new TabSettingFragment();
        tabFriendFragment = new TabFriendFragment();

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


        for (int i = 0; i < friger.size(); i++) {
            ArrayList<Food> foodList = friger.get(i).getFoodList();
            for (int j = 0; j < foodList.size(); j++) {
                Food food = foodList.get(j);
                try {
                    Date expiration_date = simple_date_format.parse(food.getUseByDate());
                    calendar.setTime(expiration_date);

                    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

        }
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


}
