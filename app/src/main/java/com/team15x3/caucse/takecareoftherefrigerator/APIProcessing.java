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

public class APIProcessing {

    protected APIInterface mApiInterface = APIClient.getClient().create(APIInterface.class);;

    public void parseJsonFromBarcode(String barcode) {
        Call<EatSightAPI> call = mApiInterface.getFoodInformation("ALL", "barcode",
                barcode, null, null, null,
                null, 0, 2);

        call.enqueue(new Callback<EatSightAPI>() {
            @Override
            public void onResponse(Call<EatSightAPI> call, Response<EatSightAPI> response) {
                if (response.isSuccessful()) {
                    EatSightAPI eatSightAPI = response.body();
                    ArrayList<Food> foodArrayList = eatSightAPI.getFoodList();

                    Food food = foodArrayList.get(0);
                }
            }

            @Override
            public void onFailure(Call<EatSightAPI> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void parseJsonFromFoodID(String foodID) {
        Call<ResponseBody> call = mApiInterface.getDetailFoodInformation(foodID);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String jsonInfo = response.body().string();

                        Log.d("json", jsonInfo);

                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
