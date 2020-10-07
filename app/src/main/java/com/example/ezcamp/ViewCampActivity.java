package com.example.ezcamp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ViewCampActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ImageView mCampImage;
    private TextView mCampDescription;
    private Button mCampAdd;

    final String NAME = "NAME";
    final String IMAGE_URL= "IMAGE_URL";
    final String DESCRIPTION = "DESCRIPTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize views
        mCampImage = findViewById(R.id.ImageViewCampImage);
        mCampDescription = findViewById(R.id.TextViewCampDescription);
        mCampAdd = findViewById(R.id.ButtonViewSetTime);

        // Get info from previous activity
        Intent intent = getIntent();
        getSupportActionBar().setTitle(intent.getStringExtra(NAME));
        mCampDescription.setText(intent.getStringExtra(DESCRIPTION));
        Glide.with(this).load(intent.getStringExtra(IMAGE_URL)).into(mCampImage);

        // Button action when clicked
        mCampAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DataPickerFragment();
                datePicker.show(getSupportFragmentManager(), "TAG_DatePicker");
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // what do with the acquired informatoin???... only i know
    }
}
