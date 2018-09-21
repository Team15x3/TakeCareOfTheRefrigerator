package com.team15x3.caucse.takecareoftherefrigerator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MultipleResources {

    //@SerializedName("page")
    //@Expose
    //public List<Pageum> page = null;

    //public class Pageum{

        @SerializedName("offset")
        @Expose
        public Number offset;

        @SerializedName("limit")
        @Expose
        public Number limit;

        @SerializedName("resultCount")
        @Expose
        public Number resultCount;

        @SerializedName("totalCount")
        @Expose
        public Number totalCount;
    //}

    //@SerializedName("items")
    //@Expose
    //public List<itemsum> items = null;

    //public class itemsum{
        @SerializedName("foodId")
        @Expose
        public String foodId;

        @SerializedName("mainNutrientServingMeasureAmount")
        @Expose
        public Number mainNutrientServingMeasureAmount;

        @SerializedName("volumeUnit")
        @Expose
        public String volumeUnit;

        @SerializedName("thumbnailUrl")
        @Expose
        public String thumbnailUrl;

        @SerializedName("registerDate")
        @Expose
        public String registerDate;

        @SerializedName("specificNutrientIncludeYn")
        @Expose
        public String specificNutrientIncludeYn;

        @SerializedName("volume")
        @Expose
        public Number volume;

        @SerializedName("foodType")
        @Expose
        public String foodType;

        @SerializedName("bacode")
        @Expose
        public String bacode;

       // @SerializedName("mainNutrients")
        //@Expose
        //public List<mainNu> mainNutrients = null;

       // public class mainNu{
           @SerializedName("servingAmount")
            @Expose
            public Number servingAmount;

            @SerializedName("servingAmountUnit")
            @Expose
            public String servingAmountUnit;

            @SerializedName("rate")
            @Expose
            public Number rate;

            @SerializedName("nutrientName")
            @Expose
            public String nutrientName;

            @SerializedName("nutrientId")
            @Expose
            public String nutrientId;

     //   }

        @SerializedName("vendors")
        @Expose
        public String vendors;

        @SerializedName("foodName")
        @Expose
        public String foodName;


//        @SerializedName("mainNutrientServingMeasureUnit")
//        public String mainNutrientServingMeasureUnit;


   // }

}
