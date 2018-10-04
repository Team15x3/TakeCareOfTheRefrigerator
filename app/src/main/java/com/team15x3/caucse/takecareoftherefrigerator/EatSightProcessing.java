package com.team15x3.caucse.takecareoftherefrigerator;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EatSightProcessing {

    private Food mFood;

    public void addFoodInformationFromEatSightAPI(String barcode) {
        EatSightInterface service = EatSightClient.createService(EatSightInterface.class);;

        Call<ResponseBody> request = service.doGetListResources("ALL","barcode",
                "18801073181905",null,
                null,null,null,0,2);

        request.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        String jsonInfo = response.body().string();
                        JsonObject jsonObject = new JsonParser().parse(jsonInfo).getAsJsonObject();

                        JsonArray jsonArray = jsonObject.getAsJsonArray("items");
                        JsonElement jsonElement = jsonArray.get(0);

                        for (int i = 0; i < jsonArray.size(); i++) {
                            JsonObject jsonObject1 = jsonElement.getAsJsonObject();

                            String foodID = jsonObject1.get("foodId").getAsString();
                            String foodName = jsonObject1.get("foodName").getAsString();
                            String barcode = jsonObject1.get("barcode").getAsString();

                            String foodType = jsonObject1.get("foodType").getAsString();
                            String thumbnailURL = jsonObject1.get("thumbnailUrl").getAsString();

                            //mFood = new Food(foodID, foodName, barcode, foodType, thumbnailURL);

                            //List<String> mainNutrients = jsonObject1.get("mainNutrients").getAsJsonArray();
                            //JsonArray jsonArray1 = jsonElement.getAsJsonArray();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();;
            }
        });
    }
}
