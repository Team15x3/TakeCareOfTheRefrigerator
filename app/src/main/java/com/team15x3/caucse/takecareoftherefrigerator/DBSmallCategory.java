package com.team15x3.caucse.takecareoftherefrigerator;


import io.realm.RealmObject;

public class DBSmallCategory extends RealmObject {
    int bigIndex;
    int mediumIndex;
    int smallIndex;
    String name;

    public void setData(int b, int m, int s, String n){
        bigIndex = b;
        mediumIndex = m;
        smallIndex = s;
        name=n;
    }

    public int getIndex(){ return smallIndex;}
    public String getName(){
        return name;
    }
    public int getParentIndex(){return mediumIndex;}
    public int getBigIndex(){ return bigIndex;}
}
