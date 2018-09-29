package com.team15x3.caucse.takecareoftherefrigerator;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;
    private EditText edtUserName;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private EditText edtEmail;
    public String email = "";
    public String username = "";
    private String password = "";
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    public static String checkuid;
    public String jsonparing;
    private ChildEventListener mChild;

    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        edtEmail = findViewById(R.id.edtEmail);
        //databaseReference = FirebaseDatabase.getInstance().getReference("recipe");
        edtUserName = (EditText)findViewById(R.id.edtUserName);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        initDatabase();
        //output();

          databaseReference = firebaseDatabase.getReference("users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                   String data1 = dataSnapshot.getValue().toString();
                    //String data2 = databaseReference.orderByChild("users").toString();

                    // child 내에 있는 데이터만큼 반복합니다.
                //String data = dataSnapshot.getChildren().toString();

               // Log.d("ooooo",data);
                    Log.d("ooooo",data1);
                   // Log.d("ooooo",data2);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




      /*  if(Build.VERSION.SDK_INT >=21) {
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
        });*/

    }

/*    private boolean putUserInfoToIntent(Intent intent){

        String UserName, Password, ConfirmPassword, Email;

        edtUserName = (EditText)findViewById(R.id.edtUserName);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        edtConfirmPassword = (EditText)findViewById(R.id.edtConfirmPassword);
        edtEmail = (EditText)findViewById(R.id.edtEmail);

        UserName = edtUserName.getText().toString();
        Password = edtPassword.getText().toString();
        ConfirmPassword = edtConfirmPassword.getText().toString();
        Email = edtEmail.getText().toString();
        //check correction of information
        if(Password.equals(ConfirmPassword)){
            return true;
    }
        return false;

    }*/

    public void singUp(View view) {
        email = edtEmail.getText().toString();
        password = edtPassword.getText().toString();
        username  = edtUserName.getText().toString();

        if(isValidEmail() && isValidPasswd()) {
            createUser(email, password,username);
        }
    }

    public void signIn(View view) {
        email = edtEmail.getText().toString();
        password = edtPassword.getText().toString();


    }

    // 이메일 유효성 검사
    private boolean isValidEmail() {
        if (email.isEmpty()) {
            // 이메일 공백
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // 이메일 형식 불일치
            return false;
        } else {
            return true;
        }
    }


    private boolean isValidPasswd() {
        if (password.isEmpty()) {
            // 비밀번호 공백
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {

            return false;
        } else {
            return true;
        }
    }

    private void createUser(final String email, final String password,final String username) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        final String uid = task.getResult().getUser().getUid();
                        if (task.isSuccessful()) {
                            // 회원가입 성공
                            Toast.makeText(RegisterActivity.this, R.string.success_signup, Toast.LENGTH_SHORT).show();
                            checkuid = uid;
                            Log.d("ppppp",uid);
                            writeNewUser("1234","NEW");

                           // databaseReference.push().child().child("users").setValue(email);
                            Log.d("1111111111111111111","dddddddddddddddd");
                        } else {
                            // 회원가입 실패
                            Toast.makeText(RegisterActivity.this, R.string.failed_signup, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    private void writeNewUser(String usename, String email1) {
        User user = new User(username, email);
        Log.d("ppppp",checkuid);

        databaseReference.child(checkuid).setValue(user);
    }
    public class User {

        public String username;
        public String email;

        public User() {

        }

        public User(String username, String email) {
            this.username = username;
            this.email = email;
        }

    }

    //레시피 보내기
    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("레시피+재료정보_20180912192458.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;

    }

    public void output(){

        JSONObject obj = new JSONObject();
        jsonparing =  loadJSONFromAsset().toString();
        Log.d("www","www");
        databaseReference.child("recipe3").setValue(jsonparing);
        Log.d("eee","eee");
    }

    private void initDatabase() {

        //databaseReference = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("recipe");
        databaseReference.child("users").setValue(checkuid);

        mChild = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.addChildEventListener(mChild);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(mChild);
    }
}

