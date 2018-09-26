package com.team15x3.caucse.takecareoftherefrigerator;

import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

public class HomeActivity extends FragmentActivity {

    BottomBar bottomBar;
    private TabHomeFragment tabHomeFragment;
    private TabRecipeFragment tabRecipeFragment;
    private TabSettingFragment tabSettingFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabHomeFragment = new TabHomeFragment();
        tabRecipeFragment = new TabRecipeFragment();
        tabSettingFragment = new TabSettingFragment();

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
                    default: break;
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        //super.onBackPressed();
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }

    public void initFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.contentContainer, tabHomeFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


}
