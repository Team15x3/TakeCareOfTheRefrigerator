package com.team15x3.caucse.takecareoftherefrigerator;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MultipleResources {

    @SerializedName("page")
    public List<Pageum> page = null;

    public class Pageum{

        @SerializedName("offset")
        public Number offset;

        @SerializedName("limit")
        public Number limit;

        @SerializedName("resultCount")
        public Number resultCount;

        @SerializedName("totalCount")
        public Number totalCount;
    }

    @SerializedName("items")
    public List<itemsum> items = null;

    public class itemsum{
        @SerializedName("foodId")
        public String foodId;

        @SerializedName("mainNutrientServingMeasureAmount")
        public Number mainNutrientServingMeasureAmount;

        @SerializedName("volumeUnit")
        public String volumeUnit;

        @SerializedName("thumbnailUrl")
        public String thumbnailUrl;

        @SerializedName("registerDate")
        public String registerDate;

        @SerializedName("specificNutrientIncludeYn")
        public String specificNutrientIncludeYn;

        @SerializedName("volume")
        public Number volume;

        @SerializedName("foodType")
        public String foodType;

        @SerializedName("bacode")
        public String bacode;

        @SerializedName("mainNutrients")
        public List<mainNu> mainNutrients = null;

        public class mainNu{
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

        @SerializedName("vendors")
        public String vendors;

        @SerializedName("foodName")
        public String foodName;


//        @SerializedName("mainNutrientServingMeasureUnit")
//        public String mainNutrientServingMeasureUnit;


    }

}
