package com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds;

import java.util.ArrayList;
import java.util.List;

public class Amenities {

    private String trashrecyclingcollection;
    private ArrayList<String> toilets;
    private String internetconnectivity;
    private ArrayList<String> showers;
    private String cellphonereception;
    private String laundry;
    private String amphitheater;
    private String dumpstation;
    private String campstore;
    private String stafforvolunteerhostonsite;
    private ArrayList<String> potablewater;
    private String iceavailableforsale;
    private String firewoodforsale;
    private String ampitheater;
    private String foodstoragelockers;

    public Amenities(String trashrecyclingcollection, ArrayList<String> toilets, String internetconnectivity, ArrayList<String> showers, String cellphonereception, String laundry, String amphitheater, String dumpstation, String campstore, String stafforvolunteerhostonsite, ArrayList<String> potablewater, String iceavailableforsale, String firewoodforsale, String ampitheater, String foodstoragelockers) {
        this.trashrecyclingcollection = trashrecyclingcollection;
        this.toilets = toilets;
        this.internetconnectivity = internetconnectivity;
        this.showers = showers;
        this.cellphonereception = cellphonereception;
        this.laundry = laundry;
        this.amphitheater = amphitheater;
        this.dumpstation = dumpstation;
        this.campstore = campstore;
        this.stafforvolunteerhostonsite = stafforvolunteerhostonsite;
        this.potablewater = potablewater;
        this.iceavailableforsale = iceavailableforsale;
        this.firewoodforsale = firewoodforsale;
        this.ampitheater = ampitheater;
        this.foodstoragelockers = foodstoragelockers;
    }

    public String getTrashrecyclingcollection() {
        return trashrecyclingcollection;
    }

    public List<String> getToilets() {
        return toilets;
    }

    public String getInternetconnectivity() {
        return internetconnectivity;
    }

    public List<String> getShowers() {
        return showers;
    }

    public String getCellphonereception() {
        return cellphonereception;
    }

    public String getLaundry() {
        return laundry;
    }

    public String getAmphitheater() {
        return amphitheater;
    }

    public String getDumpstation() {
        return dumpstation;
    }

    public String getCampstore() {
        return campstore;
    }

    public String getStafforvolunteerhostonsite() {
        return stafforvolunteerhostonsite;
    }

    public List<String> getPotablewater() {
        return potablewater;
    }

    public String getIceavailableforsale() {
        return iceavailableforsale;
    }

    public String getFirewoodforsale() {
        return firewoodforsale;
    }

    public String getAmpitheater() {
        return ampitheater;
    }

    public String getFoodstoragelockers() {
        return foodstoragelockers;
    }
}
