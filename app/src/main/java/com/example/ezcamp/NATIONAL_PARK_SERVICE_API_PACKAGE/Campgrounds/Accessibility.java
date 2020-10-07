package com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds;

import java.util.ArrayList;
import java.util.List;

public class Accessibility {

    private String wheelchairaccess;
    private String internetinfo;
    private String rvallowed;
    private String cellphoneinfo;
    private String firestovepolicy;
    private String rvmaxlength;
    private String additionalinfo;
    private String trailermaxlength;
    private String adainfo;
    private String rvinfo;
    private ArrayList<String> accessroads;
    private String trailerallowed;
    private ArrayList<String> classifications;

    public Accessibility(String wheelchairaccess, String internetinfo, String rvallowed, String cellphoneinfo, String firestovepolicy, String rvmaxlength, String additionalinfo, String trailermaxlength, String adainfo, String rvinfo, ArrayList<String> accessroads, String trailerallowed, ArrayList<String> classifications) {
        this.wheelchairaccess = wheelchairaccess;
        this.internetinfo = internetinfo;
        this.rvallowed = rvallowed;
        this.cellphoneinfo = cellphoneinfo;
        this.firestovepolicy = firestovepolicy;
        this.rvmaxlength = rvmaxlength;
        this.additionalinfo = additionalinfo;
        this.trailermaxlength = trailermaxlength;
        this.adainfo = adainfo;
        this.rvinfo = rvinfo;
        this.accessroads = accessroads;
        this.trailerallowed = trailerallowed;
        this.classifications = classifications;
    }

    public String getWheelchairaccess() {
        return wheelchairaccess;
    }

    public String getInternetinfo() {
        return internetinfo;
    }

    public String getRvallowed() {
        return rvallowed;
    }

    public String getCellphoneinfo() {
        return cellphoneinfo;
    }

    public String getFirestovepolicy() {
        return firestovepolicy;
    }

    public String getRvmaxlength() {
        return rvmaxlength;
    }

    public String getAdditionalinfo() {
        return additionalinfo;
    }

    public String getTrailermaxlength() {
        return trailermaxlength;
    }

    public String getAdainfo() {
        return adainfo;
    }

    public String getRvinfo() {
        return rvinfo;
    }

    public List<String> getAccessroads() {
        return accessroads;
    }

    public String getTrailerallowed() {
        return trailerallowed;
    }

    public List<String> getClassifications() {
        return classifications;
    }
}


