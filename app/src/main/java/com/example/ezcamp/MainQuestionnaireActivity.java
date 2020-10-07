package com.example.ezcamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Button;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.lang.reflect.Array;

public class MainQuestionnaireActivity extends AppCompatActivity implements View.OnClickListener,
                                                                            GoogleApiClient.OnConnectionFailedListener,
                                                                            AdapterView.OnItemSelectedListener {

    private RadioGroup experiencedRadioGroup;
    private RadioButton currentRadioButton;
    private GoogleApiClient ClientApi;
    private GoogleSignInOptions SignInOptions;
    private TextView milesTextView;
    private Button confirmQuestionnaireButton;
    private final byte numbefOfCampingTypes = 6;

    // Set Data holders
    private String Region = "Local";
    private String State = "Florida";
    private String Distance = "1";
    private String ExperiencedLevel = null;
    private checkbox[] checkboxes = new checkbox[numbefOfCampingTypes];

    //Set seeekbar
    private TextView textViewDistance;
    private SeekBar seekBarDistance;

    // Set Spinners
    private Spinner spinnerRegion;
    private SpinnerAdapter spinnerAdapterRegion;
    private ArrayList<SpinnerItem> arraySpinnerRegions;
    private Spinner spinnerCorrespondence;
    private SpinnerAdapter spinnerCorrespondingAdapter;
    private ArrayList<SpinnerItem> arraySpinnerCorrespondence;

    // Share Preference

    private final static String QuestionnaireResults = "questionnaire";
    private final static String PreferredExperienceLevel = "preferredexperiencelevel";
    private final static String PreferredRegion = "preferredregion";
    private final static String PreferredState = "preferredstate";
    private final static String PreferredDistance = "preferreddistance";

    // checkbox types
    private final static int Adventure = 0;
    private final static int Backyard = 1;
    private final static int Car = 2;
    private final static int Bicycle = 3;
    private final static int Survial = 4;
    private final static int Tent = 5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        experiencedRadioGroup = findViewById(R.id.experiencedRadioGroup);
        milesTextView = findViewById(R.id.TextViewMiles);
        confirmQuestionnaireButton = findViewById(R.id.buttonConfirmQuestionnaire);
        confirmQuestionnaireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ExperiencedLevel == null) {
                    Toast.makeText(getBaseContext(),"You have not chosen a experience level",Toast.LENGTH_SHORT).show();
                }else{
                    goToActivity(MenuActivity.class);
                }
            }
        });

        // set checkbox to false
        for(int i = 0; i < numbefOfCampingTypes; i++){
            checkboxes[i] = new checkbox();
            checkboxes[i].checked = false;
        }

        // set up Seekbar stuff
        textViewDistance = findViewById(R.id.SeekBarMileDistance);
        seekBarDistance = findViewById(R.id.SeekBarAboutDistance);
        seekBarDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

               textViewDistance.setText("Mi: " + progress);
               seekBarDistance.setMax(50);
           }

           @Override
           public void onStartTrackingTouch(SeekBar seekBar) {

           }

           @Override
           public void onStopTrackingTouch(SeekBar seekBar) {

           }
       });
                setSpinners();

    }

    public class checkbox{
        public String type;
        public boolean checked;
    }

    @Override
    public void onClick(View v) {

        CheckBox box = findViewById(v.getId());
            switch(v.getId()){

            case R.id.CheckBoxAnswerAdventureCamping:
                checkboxes[Adventure].type = box.getText().toString();
                checkboxes[Adventure].checked = box.isChecked();
                break;
            case R.id.CheckBoxAnswerBackyardCamping:
                checkboxes[Backyard].type = box.getText().toString();
                checkboxes[Backyard].checked = box.isChecked();
                break;
            case R.id.CheckBoxAnswerCarCamping:
                checkboxes[Car].type = box.getText().toString();
                checkboxes[Car].checked = box.isChecked();
                break;
            case R.id.CheckBoxButtonAnswerBicycleCamping:
                checkboxes[Bicycle].type = box.getText().toString();
                checkboxes[Bicycle].checked = box.isChecked();
                break;
            case R.id.CheckBoxButtonAnswerSurvivalCamping:
                checkboxes[Survial].type = box.getText().toString();
                checkboxes[Survial].checked = box.isChecked();
                break;
            case R.id.CheckBoxButtonAnswerTentCamping:
                checkboxes[Tent].type = box.getText().toString();
                checkboxes[Tent].checked = box.isChecked();
                break;

        }


    }


    public void onClickRadioButton(View v){

        int radioButtonId = experiencedRadioGroup.getCheckedRadioButtonId();
        currentRadioButton = findViewById(radioButtonId);
        ExperiencedLevel = (String) currentRadioButton.getText();
        ExperiencedLevel = ExperiencedLevel.replaceAll("\\D+","").trim();

    }

    public void showCheckMessage(int stringId, View v){

        boolean checked = ((CheckBox) v).isChecked();
        Context context = getApplicationContext();
        CharSequence kindOfCamper = getResources().getString(stringId);
        final Toast message = Toast.makeText(context, checked? "Checked: " + kindOfCamper : "Checked Off: " + kindOfCamper, Toast.LENGTH_SHORT);
        message.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                message.cancel();
            }
        }, 350);
    }

    public void saveQuestionnaireData(){
        SharedPreferences sharedPreferences = getSharedPreferences(QuestionnaireResults, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(PreferredExperienceLevel, ExperiencedLevel);

        for(checkbox box : checkboxes){
            editor.putBoolean(box.type,box.checked);
        }

        editor.putString(PreferredRegion, Region);
        editor.putString(PreferredState, State);
        editor.putString(PreferredDistance,isNumeric(State)?Distance:"0");
        editor.apply();

    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void goToActivity(Class activity){
        saveQuestionnaireData();
        finish();
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void setSpinners(){

        // Regions
        spinnerRegion = findViewById(R.id.SpinnerQuestionRegion);
        arraySpinnerRegions = new ArrayList<>();
        arraySpinnerRegions.add(new SpinnerItem(getResources().getString(R.string.Local),R.drawable.ic_local));
        arraySpinnerRegions.add(new SpinnerItem(getResources().getString(R.string.North_east),R.drawable.ic_region));
        arraySpinnerRegions.add(new SpinnerItem(getResources().getString(R.string.South_east),R.drawable.ic_region));
        arraySpinnerRegions.add(new SpinnerItem(getResources().getString(R.string.South_west),R.drawable.ic_region));
        arraySpinnerRegions.add(new SpinnerItem(getResources().getString(R.string.Midwest),R.drawable.ic_region));
        arraySpinnerRegions.add(new SpinnerItem(getResources().getString(R.string.West),R.drawable.ic_region));
        spinnerAdapterRegion = new SpinnerAdapter(this, arraySpinnerRegions);
        spinnerRegion.setAdapter(spinnerAdapterRegion);
        spinnerRegion.setOnItemSelectedListener(this);

        // Set up States
        arraySpinnerCorrespondence = new ArrayList<>();
        spinnerCorrespondence = findViewById(R.id.SpinnerQuestionStates);
        spinnerCorrespondence.setAdapter(spinnerCorrespondingAdapter);
        spinnerCorrespondence.setOnItemSelectedListener(this);

    }

    public void setSouthWestStates(){
        arraySpinnerCorrespondence.clear();
        arraySpinnerCorrespondence.add(new SpinnerItem("Texas",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Oklahoma",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("New Mexico",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Arizona",R.drawable.ic_check));
        spinnerCorrespondingAdapter = new SpinnerAdapter(this, arraySpinnerCorrespondence);
        spinnerCorrespondence.setAdapter(spinnerCorrespondingAdapter);
    }

    public void setSouthEastStates(){
        arraySpinnerCorrespondence.clear();
        arraySpinnerCorrespondence.add(new SpinnerItem("Arkansas",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Alabama",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("West Virginia",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Virginia",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Kentucky",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Tennessee",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Louisiana",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Mississippi",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("South Carolina",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("North Carolina",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Florida",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Georgia",R.drawable.ic_check));
        spinnerCorrespondingAdapter = new SpinnerAdapter(this, arraySpinnerCorrespondence);
        spinnerCorrespondence.setAdapter(spinnerCorrespondingAdapter);
    }

    public void setWestStates(){
        arraySpinnerCorrespondence.clear();
        arraySpinnerCorrespondence.add(new SpinnerItem("California",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Utah",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Nevada",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Colorado",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Wyoming",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Montana",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Idaho",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Oregon",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Washington",R.drawable.ic_check));
        spinnerCorrespondingAdapter = new SpinnerAdapter(this, arraySpinnerCorrespondence);
        spinnerCorrespondence.setAdapter(spinnerCorrespondingAdapter);
    }

    public void setNorthStates(){

        arraySpinnerCorrespondence.clear();
        arraySpinnerCorrespondence.add(new SpinnerItem("Pennsylvania",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("New York",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("New Jersey",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Delaware",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Maryland",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Maine",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Massachusetts",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Connecticut",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Rhode Island",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Vermont",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("New Hampshire",R.drawable.ic_check));
        spinnerCorrespondingAdapter = new SpinnerAdapter(this, arraySpinnerCorrespondence);
        spinnerCorrespondence.setAdapter(spinnerCorrespondingAdapter);
    }

    public void setMidwestStates(){

        arraySpinnerCorrespondence.clear();
        arraySpinnerCorrespondence.add(new SpinnerItem("North Dakota",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("South Dakota",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Nebraska",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Kansas",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Minnesota",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Iowa",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Missouri",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Illinois",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Wisconsin",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Michigan",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Indiana",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("Ohio",R.drawable.ic_check));
        spinnerCorrespondingAdapter = new SpinnerAdapter(this, arraySpinnerCorrespondence);
        spinnerCorrespondence.setAdapter(spinnerCorrespondingAdapter);

    }


    public void setLocal(){
        arraySpinnerCorrespondence.clear();
        arraySpinnerCorrespondence.add(new SpinnerItem("1 Mile",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("2 Miles",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("3 Miles",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("5 Miles",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("10 Miles",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("15 Miles",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("20 Miles",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("30 Miles",R.drawable.ic_check));
        arraySpinnerCorrespondence.add(new SpinnerItem("50 Miles",R.drawable.ic_check));
        spinnerCorrespondingAdapter = new SpinnerAdapter(this, arraySpinnerCorrespondence);
        spinnerCorrespondence.setAdapter(spinnerCorrespondingAdapter);
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch(parent.getId()){

            case R.id.SpinnerQuestionRegion:

                SpinnerItem objectRegions = arraySpinnerRegions.get(position);

                // Check if spinner correspondence is not visible
                if(spinnerCorrespondence.getVisibility() == View.GONE){
                    spinnerCorrespondence.setVisibility(View.VISIBLE);
                }

                // check if textview or seekbar are visible and set them to falase
                if(textViewDistance.getVisibility() == View.VISIBLE){
                    textViewDistance.setVisibility(View.GONE);
                }

                if(seekBarDistance.getVisibility() == View.VISIBLE){
                    seekBarDistance.setVisibility(View.GONE);
                }

                switch (objectRegions.getName()){

                    case "Local":
                        spinnerCorrespondence.setVisibility(View.GONE);
                        textViewDistance.setVisibility(View.VISIBLE);
                        seekBarDistance.setVisibility(View.VISIBLE);
                        break;

                    case "North East":
                        setNorthStates();
                        break;

                    case "South East":
                        setSouthEastStates();
                        break;

                    case "South West":
                        setSouthWestStates();
                        break;

                    case "Midwest":
                        setMidwestStates();
                        break;

                    case "West":
                        setWestStates();
                        break;
                }
                Region = objectRegions.getName();
                break;


            case R.id.SpinnerQuestionStates:

                SpinnerItem objectCorrespondence = arraySpinnerCorrespondence.get(position);
                String value = null;

                if(objectCorrespondence.getName().equals("Local")){
                    value = objectCorrespondence.getName().substring(0,1).trim();
                }else{
                    value = objectCorrespondence.getName();
                }

                State = value;
                    break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
