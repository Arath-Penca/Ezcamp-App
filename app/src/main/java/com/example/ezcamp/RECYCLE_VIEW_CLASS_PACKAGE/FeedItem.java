package com.example.ezcamp.RECYCLE_VIEW_CLASS_PACKAGE;

public class FeedItem {

    private String ImageUrl;
    private String Title;
    private String Description;
    private String url;

    public FeedItem(String imageUrl, String title, String description, String url) {
        ImageUrl = imageUrl;
        Title = title;
        Description = description;
        this.url = url;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public String getUrl(){
        return url;
    }
}




