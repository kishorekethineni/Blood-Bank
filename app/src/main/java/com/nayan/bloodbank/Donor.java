package com.nayan.bloodbank;


public class Donor {
    String name;
    String mobileNumber;
    String city;
    String bloodGroup;
    String lat;
    String lan;
    String adhar;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public Donor() {

    }

    public Donor(String name, String mobileNumber, String bloodGroup, String city, String lat, String lng,String adhar) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.bloodGroup = bloodGroup;
        this.city = city;
        this.lat = lat;
        this.lan = lng;
        this.adhar = adhar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAdhar(){return adhar;}

    public void setAdhar(String adhar) {this.adhar = adhar;}

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
