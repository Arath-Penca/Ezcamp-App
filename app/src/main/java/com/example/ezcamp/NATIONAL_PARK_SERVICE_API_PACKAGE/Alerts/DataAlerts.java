package com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Alerts;

import com.google.gson.annotations.SerializedName;

public class DataAlerts {

    private String title;
    private String id;
    private String description;
    private String category;
    private String url;
    private String parkCode;

    public DataAlerts(String title, String id, String description, String category, String url, String parkCode) {
        this.title = title;
        this.id = id;
        this.description = description;
        this.category = category;
        this.url = url;
        this.parkCode = parkCode;
    }

    public String getTitle() {
        return title;
    }

    public String getid() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getUrl() {
        return url;
    }

    public String getParkCode() {
        return parkCode;
    }
}