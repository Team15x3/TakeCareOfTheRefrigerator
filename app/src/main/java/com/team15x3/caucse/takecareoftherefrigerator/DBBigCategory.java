package com.team15x3.caucse.takecareoftherefrigerator;


import io.realm.RealmList;
import io.realm.RealmObject;

public class DBBigCategory extends RealmObject {
    int bigIndex;
    String name;

    public void setData(int i, String n){
        bigIndex = i;
        name = n;
    }
}

