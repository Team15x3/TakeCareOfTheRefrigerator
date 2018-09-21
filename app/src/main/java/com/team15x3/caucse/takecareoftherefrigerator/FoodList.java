package com.team15x3.caucse.takecareoftherefrigerator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FoodList {

    @SerializedName("foodlist")
    private FoodList foodlist;

    @SerializedName("servingAmount")
    @Expose
    private Number servingAmount;

    @SerializedName("servingAmountUnit")
    @Expose
    private String servingAmountUnit;

    @SerializedName("rate")
    @Expose
    private Number rate;

    @SerializedName("nutrientName")
    @Expose
    private String nutrientName;

    @SerializedName("nutrientId")
    @Expose
    private String nutrientId;


    public FoodList(Number servingAmount, String servingAmountUnit, Number rate, String nutrientName,
                    String nutrientId)
    {
        this.nutrientId = nutrientId;
        this.nutrientName = nutrientName;
        this.rate = rate;
        this.servingAmount = servingAmount;
        this.servingAmountUnit = servingAmountUnit;
    }

    public Number getRate() {
        return rate;
    }

    public Number getServingAmount() {
        return servingAmount;
    }

    public String getNutrientId() {
        return nutrientId;
    }

    public String getNutrientName() {
        return nutrientName;
    }

    public String getServingAmountUnit() {
        return servingAmountUnit;
    }


}
