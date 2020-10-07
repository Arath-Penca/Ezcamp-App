package com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE;

import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Alerts.AlertsResponse;
import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Articles.ArticlesResponse;
import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds.CampgroundsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NationalParkApi {

    @GET ("campgrounds")
    Call<CampgroundsResponse> getCampgrounds(
            @Query("parkCode") String parkCode,
            @Query("stateCode") String stateCode,
            @Query("limit") Integer limit,
            @Query("start") Integer startOn,
            @Query("api_key") String API_KEY
    );

    @GET("alerts")
    Call<AlertsResponse> getAlerts(
            @Query("parkCode") String parkCode,
            @Query("stateCode") String stateCode,
            @Query("limit") Integer limit,
            @Query("start") Integer startOn,
            @Query("api_key") String API_KEY
    );

    @GET("articles")
    Call<ArticlesResponse> getArticles(
            @Query("parkCode") String parkCode,
            @Query("stateCode") String stateCode,
            @Query("limit") Integer limit,
            @Query("start") Integer startOn,
            @Query("api_key") String API_KEY
    );
}

//void getCampgrounds(Call<List<CampgroundsResponse>> cb);