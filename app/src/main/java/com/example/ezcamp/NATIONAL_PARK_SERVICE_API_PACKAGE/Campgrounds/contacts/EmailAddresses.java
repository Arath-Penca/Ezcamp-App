package com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds.contacts;

public class EmailAddresses {

    private String description;
    private String emailAddress;

    public EmailAddresses(String description, String emailAddress) {
        this.description = description;
        this.emailAddress = emailAddress;
    }

    public String getDescription() {
        return description;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
