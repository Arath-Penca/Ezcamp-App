package com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Articles;

public class DataArticles {

    private String latLong;
    private ListingImage listingImage;
    private String relatedParks;
    private String longitude;
    private String listingDescription;
    private String title;
    private String id;
    private String latitude;
    private String url;

    public DataArticles(String latLong, ListingImage listingImage, String relatedParks, String longitude, String listingDescription, String title, String id, String latitude, String url) {
        this.latLong = latLong;
        this.listingImage = listingImage;
        this.relatedParks = relatedParks;
        this.longitude = longitude;
        this.listingDescription = listingDescription;
        this.title = title;
        this.id = id;
        this.latitude = latitude;
        this.url = url;
    }

    public String getLatLong() {
        return latLong;
    }

    public ListingImage getListingImage() {
        return listingImage;
    }

    public String getRelatedParks() {
        return relatedParks;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getListingDescription() {
        return listingDescription;
    }

    public String getTitle() {
        return title;
    }

    public String getid() {
        return id;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getUrl() {
        return url;
    }
}
