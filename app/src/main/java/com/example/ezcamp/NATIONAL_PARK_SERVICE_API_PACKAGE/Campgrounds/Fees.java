package com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds;

public class Fees {

    private String cost;
    private String description;
    private String title;

    public Fees(String cost, String description, String title) {
        this.cost = cost;
        this.description = description;
        this.title = title;
    }

    public String getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
