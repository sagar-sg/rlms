package com.rlms.model;

/**
 * Created by Swapnil on 3/20/2017.
 */

public class Technician {

//    {"userName":null,
//            "firstName":"testFirst"
//            ,"lastName":"TestLast",
//            "companyName":"INDITECH",
//            "contactNumber":"9096136234",
//            "address":null,
//            "emailId":null,
//            "branchName":"Hinjewadi Branch",
//            "userId":4,
//            "branchCompanyMapId":8,
//            "companyId":null,
//            "userRoleId":17,
//            "appRegId":null,
//            "latitude":null,
//            "longitude":null,
//            "city":null,
//            "area":null,
//            "pinCode":null,
//            "userRoleName":null}

    private String branchCompanyMapId = "";
    private String branchName = "";
    private String companyName = "";
    private String firstName = "";
    private String lastName = "";
    private String userId = "";
    private int userRoleId = 0;
    private String address = "";
    private String contactNumber = "";
    private double latitude = 0;
    private double longitude = 0;
    private String pinCode = "";
    private String userRoleName = "";
    private String emailId = "";
    private String appRegId = "";
    private String area = "";
    private String city = "";
    private String companyId = "";

    public Technician() {
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBranchCompanyMapId() {
        return branchCompanyMapId;
    }

    public void setBranchCompanyMapId(String branchCompanyMapId) {
        this.branchCompanyMapId = branchCompanyMapId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        this.longitude = longitude;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAppRegId() {
        return appRegId;
    }

    public void setAppRegId(String appRegId) {
        this.appRegId = appRegId;
    }

    @Override
    public String toString() {
        return "Technician{" +
                "branchCompanyMapId='" + branchCompanyMapId + '\'' +
                ", branchName='" + branchName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userId='" + userId + '\'' +
                ", userRoleId=" + userRoleId +
                ", address='" + address + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", pinCode='" + pinCode + '\'' +
                ", userRoleName='" + userRoleName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", appRegId='" + appRegId + '\'' +
                '}';
    }
}
