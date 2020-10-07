package com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds.operatingHours;

public class StandardHours {

    private String wednesday;
    private String monday;
    private String thursday;
    private String sunday;
    private String tuesday;
    private String friday;
    private String saturday;

    public StandardHours(String wednesday, String monday, String thursday, String sunday, String tuesday, String friday, String saturday) {
        this.wednesday = wednesday;
        this.monday = monday;
        this.thursday = thursday;
        this.sunday = sunday;
        this.tuesday = tuesday;
        this.friday = friday;
        this.saturday = saturday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public String getMonday() {
        return monday;
    }

    public String getThursday() {
        return thursday;
    }

    public String getSunday() {
        return sunday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public String getFriday() {
        return friday;
    }

    public String getSaturday() {
        return saturday;
    }
}
