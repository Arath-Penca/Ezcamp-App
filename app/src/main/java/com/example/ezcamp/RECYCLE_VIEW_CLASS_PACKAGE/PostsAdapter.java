package com.example.ezcamp.RECYCLE_VIEW_CLASS_PACKAGE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ezcamp.R;
import com.example.ezcamp.ViewCampActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder>{

    private ArrayList<FeedItem> aFeedItem;
    public PostAdapterListener activityCommander;
    public Activity activity;

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        public ImageView mFeedImage;
        public TextView mFeedTitle;
        public TextView mFeedDescription;
        public Button mFeedMoreInformationButton;
        public String url;



        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            mFeedImage  = itemView.findViewById(R.id.FeedImage);
            mFeedTitle = itemView.findViewById(R.id.FeedTitle);
            mFeedDescription = itemView.findViewById(R.id.FeedDescription);
            mFeedMoreInformationButton = itemView.findViewById(R.id.ButtonMoreInformation);
        }
    }

    public PostsAdapter (ArrayList<FeedItem> aFeedItem, PostAdapterListener listener, Activity activity){
        this.aFeedItem = aFeedItem;
        activityCommander = listener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
        PostViewHolder postViewHolder = new PostViewHolder(v);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        final FeedItem oFeedItem = aFeedItem.get(position);
        final String no_image_url = "https://semantic-ui.com/images/wireframe/image.png";

        // set Content
        try{
            Glide.with(holder.itemView).load(oFeedItem.getImageUrl()).into(holder.mFeedImage);
        }catch (Exception e ){
            Glide.with(holder.itemView).load(no_image_url).into(holder.mFeedImage);
        }

        //Glide.with(holder.itemView).load(oFeedItem.getImageUrl()).into(holder.mFeedImage);
        holder.mFeedTitle.setText(oFeedItem.getTitle());
        holder.mFeedDescription.setText(oFeedItem.getDescription());

        holder.mFeedMoreInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(activity,ViewCampActivity.class);
                    intent.putExtra("NAME", oFeedItem.getTitle());
                    intent.putExtra("IMAGE_URL", oFeedItem.getImageUrl());
                    intent.putExtra("DESCRIPTION", oFeedItem.getDescription() );
                    activity.startActivity(intent);
                }
                catch (Exception e){
                    // idk mate
                }
            }
        });


    }
    @Override
    public int getItemCount() {
        return aFeedItem.size();
    }

    public interface PostAdapterListener{
        void webGoTo(String url);
        //void alterImage(String url, ImageView view);
    }

    //private String[] PostData;
}
