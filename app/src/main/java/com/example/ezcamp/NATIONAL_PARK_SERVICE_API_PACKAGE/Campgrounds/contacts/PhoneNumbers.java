package com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds.contacts;

public class PhoneNumbers {

    private String phoneNumber;
    private String description;
    private String extension;
    private String type;

    public PhoneNumbers(String phoneNumber, String description, String extension, String type) {
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.extension = extension;
        this.type = type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public String getExtension() {
        return extension;
    }

    public String getType() {
        return type;
    }
}
