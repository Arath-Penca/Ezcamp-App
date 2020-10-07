package com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds;

import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds.contacts.EmailAddresses;
import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds.contacts.PhoneNumbers;

import java.util.ArrayList;
import java.util.List;

public class Contacts {

    private ArrayList<PhoneNumbers> phoneNumbers;
    private ArrayList<EmailAddresses> emailAddresses;

    public Contacts(ArrayList<PhoneNumbers> phoneNumbers, ArrayList<EmailAddresses> emailAddresses) {
        this.phoneNumbers = phoneNumbers;
        this.emailAddresses = emailAddresses;
    }

    public List<PhoneNumbers> getPhoneNumbers() {
        return phoneNumbers;
    }

    public List<EmailAddresses> getEmailAddresses() {
        return emailAddresses;
    }
}
