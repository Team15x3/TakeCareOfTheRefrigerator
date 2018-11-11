package com.team15x3.caucse.takecareoftherefrigerator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingFrigerActivity extends AppCompatActivity {


    private final ArrayList<String> menu = new ArrayList<>(Arrays.asList("set default Refrigerator","Invite new friends","Clear the food list","Delete this refrigerator"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_friger);

    }
}
