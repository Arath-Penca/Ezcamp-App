package com.example.ezcamp.FAKE_DATA;

import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds.CampgroundsResponse;
import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds.DataCampgrounds;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FakeDataApi {



    @GET("campgrounds")
    Flowable<DataCampgrounds> getCampgrounds(
            @Query("parkCode") String parkCode,
            @Query("stateCode") String stateCode,
            @Query("limit") Integer limit,
            @Query("start") Integer startOn,
            @Query("api_key") String API_KEY
    );


}
