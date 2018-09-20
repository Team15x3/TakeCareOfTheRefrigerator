package com.team15x3.caucse.takecareoftherefrigerator;

import android.app.TabActivity;
import android.content.Intent;
import android.icu.util.Output;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.util.Log.e;

public class MainActivity extends AppCompatActivity {
    String myBarcode;
    Button btnOpenBarcode;
    TextView text;

    APIInterface apiInterface;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode,data);
        myBarcode = result.getContents(); //get barcode number
        Toast.makeText(this,""+myBarcode,Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        text =(TextView)findViewById(R.id.text);
        btnOpenBarcode = (Button)findViewById(R.id.btnOpenBarcode);
        btnOpenBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new IntentIntegrator(MainActivity.this).initiateScan();
            }


        });

*/
        apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<FoodLists> call = apiInterface.doGetListResources("ALL","barcode","18801073181905",null
                ,null,null,null,0,2);
        call.enqueue(new Callback<FoodLists>() {
            @Override
            public void onResponse(Call<FoodLists> call, Response<FoodLists> response) {

                Log.d("TAG",""+response.body().getSize());

            }

            @Override
            public void onFailure(Call<FoodLists> call, Throwable t) {

                Log.d("TAG",t.getLocalizedMessage());
                call.cancel();
            }
        });

        /*
        Call<MultipleResources> call = apiInterface.doGetListResources("ALL","barcode","18801073181905",null
                ,null,null,null,0,2);
        call.enqueue(new Callback<MultipleResources>() {
            @Override
            public void onResponse(Call<MultipleResources> call, Response<MultipleResources> response) {

                MultipleResources resource = response.body();
                List<MultipleResources.itemsum> itemdata= resource.items;

            }
            @Override
            public void onFailure(Call<MultipleResources> call, Throwable t) {

            }
        });
*/
    }


}


