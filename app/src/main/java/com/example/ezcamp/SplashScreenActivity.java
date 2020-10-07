package com.example.ezcamp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.gms.auth.api.signin.GoogleSignIn;

public class SplashScreenActivity extends AppCompatActivity {

    public final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(isSignedIn()){
                    goToActivity(MenuActivity.class);
                }else{
                    goToActivity(MainLoginActivity.class);
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    public boolean isSignedIn(){
        Context context = getApplicationContext();
        return GoogleSignIn.getLastSignedInAccount(context) != null;
    }

    public void goToActivity(Class activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        finish();
    }

}
