package com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds.operatingHours;

public class Exceptions {

    private String startDate;
    private String name;
    private String endDate;

    public Exceptions(String startDate, String name, String endDate) {
        this.startDate = startDate;
        this.name = name;
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getname() {
        return name;
    }

    public String getEndDate() {
        return endDate;
    }
}
