package com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds;

import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds.operatingHours.Exceptions;
import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds.operatingHours.StandardHours;

import java.util.ArrayList;
import java.util.List;

public class OperatingHours {

    private ArrayList<Exceptions> exceptions;
    private String description;
    private StandardHours standardHours;
    private String name;

    public OperatingHours(ArrayList<Exceptions> exceptions, String description, StandardHours standardHours, String name) {
        this.exceptions = exceptions;
        this.description = description;
        this.standardHours = standardHours;
        this.name = name;
    }

    public List<Exceptions> getExceptions() {
        return exceptions;
    }

    public String getDescription() {
        return description;
    }

    public StandardHours getStandardHours() {
        return standardHours;
    }

    public String getName() {
        return name;
    }
}
