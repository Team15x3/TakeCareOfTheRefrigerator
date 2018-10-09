package com.team15x3.caucse.takecareoftherefrigerator;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class InsertFoodActivity extends AppCompatActivity implements View.OnClickListener{

    private final int NO_DATA = 3;
    Button btnBarcode, btnAdd, btnCancle;
    String myBarcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_food);

        btnBarcode = (Button)findViewById(R.id.btnBarcodeInsert);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnCancle = (Button)findViewById(R.id.btnCancle);


        btnBarcode.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnCancle.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        if(view == btnBarcode){
            new IntentIntegrator(this).initiateScan();
        }


        if(view == btnAdd){



        }


        if(view == btnCancle){
            setResult(NO_DATA);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode,data);
        myBarcode = result.getContents(); //get barcode number
        Toast.makeText(getApplicationContext(),myBarcode,Toast.LENGTH_SHORT).show();
        //api parsing , get information

        /*APIProcessing api = new APIProcessing();
        Food f = api.parseJsonFromBarcode(myBarcode);*/
    }
}
