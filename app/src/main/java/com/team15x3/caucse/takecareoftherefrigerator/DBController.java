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
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

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

    public ArrayList<String> getMediumList(int pos){
        ArrayList<String> medi = new ArrayList<>();
        realm.beginTransaction();
        RealmResults<DBMediumCategory> mediumCategory = realm.where(DBMediumCategory.class).equalTo("bigIndex", pos).findAll();
        for(int i = 0; i<mediumCategory.size(); i++){
            DBMediumCategory cur = mediumCategory.get(i);
            medi.add(cur.getIndex(), cur.getName());
        }
        realm.commitTransaction();
        return medi;

    }

    public ArrayList<String> getSmallList(int pos){
        ArrayList<String> small = new ArrayList<>();
        realm.beginTransaction();
        RealmResults<DBSmallCategory> mediumCategory = realm.where(DBSmallCategory.class).equalTo("mediumIndex", pos).findAll();
        for(int i = 0; i<mediumCategory.size(); i++){
            DBSmallCategory cur = mediumCategory.get(i);
            small.add(cur.getIndex(), cur.getName());
        }
        realm.commitTransaction();
        return small;
    }


    public ArrayList<Integer> getParentIndex(String str){

        realm.beginTransaction();
        DBSmallCategory small = realm.where(DBSmallCategory.class).equalTo("name",str).findFirst();
        int sIdx = small.getIndex();
        int medi = small.getParentIndex();
        DBMediumCategory medium = realm.where(DBMediumCategory.class).equalTo("mediumIndex",medi).findFirst();
        int big = medium.getParentIndex();

        ArrayList<Integer> array = new ArrayList<>();
        array.add(big);
        array.add(medi);
        array.add(sIdx);

        realm.commitTransaction();
        return array;
    }
}
