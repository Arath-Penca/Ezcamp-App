package com.example.ezcamp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SlideViewHolder> {

    private List<SliderItem> sliderItems;
    private ViewPager2 mViewPager2;

    public SliderAdapter(List<SliderItem> sliderItems, ViewPager2 mViewPager2) {
        this.sliderItems = sliderItems;
        this.mViewPager2 = mViewPager2;
    }

    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SlideViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        holder.setText(sliderItems.get(position));
        holder.setImage(sliderItems.get(position));

        if(position == sliderItems.size() - 2){
            mViewPager2.post(runnable);
        }

    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    class SlideViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView mTextView;

        SlideViewHolder(@Nullable View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.SliderImageItem);
            mTextView = itemView.findViewById(R.id.SliderDescriptionItem);
        }



        void setImage(SliderItem slideritem){
            imageView.setImageResource(slideritem.getImage());
        }

        void setText(SliderItem sliderItem){
            Log.d("Sure", "setText: "+ sliderItem.getText());
            mTextView.setText(sliderItem.getText());
        }

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sliderItems.addAll(sliderItems);
            notifyDataSetChanged();
        }
    };

}
