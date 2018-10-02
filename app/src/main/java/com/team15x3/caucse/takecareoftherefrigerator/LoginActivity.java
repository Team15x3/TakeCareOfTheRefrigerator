package com.team15x3.caucse.takecareoftherefrigerator;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    //define view objects
    EditText edtUserName;
    EditText edtPassword;
    Button btnSignin;
    TextView textviewSignin;
    TextView textviewMessage;
    Button btnSearchID;
    ProgressDialog progressDialog;
    //define firebase object
    FirebaseAuth firebaseAuth;
    Button btnRegister;
    final int REGISTER_REQUEST = 10;


    //animation splash
    RelativeLayout rellay1,rellay2;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REGISTER_REQUEST && resultCode == RESULT_OK){
            Toast.makeText(getApplicationContext(),"로그인 성공",Toast.LENGTH_SHORT).show();
        }
    }

    private void userLogin(){
        String email = edtUserName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("로그인중입니다. 잠시 기다려 주세요...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "로그인 실패!", Toast.LENGTH_LONG).show();
                            /*textviewMessage.setText("로그인 실패 유형\n - password가 맞지 않습니다.\n -서버에러");*/
                        }
                    }
                });
    }



    @Override
    public void onClick(View view) {

        if(view == btnSignin) {
            userLogin();
        }
        if(view == btnSearchID) {
            startActivity(new Intent(this, FindActivity.class));
        }
        if(view== btnRegister)
        {
            startActivityForResult(new Intent(getApplicationContext(),RegisterActivity.class),REGISTER_REQUEST);
        }
    }



    private void initViews(){

        rellay1 =(RelativeLayout)findViewById(R.id.rellay1);
        rellay2 =(RelativeLayout)findViewById(R.id.rellay2);
        handler.postDelayed(runnable,2000);

        //initializig firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }


        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        /*textviewSignin= (TextView) findViewById(R.id.textViewSignin);*/
        /*textviewMessage = (TextView) findViewById(R.id.textviewMessage)*/;
        btnSearchID= (Button) findViewById(R.id.btnSearchID);
        btnSignin = (Button) findViewById(R.id.btnSignIn);
        progressDialog = new ProgressDialog(this);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        //button click event
        btnSignin.setOnClickListener(this);
        //textviewSignin.setOnClickListener(this);
        btnSearchID.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }
}
