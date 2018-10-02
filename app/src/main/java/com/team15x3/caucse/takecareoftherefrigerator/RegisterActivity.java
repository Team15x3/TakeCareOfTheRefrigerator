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
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
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

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent returnIntent = new Intent();
                if(checkValid(returnIntent)){
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"wrong input",Toast.LENGTH_SHORT).show();
                }
            }
        });

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

    private void initViews(){

        firebaseAuth = FirebaseAuth.getInstance();
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        edtEmail = findViewById(R.id.edtEmail);
        //databaseReference = FirebaseDatabase.getInstance().getReference("recipe");
        edtUserName = (EditText)findViewById(R.id.edtUserName);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        btnRegister = (Button)findViewById(R.id.btnSignup);


    }

    private boolean checkValid(Intent intent){

        String usrname = edtUserName.getText().toString();
        String password = edtPassword.getText().toString();
        String confirmPassword = edtPassword.getText().toString();
        String email = edtEmail.getText().toString();

        //check username
        if(usrname.equals("")) return false;

        //check password
        if (password.isEmpty() || !PASSWORD_PATTERN.matcher(password).matches() || !password.equals(confirmPassword)) return false;

        //check email
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) return false;


        createUser(email, password,usrname);
        return true;
    }
}

