package com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds;

public class Images {

    private String credit;
    private String imageId;
    private String altText;
    private String title;
    private String caption;
    private String url;

    public Images(String credit, String imageId, String altText, String title, String caption, String url) {
        this.credit = credit;
        this.imageId = imageId;
        this.altText = altText;
        this.title = title;
        this.caption = caption;
        this.url = url;
    }

    public String getCredit() {
        return credit;
    }

    public String getImageId() {
        return imageId;
    }

    public String getAltText() {
        return altText;
    }

    public String getTitle() {
        return title;
    }

    public String getCaption() {
        return caption;
    }

    public String getUrl() {
        return url;
    }
}
