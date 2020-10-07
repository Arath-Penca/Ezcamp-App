package com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds;

import java.util.ArrayList;
import java.util.List;

public class DataCampgrounds {

    private Contacts contacts;
    private String regulationsurl;
    private String weatheroverview;
    private Campsites campsites;
    private Accessibility accessibility;
    private String longitude;
    private String directionsoverview;
    private String reservationsurl;
    private String directionsUr;
    private ArrayList<Fees> fees;
    private String reservationssitesfirstcome;
    private String name;
    private String regulationsoverview;
    private ArrayList<OperatingHours> operatingHours;
    private String latLong;
    private String description;
    private ArrayList<Images> images;
    private String reservationssitesreservable;
    private String parkCode;
    private Amenities amenities;
    private ArrayList<Addresses> addresses;
    private String id;
    private String latitude;
    private String reservationsdescription;

    public DataCampgrounds(Contacts contacts, String regulationsurl, String weatheroverview, Campsites campsites, Accessibility accessibility, String longitude, String directionsoverview, String reservationsurl, String directionsUr, ArrayList<Fees> fees, String reservationssitesfirstcome, String name, String regulationsoverview, ArrayList<OperatingHours> operatingHours, String latLong, String description, ArrayList<Images> images, String reservationssitesreservable, String parkCode, Amenities amenities, ArrayList<Addresses> addresses, String id, String latitude, String reservationsdescription) {
        this.contacts = contacts;
        this.regulationsurl = regulationsurl;
        this.weatheroverview = weatheroverview;
        this.campsites = campsites;
        this.accessibility = accessibility;
        this.longitude = longitude;
        this.directionsoverview = directionsoverview;
        this.reservationsurl = reservationsurl;
        this.directionsUr = directionsUr;
        this.fees = fees;
        this.reservationssitesfirstcome = reservationssitesfirstcome;
        this.name = name;
        this.regulationsoverview = regulationsoverview;
        this.operatingHours = operatingHours;
        this.latLong = latLong;
        this.description = description;
        this.images = images;
        this.reservationssitesreservable = reservationssitesreservable;
        this.parkCode = parkCode;
        this.amenities = amenities;
        this.addresses = addresses;
        this.id = id;
        this.latitude = latitude;
        this.reservationsdescription = reservationsdescription;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public String getRegulationsurl() {
        return regulationsurl;
    }

    public String getWeatheroverview() {
        return weatheroverview;
    }

    public Campsites getCampsites() {
        return campsites;
    }

    public Accessibility getAccessibility() {
        return accessibility;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getDirectionsoverview() {
        return directionsoverview;
    }

    public String getReservationsurl() {
        return reservationsurl;
    }

    public String getDirectionsUr() {
        return directionsUr;
    }

    public List<Fees> getFees() {
        return fees;
    }

    public String getReservationssitesfirstcome() {
        return reservationssitesfirstcome;
    }

    public String getname() {
        return name;
    }

    public String getRegulationsoverview() {
        return regulationsoverview;
    }

    public List<OperatingHours> getOperatingHours() {
        return operatingHours;
    }

    public String getLatLong() {
        return latLong;
    }

    public String getDescription() {
        return description;
    }

    public List<Images> getImages() {
        return images;
    }

    public String getReservationssitesreservable() {
        return reservationssitesreservable;
    }

    public String getParkCode() {
        return parkCode;
    }

    public Amenities getAmenities() {
        return amenities;
    }

    public List<Addresses> getAddresses() {
        return addresses;
    }

    public String getid() {
        return id;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getReservationsdescription() {
        return reservationsdescription;
    }
}
