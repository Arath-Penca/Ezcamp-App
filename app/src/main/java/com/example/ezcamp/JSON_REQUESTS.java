package com.example.ezcamp;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Alerts.AlertsResponse;
import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Articles.ArticlesResponse;
import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds.CampgroundsResponse;
import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.NationalParkApi;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JSON_REQUESTS {

    public FeedFragmentListener activityCommander;
    final private String TAG = "JSON REQUESTS";
    private NationalParkApi nationalParkApi;




    public JSON_REQUESTS(FeedFragmentListener listener){
        activityCommander = listener;
    }

    public void apiNationalParkRequestCampgrounds(String parkCode, String stateCode, Integer limit, Integer startOn){

        final String API_KEY= "Aa4Yrekf9m2MhKbcb1J8Bzzyx6SVNRBAtJlpFdLr";
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100,TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://developer.nps.gov/api/v1/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        nationalParkApi = retrofit.create(NationalParkApi.class);
        Call<CampgroundsResponse> call = nationalParkApi.getCampgrounds(parkCode,stateCode,limit,startOn,API_KEY);


        call.enqueue(new Callback<CampgroundsResponse>() {
            @Override
            public void onResponse(Call<CampgroundsResponse> call, retrofit2.Response<CampgroundsResponse> response) {
                if(!response.isSuccessful()){
                    return;
                }
                CampgroundsResponse campgroundsResponse = response.body();
                activityCommander.gotCampgrounds(campgroundsResponse);
            }
            @Override
            public void onFailure(Call<CampgroundsResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void apiNationalParkRequestAlerts(String parkCode, String stateCode, Integer limit, Integer startOn){

        final String API_KEY= "Aa4Yrekf9m2MhKbcb1J8Bzzyx6SVNRBAtJlpFdLr";

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100,TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://developer.nps.gov/api/v1/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        nationalParkApi = retrofit.create(NationalParkApi.class);
        Call<AlertsResponse> call = nationalParkApi.getAlerts(parkCode,stateCode,limit,startOn,API_KEY);


        call.enqueue(new Callback<AlertsResponse>() {
            @Override
            public void onResponse(Call<AlertsResponse> call, retrofit2.Response<AlertsResponse> response) {
                if(!response.isSuccessful()){
                    return;
                }

                AlertsResponse alertsResponse = response.body();
                activityCommander.gotAlert(alertsResponse);

            }
            @Override
            public void onFailure(Call<AlertsResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void apiNationalParkRequestArticles(String parkCode, String stateCode, Integer limit, Integer startOn){

        final String API_KEY= "Aa4Yrekf9m2MhKbcb1J8Bzzyx6SVNRBAtJlpFdLr";

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100,TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://developer.nps.gov/api/v1/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        nationalParkApi = retrofit.create(NationalParkApi.class);
        Call<ArticlesResponse> call = nationalParkApi.getArticles(parkCode,stateCode,limit,startOn,API_KEY);


        call.enqueue(new Callback<ArticlesResponse>() {
            @Override
            public void onResponse(Call<ArticlesResponse> call, retrofit2.Response<ArticlesResponse> response) {
                if(!response.isSuccessful()){
                    return;
                }
                ArticlesResponse articlesResponse = response.body();
                activityCommander.gotArticles(articlesResponse);
            }
            @Override
            public void onFailure(Call<ArticlesResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }



    public interface FeedFragmentListener{
        void gotAlert(AlertsResponse response);
        void gotCampgrounds(CampgroundsResponse response);
        void gotArticles(ArticlesResponse response);
    }


}
/*
ArrayList<DataCampgrounds> dataCampgroundsObject;
    CampgroundsResponse campgroundsResponses = response.body();
                dataCampgroundsObject = new ArrayList<>(Arrays.asList(campgroundsResponses.getData()));*/