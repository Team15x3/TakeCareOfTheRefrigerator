package com.team15x3.caucse.takecareoftherefrigerator;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/* If you want to receive data, */
/* use APIProcessing class */

public class APIProcessing {

    protected Food mFood;
    protected APIInterface mApiInterface = APIClient.getClient().create(APIInterface.class);;

    /* get food information from barcode */

}
