package com.example.ezcamp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Alerts.AlertsResponse;
import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Alerts.DataAlerts;
import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Articles.ArticlesResponse;
import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Articles.DataArticles;
import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds.CampgroundsResponse;
import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds.DataCampgrounds;
import com.example.ezcamp.RECYCLE_VIEW_CLASS_PACKAGE.FeedItem;
import com.example.ezcamp.RECYCLE_VIEW_CLASS_PACKAGE.PostsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentFeed extends Fragment implements JSON_REQUESTS.FeedFragmentListener, PostsAdapter.PostAdapterListener {

    public RecyclerView Feed;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    final private String TAG = "testing";

    private JSON_REQUESTS json;
    private ArrayList<FeedItem> arrayFeedItem;
    private AlertsResponse oAlertResponse;
    private ArrayList<DataAlerts> arrayDataAlerts;
    private ArticlesResponse oArticlesResponse;
    private ArrayList<DataArticles> arrayDataArticles;
    private CampgroundsResponse oCampgroundsResponse;
    private ArrayList<DataCampgrounds> arrayDataCampgrounds;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        // Create objects
        json = new JSON_REQUESTS(this);
        oCampgroundsResponse = new CampgroundsResponse();
        oAlertResponse = new AlertsResponse();
        oArticlesResponse = new ArticlesResponse();
        arrayFeedItem = new ArrayList<>();
        progressBar = view.findViewById(R.id.ProgressBarFeed);


        // set up
        Feed = view.findViewById(R.id.RecycleViewFeed);

        // Request
        // this comment below would grab the data, turned off due changed plans
        //json.apiNationalParkRequestCampgrounds(null, "FL",25,1);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
               getFeedCamps();
            }
        }, 400);


        return view;
    }


    @Override
    public void webGoTo(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        this.startActivity(browserIntent);
    }

    @Override
    public void gotAlert(AlertsResponse response) {

    }

    @Override
    public void gotArticles(ArticlesResponse response) {

    }

    @Override
    public void gotCampgrounds(CampgroundsResponse response) {




    }

    public void getFeedCamps(){


        progressBar.setVisibility(View.GONE);
        // Get information from each campground and insert it into FeedItem array // due to time, we had to reicve fake data
        try {
            // although its not fake data as the data has already been pre-downloaded

            JSONObject mainObject = new JSONObject(getDataFromAssets("fakedata.txt"));
            JSONArray data = mainObject.getJSONArray("data");

            for(int i = 0; i < data.length(); i++){

                try {
                    JSONObject object = data.getJSONObject(i);
                    String type = object.getString("type");
                    if(type.equals("Camp")){
                        arrayFeedItem.add(new FeedItem(object.getString("image"),object.getString("name"),object.getString("about"),object.getString("image")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                // Display the results
                mLayoutManager = new LinearLayoutManager(getActivity());
                mLayoutManager.isAutoMeasureEnabled();
                mAdapter = new PostsAdapter(arrayFeedItem, this, getActivity());
                Feed.setAdapter(mAdapter);
                Feed.setLayoutManager(mLayoutManager);

            }




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public JSONObject checkTwo(String title){
        String markerTitle = title;
        JSONObject object = null;
        try {
            final String NULL = "";
            // first check if the big one has it
            JSONObject mainObject = new JSONObject(getDataFromAssets("fakedata.txt"));
            JSONArray data = mainObject.getJSONArray("data");

            for(int i = 0; i < data.length(); i++) {
                JSONObject Campsite = data.getJSONObject(i);
                title = Campsite.getString("name");

                if (markerTitle.equals(title)) {
                    object = Campsite;
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "getDataForMarker: Error, something went wrong with jsons");
        }
        return object;

    }

    public String getDataFromAssets(String fileName){
        try {

            InputStream inputStream = getContext().getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String text = new String(buffer);
            return text;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}

