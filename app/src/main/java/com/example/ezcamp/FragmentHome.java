package com.example.ezcamp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Alerts.AlertsResponse;
import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Alerts.DataAlerts;
import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Articles.ArticlesResponse;
import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Articles.DataArticles;
import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds.Addresses;
import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds.CampgroundsResponse;
import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds.Campsites;
import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds.DataCampgrounds;
import com.example.ezcamp.RECYCLE_VIEW_CLASS_PACKAGE.FeedItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;
import com.koushikdutta.async.http.body.JSONObjectBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.Manifest;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentHome extends Fragment implements OnMapReadyCallback, View.OnClickListener, JSON_REQUESTS.FeedFragmentListener, AdapterView.OnItemSelectedListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    // Map Setup
    private static boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;
    private EditText searchText;
    private ImageView imgGPS;

    //FAKE DATA


    private Button findPerfectCamps;

    private HomeFragmentListener activityCommander;
    private JSON_REQUESTS json;
    private CampgroundsResponse oCampgroundsResponse;
    private ArrayList<DataCampgrounds> arrayDataCampgrounds;
    private Location userLocation;
    private ArrayList<LatLng> arrayMarkerLocations;

    // Spinners
    public Spinner spinnerRegion;
    public Spinner spinnerStates;
    private ArrayList<SpinnerItem> arrayRegion;
    private ArrayList<SpinnerItem> arrayStates;
    private SpinnerAdapter spinnerAdapterRegion;
    private SpinnerAdapter spinnerAdapterStates;

    // Pereference
    private String regionPreference = "Local";
    private String statePreference = "Florida";
    public ProgressBar progressBar;
    private boolean isSearching;
    private View rootview;
    public FragmentHome(){}

    final private static float WIDE_ZOOM = 6f;
    final private String TAG = "k";

    // Beta
    private RequestQueue requestQueue;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            activityCommander.getLocationPermission();
            searchText = view.findViewById(R.id.EditTextSearchInput);
            imgGPS = view.findViewById(R.id.icon_gps);
            findPerfectCamps = view.findViewById(R.id.ButtonFindCamps);
            findPerfectCamps.setOnClickListener(this);
            arrayMarkerLocations = new ArrayList<>();
            requestQueue = Volley.newRequestQueue(getActivity());
            spinnerRegion = view.findViewById(R.id.SpinnerRegion);
            spinnerStates = view.findViewById(R.id.SpinnerStates);
            progressBar = view.findViewById(R.id.ProgressBarHome);
            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            progressBar.setVisibility(View.GONE);
            isSearching = false;
            setRetainInstance(true);
            // Create objects
            json = new JSON_REQUESTS(this);
            oCampgroundsResponse = new CampgroundsResponse();
            //json.getPrepapredData();
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Region, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            regionPreference = "International";
            statePreference = "Florida";
            setSpinners();
        }
        return view;
    }



    public void setSpinners(){

        arrayRegion = new ArrayList<>();
        arrayRegion.add(new SpinnerItem("International",R.drawable.ic_international));
        arrayRegion.add(new SpinnerItem("States", R.drawable.ic_state));
        arrayRegion.add(new SpinnerItem("Local", R.drawable.ic_local));
        spinnerAdapterRegion = new SpinnerAdapter(getContext(), arrayRegion);
        spinnerRegion.setOnItemSelectedListener(this);
        spinnerRegion.setAdapter(spinnerAdapterRegion);
        arrayStates = new ArrayList<>();
        arrayStates.add(new SpinnerItem("Florida",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Oklahoma",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("New Mexico",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Arizona",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Arkansas",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Alabama",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("West Virginia",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Virginia",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Kentucky",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Tennessee",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Louisiana",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Mississippi",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("South Carolina",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("North Carolina",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Texas",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Georgia",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("California",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Utah",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Nevada",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Colorado",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Wyoming",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Montana",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Idaho",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Oregon",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Washington",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Pennsylvania",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("New York",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("New Jersey",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Delaware",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Maryland",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Maine",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Massachusetts",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Connecticut",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Rhode Island",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Vermont",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("New Hampshire",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("North Dakota",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("South Dakota",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Nebraska",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Kansas",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Minnesota",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Iowa",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Missouri",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Illinois",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Wisconsin",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Michigan",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Indiana",R.drawable.ic_check));
        arrayStates.add(new SpinnerItem("Ohio",R.drawable.ic_check));
        spinnerAdapterStates = new SpinnerAdapter(getContext(), arrayStates);
        spinnerStates.setOnItemSelectedListener(this);
        spinnerStates.setAdapter(spinnerAdapterStates);

    }

    // MAPS --------------------------------------------------------------------------------------->
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mLocationPermissionGranted = activityCommander.isLocationPermissionGranted();

        if(mLocationPermissionGranted){
            activityCommander.getLocation();

            if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return; // get out of the method
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mMap.getUiSettings().setCompassEnabled(false);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.setOnMarkerClickListener(this);
            mMap.setOnInfoWindowClickListener(this);

            init();
        }


    }


    private void init(){
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER
                        || event.getAction() == KeyEvent.KEYCODE_SPACE){
                    // Insert Code
                    geoLocate();
                    Log.d(TAG, "onEditorAction: Enter buttons");
                }
                return false;
            }
        });

        imgGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommander.getLocation();
            }
        });

        hideSoftKeyboard();
    }

    private void geoLocate(){

        String SearchInput = searchText.getText().toString();
        Geocoder geocoder = new Geocoder(getActivity());
        List<Address> list = new ArrayList<>();

        try {
            list = geocoder.getFromLocationName(SearchInput,1);
        }catch (IOException e){
            Log.d(TAG, "geoLocate: IOEXCEPTION");
        }

        if(list.size() > 0){
            Address address = list.get(0);
            moveMapCameraTo(new LatLng(address.getLatitude(), address.getLongitude()),WIDE_ZOOM,address.getAddressLine(0));
        }

    }


    public void initMap(){
       SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
       mapFragment.getMapAsync(this);
    }

    public void moveMapCameraTo(LatLng latLng, float zoomValue, String title){
        //latLng.latitude; latLng.longitude;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomValue));
        //setMarkerAt(latLng,title);
        hideSoftKeyboard();
    }

    private void setMarkerAt(LatLng latLng, String title){

        // check if this markers already exists
        for(LatLng currentMarker : arrayMarkerLocations){
            if(currentMarker.longitude == latLng.longitude && currentMarker.latitude == latLng.latitude){
                return;
            }else{
                // if does not exist
                arrayMarkerLocations.add(latLng);
            }
        }
        // if not, place it
        MarkerOptions options = new MarkerOptions().position(latLng).title(title);
        mMap.addMarker(options);
    }

    // Responses ---------------------------------------------------------------------------------->

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.ButtonFindCamps:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        if(!isSearching){
                            isSearching = true;
                            progressBar.setVisibility(View.VISIBLE);
                            mMap.clear();

                            if(regionPreference.equals("International")){
                                getInternationalCamps();
                                getLocalCamps();
                            }else {
                                if (regionPreference.equals("States")) {
                                    getStateCamps();
                                } else {
                                    if (regionPreference.equals("Local")) {
                                        getLocalCamps();
                                    }
                                }
                            }}
                    }
                }, 500);
                break;
        }
    }

    public void getInternationalCamps(){
        try {
            final String NULL = "";
            JSONObject mainObject = new JSONObject(getDataFromAssets("thebigone.txt"));
            JSONArray data = mainObject.getJSONArray("data");

            ArrayList<JSONObject> Campsites = new ArrayList<>();

            for(int i = 0; i < data.length(); i++){

                JSONObject Campsite = data.getJSONObject(i);

                String longitude = Campsite.getString("longitude");
                String latitude = Campsite.getString("latitude");
                String name = Campsite.getString("name");

                if(!longitude.equals(NULL)){
                    LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                    setMarkerAt(latLng,name);
                }
            }
            progressBar.setVisibility(View.GONE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        isSearching = false;
    }

    public void getStateCamps(){

        if(statePreference.equals("Florida")){
            getLocalCamps();
        }

        try {
            final String NULL = "";
            JSONObject mainObject = new JSONObject(getDataFromAssets("thebigone.txt"));
            JSONArray data = mainObject.getJSONArray("data");

            ArrayList<JSONObject> Campsites = new ArrayList<>();
            boolean result = false;


            for(int i = 0; i < data.length(); i++){
                JSONObject Campsite = data.getJSONObject(i);

                String state = NULL;
                if(Campsite.optJSONArray("addresses") != null){
                    JSONArray addresses = Campsite.optJSONArray("addresses");
                    JSONObject address = addresses.getJSONObject(0);
                    state = address.getString("stateCode");
                }

                String longitude = Campsite.getString("longitude");
                String latitude = Campsite.getString("latitude");
                String name = Campsite.getString("name");


                if(!longitude.equals(NULL) && state.equals(getStateAbreviation(statePreference))){
                    result = true;
                    LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                    setMarkerAt(latLng,name);
                }
            }
            Log.d(TAG, "getStateCamps: "+result);

            if(!result){
                Toast.makeText(getContext(),"Sorry, no results found",Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        isSearching = false;
    }

    public void getLocalCamps(){

        try {
            final String NULL = "";
            JSONObject mainObject = new JSONObject(getDataFromAssets("fakedata.txt"));
            JSONArray data = mainObject.getJSONArray("data");

            for(int i = 0; i < data.length(); i++){

                JSONObject Campsite = data.getJSONObject(i);

                String longitude = Campsite.getString("longitude");
                String latitude = Campsite.getString("latitude");
                String name = Campsite.getString("name");

                if(!longitude.equals(NULL)){
                    LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                    setMarkerAt(latLng,name);
                }
            }
            progressBar.setVisibility(View.GONE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        isSearching = false;
    }


    @Override
    public void gotAlert(AlertsResponse response) {

    }

    @Override
    public void gotArticles(ArticlesResponse response) {

    }

    @Override
    public void gotCampgrounds(CampgroundsResponse response) {

        isSearching = false;
        progressBar.setVisibility(View.GONE);
        Log.d(TAG, "gotCampgrounds: Success");
        // First lets search for camps nearby
        oCampgroundsResponse = response;
        arrayDataCampgrounds = new ArrayList<>(Arrays.asList(oCampgroundsResponse.getData()));
        final String ZIP_CODE = getZipCode(getActivity(),userLocation.getLatitude(),userLocation.getLongitude());
        final String stateCode = "FL";

        ArrayList<DataCampgrounds> arrayInternationalCampgrounds = new ArrayList<>();
        ArrayList<DataCampgrounds> arrayStateCampgrounds = new ArrayList<>();
        ArrayList<DataCampgrounds> arrayLocalCampgrounds = new ArrayList<>();

        for(DataCampgrounds currentCampgrounds : arrayDataCampgrounds){

            switch (regionPreference){

                case "International":
                    if(doesUserLikeCamp(currentCampgrounds)){
                        arrayInternationalCampgrounds.add(currentCampgrounds);
                    }
                    break;

                case "States":
                    if(currentCampgrounds.getAddresses().get(0).getStateCode().equals(getStateAbreviation(statePreference))){
                        if(doesUserLikeCamp(currentCampgrounds)){
                            arrayStateCampgrounds.add(currentCampgrounds);
                        }
                    }

                    break;

                case "Local":
                    if(currentCampgrounds.getAddresses().get(0).getPostalCode().equals(ZIP_CODE)){
                        if(doesUserLikeCamp(currentCampgrounds)){
                            arrayLocalCampgrounds.add(currentCampgrounds);
                        }
                    }
                    break;
            }


            // place the markers
            for(DataCampgrounds internationalCampgrounds : arrayInternationalCampgrounds){
                try {
                    LatLng latLng = new LatLng(Double.parseDouble(internationalCampgrounds.getLatitude()), Double.parseDouble(internationalCampgrounds.getLongitude()));
                    setMarkerAt(latLng, currentCampgrounds.getname());
                }catch (Exception e){
                }
            }

            for(DataCampgrounds stateCampgrounds : arrayStateCampgrounds){
                try{
                    LatLng latLng = new LatLng(Double.parseDouble(stateCampgrounds.getLatitude()), Double.parseDouble(stateCampgrounds.getLongitude()));
                    setMarkerAt(latLng, currentCampgrounds.getname());
                }catch (Exception e){
                    e.getMessage();
                }

            }

            for(DataCampgrounds localCampgrounds : arrayLocalCampgrounds){
                try{
                LatLng latLng = new LatLng(Double.parseDouble(localCampgrounds.getLatitude()), Double.parseDouble(localCampgrounds.getLongitude()));
                setMarkerAt(latLng,currentCampgrounds.getname());}
                catch (Exception e){
                    e.getMessage();
                }

            }


        }
    }

    private boolean doesUserLikeCamp(DataCampgrounds currentCampgroud){

        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch(parent.getId()){
            case R.id.SpinnerRegion:

                SpinnerItem spinnerItem = arrayRegion.get(position);
                regionPreference = spinnerItem.getName();

                Log.d(TAG, "onItemSelected: "+spinnerItem.getName());
                if(regionPreference.equals("States")){
                    spinnerStates.setVisibility(View.VISIBLE);
                }else{
                    spinnerStates.setVisibility(View.GONE);
                }
                break;

            case R.id.SpinnerStates:

                if(regionPreference.equals("States")){
                    SpinnerItem spinnerItem1 = arrayStates.get(position);
                    statePreference = spinnerItem1.getName();
                }
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(TAG, "onMarkerClick: "+marker);
        Log.d(TAG, "onMarkerClick: "+marker.getTitle());
        marker.getTitle();
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        JSONObject object;
        final String no_image_url = "https://semantic-ui.com/images/wireframe/image.png";
        String name;
        String img_url;
        String type;
        String weather;
        String description;

        if(checkOne(marker.getTitle()) != null) {
            object = checkOne(marker.getTitle());
            try {
                JSONArray images = object.getJSONArray("images");
                JSONObject imageObject = images.getJSONObject(0);
                img_url = imageObject.getString("url");
            } catch (JSONException e) {
                img_url = no_image_url;
            }


            try {
                name = object.getString("name");
                weather = object.getString("weatheroverview");
                description = object.getString("description");
                type = "Campground";
                sendOFF(name,img_url,type,weather,description);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(checkTwo(marker.getTitle()) != null){
            object = checkTwo(marker.getTitle());

            try {
                name = object.getString("name");
                img_url = object.getString("image");
                type = object.getString("type");
                weather = object.getString("weather");
                description = object.getString("about");
                sendOFF(name,img_url,type,weather,description);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }



        // Data Names
    }

    public void sendOFF(String name, String img_url, String type, String weather, String description){

        final String NAME = "NAME";
        final String IMAGE_URL= "IMAGE_URL";
        final String TYPE = "TYPE";
        final String WEATHER = "WEATHER";
        final String DESCRIPTION = "DESCRIPTION";
        Intent intent = new Intent(getActivity(), ViewCampActivity.class);
        intent.putExtra(NAME,name);
        intent.putExtra(IMAGE_URL,  img_url);
        intent.putExtra(TYPE,type);
        intent.putExtra(WEATHER, weather );
        intent.putExtra(DESCRIPTION,description);
        startActivity(intent);
    }


    public JSONObject checkOne(String title){
        String markerTitle = title;
        JSONObject object = null;
        try {
            final String NULL = "";
            // first check if the big one has it
            JSONObject mainObject = new JSONObject(getDataFromAssets("thebigone.txt"));
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
            Log.d(TAG, "getDataForMarker: Something went wrong with jsons");
        }
        return object;

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
            Log.d(TAG, "getDataForMarker: Something went wrong with jsons");
        }
        return object;

    }

    public interface HomeFragmentListener {
        void getLocationPermission();
        void getLocation();
        boolean isLocationPermissionGranted();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            Activity activity;
            activity = (Activity) context;

            try {
                activityCommander = (HomeFragmentListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString());
            }
        }
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

    public String getZipCode(Context c, double lat, double lng){
        String fullAdd = null;
        String locality = null;
        String zip = null;
        String country = null;
        try {
            Geocoder geocoder = new Geocoder(c, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat,lng,1);
            if (addresses.size()>0){
                Address address = addresses.get(0);
                fullAdd = address.getAddressLine(0);  // full Address
                locality = address.getLocality();
                zip = address.getPostalCode();
                country = address.getCountryName();
            }

        }catch (IOException ex){
            ex.printStackTrace();
        }
        return zip;
    }


    public void setUserLocation(Location location){
        userLocation = location;
    }

    private void hideSoftKeyboard(){
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public String getStateAbreviation(String state){

        String stateMap = "Alabama - AL Alaska - AK Arizona - AZ Arkansas - AR California - CA Colorado - CO Connecticut - CT Delaware - DE Florida - FL Georgia - GA Hawaii - HI Idaho - ID Illinois - IL Indiana - IN Iowa - IA Kansas - KS Kentucky - KY Louisiana - LA Maine - ME Maryland - MD Massachusetts - MA Michigan - MI Minnesota - MN Mississippi - MS Missouri - MO Montana - MT Nebraska - NE Nevada - NV New Hampshire - NH New Jersey - NJ New Mexico - NM New York - NY North Carolina - NC North Dakota - ND Ohio - OH Oklahoma - OK Oregon - OR Pennsylvania - PA Rhode Island - RI South Carolina - SC South Dakota - SD Tennessee - TN Texas - TX Utah - UT Vermont - VT Virginia - VA Washington - WA West Virginia - WV Wisconsin - WI Wyoming - WY";
        int stateLength = state.length();
        final int lengthToAbreviation = 3;
        int stateLocation = stateMap.indexOf(state);
        final int endpoint = stateLocation + lengthToAbreviation + stateLength + 2;
        return stateMap.substring(stateLocation + stateLength + lengthToAbreviation,endpoint);

    }

    public void goToActivity(Class activity){

        Intent intent = new Intent(getContext(), ViewCampActivity.class);
        startActivity(intent);
    }

}
