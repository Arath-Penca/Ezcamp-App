package com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds;

public class Addresses {

    private String postalCode;
    private String city;
    private String stateCode;
    private String line1;
    private String type;
    private String line3;
    private String line2;

    public Addresses(String postalCode, String city, String stateCode, String line1, String type, String line3, String line2) {
        this.postalCode = postalCode;
        this.city = city;
        this.stateCode = stateCode;
        this.line1 = line1;
        this.type = type;
        this.line3 = line3;
        this.line2 = line2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getStateCode() {
        return stateCode;
    }

    public String getLine1() {
        return line1;
    }

    public String getType() {
        return type;
    }

    public String getLine3() {
        return line3;
    }

    public String getLine2() {
        return line2;
    }
}
