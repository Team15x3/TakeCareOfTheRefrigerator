package com.team15x3.caucse.takecareoftherefrigerator;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if(Build.VERSION.SDK_INT >=21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackground));
        }

        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                if(!putUserInfoToIntent(returnIntent)){
                    Toast.makeText(getApplicationContext(),"wrong input!",Toast.LENGTH_SHORT).show();
                }else{
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            }
        });

    }

    private boolean putUserInfoToIntent(Intent intent){

        String UserName, Password, ConfirmPassword, Email;

        EditText edtUserName = (EditText)findViewById(R.id.edtUserName);
        EditText edtPassword = (EditText)findViewById(R.id.edtPassword);
        EditText edtConfirmPassword = (EditText)findViewById(R.id.edtConfirmPassword);
        EditText edtEmail = (EditText)findViewById(R.id.edtEmail);

        UserName = edtUserName.getText().toString();
        Password = edtPassword.getText().toString();
        ConfirmPassword = edtConfirmPassword.getText().toString();
        Email = edtEmail.getText().toString();
        //check correction of information
        if(Password.equals(ConfirmPassword)){
            return true;
        }
        return false;

    }
}
