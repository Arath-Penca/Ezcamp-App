package com.example.ezcamp;

public class YourCampsItem {

    private String ImageUrl;
    private String Name;
    private String WeatherOnCampDay;
    private String Alerts;

    public YourCampsItem(String imageUrl, String name, String weatherOnCampDay, String alerts) {
        ImageUrl = imageUrl;
        Name = name;
        WeatherOnCampDay = weatherOnCampDay;
        Alerts = alerts;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getName() {
        return Name;
    }

    public String getWeatherOnCampDay() {
        return WeatherOnCampDay;
    }

    public String getAlerts() {
        return Alerts;
    }
}
