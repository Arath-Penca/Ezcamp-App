package com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Articles;

public class ListingImage {

    private String credit;
    private String altText;
    private String description;
    private String caption;
    private String url;

    public ListingImage(String credit, String altText, String description, String caption, String url) {
        this.credit = credit;
        this.altText = altText;
        this.description = description;
        this.caption = caption;
        this.url = url;
    }

    public String getCredit() {
        return credit;
    }

    public String getAltText() {
        return altText;
    }

    public String getDescription() {
        return description;
    }

    public String getCaption() {
        return caption;
    }

    public String getUrl() {
        return url;
    }
}
