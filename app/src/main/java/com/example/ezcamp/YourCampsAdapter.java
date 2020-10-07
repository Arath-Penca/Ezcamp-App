package com.example.ezcamp;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class YourCampsAdapter extends RecyclerView.Adapter<YourCampsAdapter.YourCampHolder> {


    private ArrayList<YourCampsItem> aFeedItem;
    public YourCampsAdapterListener activityCommander;
    public Activity activity;

    public static class YourCampHolder extends RecyclerView.ViewHolder {

        public ImageView mCampImage;
        public TextView mCampName;
        public TextView mCampWeather;
        public TextView mCampAlerts;
        public Button mMoreOptions;
        public Button mRemoveCamp;

        public YourCampHolder(@NonNull View itemView) {
            super(itemView);

            mCampImage  = itemView.findViewById(R.id.CampImage);
            mCampName = itemView.findViewById(R.id.CampName);
            mCampWeather = itemView.findViewById(R.id.CampWeather);
            mCampAlerts = itemView.findViewById(R.id.CampAlerts);
            mMoreOptions = itemView.findViewById(R.id.ButtonEdit);
            mRemoveCamp = itemView.findViewById(R.id.ButtonInfo);
        }
    }

    public YourCampsAdapter (ArrayList<YourCampsItem> aFeedItem, YourCampsAdapterListener listener, Activity activity){
        this.aFeedItem = aFeedItem;
        activityCommander = listener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public YourCampHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_your_camps, parent, false);
        YourCampHolder postViewHolder = new YourCampHolder(v);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull YourCampHolder holder, final int position) {
        final YourCampsItem oFeedItem = aFeedItem.get(position);
        final String no_image_url = "https://semantic-ui.com/images/wireframe/image.png";

        holder.mCampName.setText(oFeedItem.getName());
        Glide.with(holder.itemView).load(oFeedItem.getImageUrl()).into(holder.mCampImage);
        holder.mCampWeather.setText(oFeedItem.getWeatherOnCampDay());
        holder.mCampAlerts.setText(oFeedItem.getAlerts());
        holder.mMoreOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // more options i gues
            }
        });

        holder.mRemoveCamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ViewCampActivity.class);
                intent.putExtra("NAME", oFeedItem.getName());
                intent.putExtra("IMAGE_URL", oFeedItem.getImageUrl());
                intent.putExtra("DESCRIPTION", "description");
                activity.startActivity(intent);
            }
        });


    }
    @Override
    public int getItemCount() {
        return aFeedItem.size();
    }

    public interface YourCampsAdapterListener{
        void webGoTo(String url);
        void removeCamp(int position);
        //void alterImage(String url, ImageView view);
    }

    //private String[] PostData;

}
