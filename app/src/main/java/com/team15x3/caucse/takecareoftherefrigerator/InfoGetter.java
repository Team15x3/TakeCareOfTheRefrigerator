package com.team15x3.caucse.takecareoftherefrigerator;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

//The class that get the information of ingredient, with barcode number
public class InfoGetter extends AsyncTask<String,Void,String> {

    private String barcode = "";
    String clientKye = "4ac36909-4c11-4cd6-ae3f-c0e2260c830b";
    private String str, receivMsg;

    @Override
    protected String doInBackground(String... strings) {
        URL eatSight = null;
        try{
            eatSight  =new URL("https://apis.eatsight.com/foodinfo/1.0/foods");
            HttpsURLConnection myConnection = (HttpsURLConnection)eatSight.openConnection();
            myConnection.setRequestProperty("User-Agent","test");
            myConnection.setRequestProperty("Content-Type","application/json");
            myConnection.setDoOutput(true);
            myConnection.setDoInput(true);

            OutputStream os = myConnection.getOutputStream();
            int responseCode = myConnection.getResponseCode();
            if(responseCode == 200){
                Log.i("check","success");
            }else{
                Log.i("check","fail");
            }
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
    //private final String ID  = ""

    public void setBarcodeNumber(String barcode){
        this.barcode = barcode;
    }
}
