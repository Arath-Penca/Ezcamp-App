package com.example.ezcamp;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SpinnerAdapter extends ArrayAdapter<SpinnerItem> {

    public SpinnerAdapter(@NonNull Context context, ArrayList<SpinnerItem> spinnerItem) {
        super(context, 0, spinnerItem);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_layout,parent,false);
        }

        SpinnerItem spinnerItem  = getItem(position);
        ImageView icon = convertView.findViewById(R.id.ImageViewCustomSpinner);
        TextView icon_detail = convertView.findViewById(R.id.TextViewCustomSpinnerDetail);
        if (spinnerItem != null) {
            icon.setImageResource(spinnerItem.getImage());
            icon_detail.setText(spinnerItem.getName());

        }

        return convertView;
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_dropdown_layout,parent,false);
        }

        SpinnerItem spinnerItem  = getItem(position);
        ImageView icon = convertView.findViewById(R.id.ImageViewDropDown);
        TextView icon_detail = convertView.findViewById(R.id.TextViewDropDownDetail);
        if (spinnerItem != null) {
            icon.setImageResource(spinnerItem.getImage());
            icon_detail.setText(spinnerItem.getName());

        }

        return convertView;
    }
}
