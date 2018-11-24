package com.team15x3.caucse.takecareoftherefrigerator;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import java.util.Iterator;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //define view objects
    EditText edtUserName;
    EditText edtPassword;
    Button btnSignin;
    TextView textviewSignin;
    TextView textviewMessage;
    DBController dbController;
    Button btnSearchID;
    ProgressDialog progressDialog;
    Button btnRegister;
    final int REGISTER_REQUEST = 10;
    CallbackManager mCallbackManager;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    //animation splash
    RelativeLayout rellay1, rellay2;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {



            if (firebaseAuth.getCurrentUser() != null) {
               homeActivityIntent();
            } else {
                rellay1.setVisibility(View.VISIBLE);
                rellay2.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        dbController = new DBController(this);
        checkFirstRun();
        initViews();
        firebaseAuth = FirebaseAuth.getInstance();
        //firebaseAuth.signOut();
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTER_REQUEST && resultCode == RESULT_OK) {
            Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
        }
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void userLogin() {
        String email = edtUserName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }



        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.setMessage("로그인중입니다. 잠시 기다려 주세요...");
                            progressDialog.show();
                            progressDialog.dismiss();

                            homeActivityIntent();
                        } else {
                            Toast.makeText(getApplicationContext(), "로그인 실패!", Toast.LENGTH_LONG).show();
                            /*textviewMessage.setText("로그인 실패 유형\n - password가 맞지 않습니다.\n -서버에러");*/
                        }
                    }
                });
    }


    @Override
    public void onClick(View view) {

        if (view == btnSignin) {
            userLogin();
        }
        if (view == btnSearchID) {
            startActivity(new Intent(this, FindActivity.class));
        }
        if (view == btnRegister) {
            startActivityForResult(new Intent(getApplicationContext(), RegisterActivity.class), REGISTER_REQUEST);
        }
    }


    private void initViews() {

        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);
        handler.postDelayed(runnable, 2000);

        //initializig firebase auth object

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(LoginActivity.this, "Login signed in", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Login signed out", Toast.LENGTH_SHORT).show();
                }
            }
        };


        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        /*textviewSignin= (TextView) findViewById(R.id.textViewSignin);*/
        /*textviewMessage = (TextView) findViewById(R.id.textviewMessage)*/
        ;
        btnSearchID = (Button) findViewById(R.id.btnSearchID);
        btnSignin = (Button) findViewById(R.id.btnSignIn);
        progressDialog = new ProgressDialog(this);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        //button click event
        btnSignin.setOnClickListener(this);
        btnSearchID.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("token", token.toString());

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            //Toast.makeText(LoginActivity.this, "Facebook Login Failed", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                        } else {
                            //Toast.makeText(LoginActivity.this, "Facebook Login Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }


    private void checkFirstRun() {
        SharedPreferences prefs = getSharedPreferences("isFirst", MODE_PRIVATE);
        Log.d("CHECK_FIRST_RUN", "FIRST OPENED");
        boolean isFirstRun = prefs.getBoolean("isFirst", false);
        if (!isFirstRun) {
            try {
                dbController.initDatabase();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isFirst", true);
                editor.commit();
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("DBCONTROLLER_ERROR", "fail to save the data to database");
            }
        }
    }
    public void homeActivityIntent()
    {
        finish();
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }
}