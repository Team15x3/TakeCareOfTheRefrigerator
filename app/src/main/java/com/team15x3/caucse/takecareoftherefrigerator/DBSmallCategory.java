package com.team15x3.caucse.takecareoftherefrigerator;


import io.realm.RealmObject;

public class DBSmallCategory extends RealmObject {
    int mediumIndex;
    int smallIndex;
    String name;

    public void setData(int m, int s, String n){
        mediumIndex = m;
        smallIndex = s;
        name=n;
    }
}
