package com.team15x3.caucse.takecareoftherefrigerator;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface APIInterface {

    @Headers({
            "DS-ApplicationKey:4ac36909-4c11-4cd6-ae3f-c0e2260c830b",
            "DS-AccessToken:6b589096-8358-4faa-9e5e-9caaed76a5bb"
    })
    @GET("foodinfo/1.0/foods/?")
    Call<ResponseBody> doGetListResources(@Query("foodType")String appkey, @Query("searchField") String searchField, @Query("searchValue") String searchValue,
                                          @Query("addSearchField") String addSearchField, @Query("addSearchValue")String addSearchValue,
                                          @Query("exSearchField")String exSearchField, @Query("exSearchValue")String exSearchValue,
                                          @Query("offset")Number offset, @Query("limit")Number limit);


}
