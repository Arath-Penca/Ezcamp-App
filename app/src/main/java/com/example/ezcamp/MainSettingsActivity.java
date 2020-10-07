package com.example.ezcamp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");


        Button revoke = findViewById(R.id.ButtonRevokeAccess);
        revoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuActivity o = new MenuActivity();
                o.revokeAccess();

            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
