package com.team15x3.caucse.takecareoftherefrigerator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class EatSightAPI {

    @SerializedName("items")
    @Expose
    private ArrayList<Food> mFoodList = new ArrayList<Food>();

    @SerializedName("page")
    @Expose
    private Page mPage;

    public ArrayList<Food> getFoodList() {
        return mFoodList;
    }

    public Page getPage() { return mPage; }
}

class Page {

    @SerializedName("offset")
    @Expose
    private Number offset;

    @SerializedName("resultCount")
    @Expose
    private Number resultCount;

    @SerializedName("totalCount")
    @Expose
    private Number totalCount;

    public int getOffset() { return offset.intValue(); }

    public int getResultCount() { return resultCount.intValue(); }

    public int getTotalCount() { return totalCount.intValue(); }
}