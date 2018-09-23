package com.team15x3.caucse.takecareoftherefrigerator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button btnRegister, btnSignIn;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(Build.VERSION.SDK_INT >=21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackground));
        }

        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkLoginInfo()){
                    Intent mainActivityIntent = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(mainActivityIntent);
                    finish();
                }
            }
        });

        //register page
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivityForResult(registerIntent,0);
            }
        });
    }

    private boolean checkLoginInfo(){
        EditText edtID = (EditText)findViewById(R.id.edtUserName);
        EditText edtPassWord = (EditText)findViewById(R.id.edtPassword);

        String ID = edtID.getText().toString();
        String Password = edtPassWord.getText().toString();

        //To do
        return true;
    }
}

