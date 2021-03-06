package com.team15x3.caucse.takecareoftherefrigerator;

import io.realm.RealmObject;

public class DBMediumCategory extends RealmObject {
    int bigIndex;
    int mediumIndex;
    String name;

    public void setData(int b , int m, String n){
        bigIndex = b;
        mediumIndex = m;
        name = n;
    }

    public int getIndex(){ return mediumIndex;}
    public String getName(){
        return name;
    }
    public int getParentIndex(){return bigIndex;}
}
