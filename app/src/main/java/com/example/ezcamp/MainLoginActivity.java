package com.example.ezcamp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainLoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{


    private ViewPager2 mViewPager2;
    private Handler sliderHandler;


    private SignInButton signInButton;
    private GoogleSignInOptions SignInOptions;
    private GoogleApiClient ClientApi;
    private final int REQ_CODE = 9001;
    private String email = "";
    private String name = "";
    private String imageUrlString = "";


    private static final String TAG = "MainLoginActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (isSignedIn())
            goToActivity(MenuActivity.class);


        String File_Name = "hellow.txt";

        Log.d(TAG, "onCreate: " + ReadFromfile("thebigone",getBaseContext()));


        // Find Objects
        mViewPager2 = findViewById(R.id.ViewPagerImages);
        signInButton = findViewById(R.id.GoogleSignInButton);

        // Setup Objects
        sliderHandler = new Handler();
        signInButton.setOnClickListener(this);
        SignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        ClientApi = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API, SignInOptions).build();

        // Add Images to PagerView
        final List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.image_backyard_camping,getResources().getString(R.string.Description_aslo_for_casuals)));
        sliderItems.add(new SliderItem(R.drawable.image_onlookers_campers,getResources().getString(R.string.Description_for_advanced)));
        sliderItems.add(new SliderItem(R.drawable.image_out_door_camping,getResources().getString(R.string.Description_makging_camping_ez)));
        sliderItems.add(new SliderItem(R.drawable.image_mobile_camping,getResources().getString(R.string.Description_for_all_types)));

        mViewPager2.setAdapter(new SliderAdapter(sliderItems, mViewPager2));

        mViewPager2.setClipToPadding(false);
        mViewPager2.setClipChildren(false);
        mViewPager2.setOffscreenPageLimit(3);
        mViewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(30));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                float incrementingValue = 0.85f + r * 0.15f;
                page.setScaleY(incrementingValue);
            }
        });

        mViewPager2.setPageTransformer(compositePageTransformer);
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable,10_000);
            }
        });
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            mViewPager2.setCurrentItem(mViewPager2.getCurrentItem() + 1);
        }
    };



    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(isSignedIn())
            goToActivity(MainQuestionnaireActivity.class);
    }

    public String ReadFromfile(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets()
                    .open(fileName, Context.MODE_WORLD_READABLE);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // For buttons
    @Override
    public void onClick(View clicker) {


        switch(clicker.getId())
        {
            case R.id.GoogleSignInButton:
                SignIn();
                System.out.println("working");
                break;

        }
    }


    public void SignIn()
    {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(ClientApi);
        startActivityForResult(intent, REQ_CODE);
    }

    private void LogOut()
    {
        Auth.GoogleSignInApi.signOut(ClientApi).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                // UpdateUi(false);
            }
        });
    }

    private void HandleResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            if (account.getDisplayName() != null && account.getEmail() != null) {
                name = account.getDisplayName();
                email = account.getEmail();
            } else {
                name = "[NO_NAME]";
                email = "[NO_EMAIL]";
            }

            if (account.getPhotoUrl() != null) {// go ahead and get the photo then
                imageUrlString = account.getPhotoUrl().toString();
                // Glide.with(this).load(img_url).into(googleAccountPictureView);
            } else {
                imageUrlString = Integer.toString(R.drawable.defualt_picture);
            }
            goToActivity(MainQuestionnaireActivity.class);

        } else{
            // if failed to sign in
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_CODE)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.i("Test","Successful DataCampgrounds gather");
            HandleResult(result);


        }
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}



