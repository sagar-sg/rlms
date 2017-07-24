package com.rlms.model;

/**
 * Created by Swapnil on 3/20/2017.
 */

public class RegisterTechnician {

//1)	contactNumber
//2)	  appRegId
//3)	  latitude
//4)	  Longitude
//5)	address

    String contactNumber = "";
    String appRegId = "";
    double latitude = 0;
    double longitude = 0;
    String address = "";

    public RegisterTechnician() {
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAppRegId() {
        return appRegId;
    }

    public void setAppRegId(String appRegId) {
        this.appRegId = appRegId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "RegisterTechnician{" +
                "contactNumber='" + contactNumber + '\'' +
                ", appRegId='" + appRegId + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
