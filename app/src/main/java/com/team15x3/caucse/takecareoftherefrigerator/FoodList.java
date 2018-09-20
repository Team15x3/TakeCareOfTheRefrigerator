package com.team15x3.caucse.takecareoftherefrigerator;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FoodList {
    //have to fix

        @SerializedName("servingAmount")
        public Number servingAmount;

        @SerializedName("servingAmountUnit")
        public String servingAmountUnit;

        @SerializedName("rate")
        public Number rate;

        @SerializedName("nutrientName")
        public String nutrientName;

        @SerializedName("nutrientId")
        public String nutrientId;

}
