package com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds;

public class Campsites {

    private String other;
    private String group;
    private String horse;
    private String totalsites;
    private String tentonly;
    private String electricalhookups;
    private String rvonly;
    private String walkboatto;

    public Campsites(String other, String group, String horse, String totalsites, String tentonly, String electricalhookups, String rvonly, String walkboatto) {
        this.other = other;
        this.group = group;
        this.horse = horse;
        this.totalsites = totalsites;
        this.tentonly = tentonly;
        this.electricalhookups = electricalhookups;
        this.rvonly = rvonly;
        this.walkboatto = walkboatto;
    }

    public String getOther() {
        return other;
    }

    public String getGroup() {
        return group;
    }

    public String getHorse() {
        return horse;
    }

    public String getTotalsites() {
        return totalsites;
    }

    public String getTentonly() {
        return tentonly;
    }

    public String getElectricalhookups() {
        return electricalhookups;
    }

    public String getRvonly() {
        return rvonly;
    }

    public String getWalkboatto() {
        return walkboatto;
    }
}
