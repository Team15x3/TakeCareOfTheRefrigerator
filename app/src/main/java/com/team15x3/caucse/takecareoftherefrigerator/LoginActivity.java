package com.team15x3.caucse.takecareoftherefrigerator;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(Build.VERSION.SDK_INT >=21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackground));
        }
    }
}
