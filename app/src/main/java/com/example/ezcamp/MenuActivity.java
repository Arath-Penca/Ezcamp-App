package com.example.ezcamp;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.appbar.MaterialToolbar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
                                                                BottomNavigationView.OnNavigationItemSelectedListener,
                                                                View.OnClickListener,
                                                                GoogleApiClient.OnConnectionFailedListener,
                                                                FragmentHome.HomeFragmentListener {



    private static final String TAG = "MenuActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    private GoogleSignInAccount account;
    private GoogleSignInClient mGoogleSignInClient;
    private TextView profileName;
    private TextView profileEmail;
    private ImageView profilePicture;

    // Fragments
    public FragmentHome oFragmentHome;
    private FragmentFeed oFragmentFeed;
    private FragmentYourCamps oFragmentYourCamps;
    private FragmentAccount oFragmentSettings;

    // Google Maps Initialization
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 9901;
    final private  static float DEFAULT_MAP_ZOOM = 15f;
    public static boolean mLocationPermissionGranted = false;
    public  FusedLocationProviderClient mFusedLocationProviderClient;

    // Navigation Setups
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    MaterialToolbar toolbar;
    BottomNavigationView bottomNavigationView;
    Fragment previousFragment;
    Button mAccount;
    Button mSettings;
    Button mQuestionnaire;
    Button mLogout;

    //Requests
   // public CampgroundsResponse dataObject;

    String personEmail = "";
    String personName = "";
    String img_url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Set up Navigation drawer's --------------------------------------------------------------->
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
        navigationView.bringToFront();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        mAccount = findViewById(R.id.ButtonProfile);
        mSettings = findViewById(R.id.ButtonSettings);
        mQuestionnaire = findViewById(R.id.ButtonQuestionnaire);
        mLogout = findViewById(R.id.ButtonLogout);

        // Get Profile Information ---------------------------------------------------------------->

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        account = GoogleSignIn.getLastSignedInAccount(this);

        // Miscellaneous Setup -------------------------------------------------------------------->

        if (account != null) {
            personName = account.getDisplayName();
            personEmail = account.getEmail();
        }

        View hview = navigationView.getHeaderView(0);
        profilePicture = hview.findViewById(R.id.profilePicture);
        profileName = hview.findViewById(R.id.profileName);
        profileEmail = hview.findViewById(R.id.profileEmail);

        if(account.getPhotoUrl() != null)
        {
            img_url = account.getPhotoUrl().toString();
        Glide.with(this).load(img_url).into(profilePicture);}
        else{
            profilePicture.setImageResource(R.drawable.ic_no_picture_round);
        }

        profileName.setText(personName);
        profileEmail.setText(personEmail);

        // Fragments ------------------------------------------------------------------------------>
        if (savedInstanceState == null) {
           getSupportFragmentManager().beginTransaction().replace(R.id.fragmentMenuContainer,
                  new FragmentYourCamps(),"TAG_FragmentYourCamps").commit();
            bottomNavigationView.setSelectedItemId(R.id.menuNavBottomYourCamps);
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {

        // set up fragment when they exist
        oFragmentHome = new FragmentHome();
        oFragmentHome = (FragmentHome) getSupportFragmentManager().findFragmentByTag("TAG_FragmentHome");

        oFragmentFeed = new FragmentFeed();
        oFragmentFeed = (FragmentFeed) getSupportFragmentManager().findFragmentByTag("TAG_FragmentFeed");

        oFragmentYourCamps = new FragmentYourCamps();
        oFragmentYourCamps = (FragmentYourCamps) getSupportFragmentManager().findFragmentByTag("TAG_FragmentYourCamps");

        oFragmentSettings = new FragmentAccount();
        oFragmentSettings = (FragmentAccount) getSupportFragmentManager().findFragmentByTag("TAG_FragmentSettings");
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentMenuContainer);
        String TAG = null;

        switch (item.getItemId()) {


            case R.id.menuNavBottomHome:
                if(isServicesOk()){
                    selectedFragment = new FragmentHome();
                    TAG = "TAG_FragmentHome";
                }
                break;

            case R.id.menuNavBottomFeed:
                selectedFragment = new FragmentFeed();
                TAG = "TAG_FragmentFeed";
                break;

            case R.id.menuNavBottomYourCamps:
                selectedFragment = new FragmentYourCamps();
                TAG = "TAG_FragmentYourCamps";
                break;


            case R.id.menuNavAccount:
                selectedFragment = new FragmentAccount();
                break;

            case R.id.menuNavSettings:
                //selectedFragment = new FragmentSettings();
                goToActivity(MainSettingsActivity.class);
                TAG = "TAG_FragmentSettings";
                break;


            case R.id.menuNavLogout:

                break;

            case R.id.menuNavQuestionnaire:
                goToActivity(MainQuestionnaireActivity.class);
                break;

        }


        if(selectedFragment != null && getSupportFragmentManager().findFragmentByTag(TAG) == null){

            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentMenuContainer,
                    selectedFragment, TAG).commit();
            //Toast.makeText(this,currentFragment.toString(),Toast.LENGTH_SHORT).show();

        }




        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        Fragment selectedFragment = null;
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentMenuContainer);
        String TAG = null;

        switch (v.getId()){

            case R.id.ButtonProfile:
                selectedFragment = new FragmentAccount();
            break;

            case R.id.ButtonSettings:
                goToActivity(MainSettingsActivity.class);
            break;

            case R.id.ButtonQuestionnaire:
                goToActivity(MainQuestionnaireActivity.class);
            break;

            case R.id.ButtonLogout:
                signOutGoogleAccount();
            break;


        }
        if(selectedFragment != null && getSupportFragmentManager().findFragmentByTag(TAG) == null){

            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentMenuContainer,
                    selectedFragment, TAG).commit();
            //Toast.makeText(this,currentFragment.toString(),Toast.LENGTH_SHORT).show();

        }

        drawerLayout.closeDrawer(GravityCompat.START);

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void goToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    public void signOutGoogleAccount(){
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        goToActivity(MainLoginActivity.class);
                    }
                });

    }

    // Home Fragment ------------------------------------------------------------------------------>

    public boolean isServicesOk(){ // checks if you have google player services at all! not related with map creation
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MenuActivity.this);

        if (available == ConnectionResult.SUCCESS){
            return true; // the user has the appropriate version
        }else if (GoogleApiAvailabilityLight.getInstance().isUserResolvableError(available)){
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MenuActivity.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{ Toast.makeText(this, "We Can't Make Map Requests", Toast.LENGTH_SHORT).show();}

        return false;
    }

    @Override
    public void getLocation(){
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            if(mLocationPermissionGranted){
                Task Location = mFusedLocationProviderClient.getLastLocation();
                Location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            // success
                            Location currentLocation = (Location) task.getResult();
                            if(currentLocation != null)
                                oFragmentHome.setUserLocation(currentLocation);
                                oFragmentHome.moveMapCameraTo(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()), DEFAULT_MAP_ZOOM,"Home... Hopefully");
                        }else{
                            Toast.makeText(MenuActivity.this,"Unable to Get Location",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException E){
            Toast.makeText(MenuActivity.this,"Security Exception",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean isLocationPermissionGranted(){
        return mLocationPermissionGranted;
    }

    @Override
    public void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(getApplicationContext(),FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(getApplicationContext(),COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                                       mLocationPermissionGranted = true;
                                       oFragmentHome.initMap();

            }else{
                ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            mLocationPermissionGranted = false;
        switch(requestCode){

            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length>0){
                    for(int i = 0; i < grantResults.length; i++){
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    oFragmentHome.initMap();
                }
            }
        }
    }

    public void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        goToActivity(MainLoginActivity.class);
                    }
                });
    }


}