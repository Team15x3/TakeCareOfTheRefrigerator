package com.team15x3.caucse.takecareoftherefrigerator;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.realm.Realm;

public class DBController {
    private Realm realm;
    private Context context;

    DBController(Context con){
        context = con;
        realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void initDatabase() throws JSONException{

        realm.beginTransaction();
        saveCategoryInfoToDB(getStringFromJSON());
        realm.commitTransaction();
    }

    private String getStringFromJSON(){
        AssetManager assetManager = context.getResources().getAssets();
        try{
            AssetManager.AssetInputStream ais = (AssetManager.AssetInputStream)assetManager.open("category.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(ais));
            StringBuilder sb = new StringBuilder();
            int bufferSize = 1024*1024;

            char readBuf[] = new char[bufferSize];
            int resultSize =0;

            while((resultSize = br.read(readBuf)) != -1){
                if(resultSize == bufferSize){
                    sb.append(readBuf);
                }else{
                    for(int i =0; i<resultSize; i++){
                        sb.append(readBuf[i]);
                    }
                }
            }
            String jString = sb.toString();
            Log.d("JSON_CONTENT",jString);
            return jString;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveCategoryInfoToDB(String str) throws  JSONException{
        try{
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonBig = jsonObject.getJSONArray("DBBigCategory");
            JSONArray jsonMedium = jsonObject.getJSONArray("DBMediumCategory");
            JSONArray jsonSmall = jsonObject.getJSONArray("DBSmallCategory");

            for(int i = 0; i<jsonBig.length(); i++){
                JSONObject curBig = jsonBig.getJSONObject(i);
                DBBigCategory dbBig = realm.createObject(DBBigCategory.class);
                dbBig.setData(curBig.getInt("bigIndex"),curBig.getString("name"));
            }

            for(int i = 0; i<jsonMedium.length(); i++){
                JSONObject curMedium = jsonMedium.getJSONObject(i);
                DBMediumCategory dbM = realm.createObject(DBMediumCategory.class);
                dbM.setData(curMedium.getInt("bigIndex"), curMedium.getInt("mediumIndex"),curMedium.getString("name"));
            }

            for( int i = 0; i<jsonSmall.length(); i++){
                JSONObject curSmall = jsonSmall.getJSONObject(i);
                DBSmallCategory dbS = realm.createObject(DBSmallCategory.class);
                dbS.setData(curSmall.getInt("mediumIndex"),curSmall.getInt("smallIndex"),curSmall.getString("name"));
            }
        }catch (Exception e){
            return;
        }
    }
}
