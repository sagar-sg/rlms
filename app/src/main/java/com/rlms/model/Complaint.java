package com.rlms.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Complaint implements Serializable {

   /* {"complaintNumber":"1","registrationDate":1485369000000,"registrationDateStr":null,
            "serviceStartDate":1485369000000,"serviceStartDateStr":null,
            "actualServiceEndDate":1485369000000,"actualServiceEndDateStr":null,
            "liftNumber":"L202","customerName":"Infosys","liftAddress":null,
            "latitude":"18.024221245","longitude":"72.123456789","registrationType":null,
            "registrationTypeStr":null,
            "remark":"door is not opening on 3rd floor. other floors " +
            "are working fine.","status":"Assigned","title":null,
            "technicianDtls":null,"complaintId":1,"userRoleId":null,
            "liftCustoMapId":null,"complaintent":null}*/

    private int complaintId = 0;
    private int userRoleId = 0;
    private int liftCustoMapId = 0;
    private String complaintNumber = "";
    private long registrationDate = 0;
    private long registrationDateStr = 0;
    private long serviceStartDate = 0;
    private long serviceStartDateStr=0;
    private long actualServiceEndDate=0;
    private long actualServiceEndDateStr = 0;
    private String liftNumber = "";
    private String customerName = "";
    private String liftAddress = "";
    private String latitude = "";
    private String longitude = "";
    private String registrationType = "";
    private String registrationTypeStr = "";
    private String remark = "";
    private String status = "";
    private String title = "No Title";
    private String technicianDtls = "";
    private String complaintent = "";
    private String regType;
    private int complaintTechMapId;


    public Complaint() {
    }

    public Complaint(int complaintId, String status) {
        this.complaintId = complaintId;
        this.status = status;
    }

    public int getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public int getLiftCustoMapId() {
        return liftCustoMapId;
    }

    public void setLiftCustoMapId(int liftCustoMapId) {
        this.liftCustoMapId = liftCustoMapId;
    }

    public String getComplaintNumber() {
        return complaintNumber;
    }

    public void setComplaintNumber(String complaintNumber) {
        this.complaintNumber = complaintNumber;
    }

    public long getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(long registrationDate) {
        this.registrationDate = registrationDate;
    }

    public long getRegistrationDateStr() {
        return registrationDateStr;
    }

    public void setRegistrationDateStr(long registrationDateStr) {
        this.registrationDateStr = registrationDateStr;
    }

    public long getServiceStartDate() {
        return serviceStartDate;
    }

    public void setServiceStartDate(long serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    public long getActualServiceEndDateStr() {
        return actualServiceEndDateStr;
    }

    public void setActualServiceEndDateStr(long actualServiceEndDateStr) {
        this.actualServiceEndDateStr = actualServiceEndDateStr;
    }

    public String getLiftNumber() {
        return liftNumber;
    }

    public void setLiftNumber(String liftNumber) {
        this.liftNumber = liftNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLiftAddress() {
        return liftAddress;
    }

    public void setLiftAddress(String liftAddress) {
        this.liftAddress = liftAddress;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public String getRegistrationTypeStr() {
        return registrationTypeStr;
    }

    public void setRegistrationTypeStr(String registrationTypeStr) {
        this.registrationTypeStr = registrationTypeStr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTechnicianDtls() {
        return technicianDtls;
    }

    public void setTechnicianDtls(String technicianDtls) {
        this.technicianDtls = technicianDtls;
    }

    public String getComplaintent() {
        return complaintent;
    }

    public void setComplaintent(String complaintent) {
        this.complaintent = complaintent;
    }

    public long getServiceStartDateStr() {
        return serviceStartDateStr;
    }

    public void setServiceStartDateStr(long serviceStartDateStr) {
        this.serviceStartDateStr = serviceStartDateStr;
    }

    public long getActualServiceEndDate() {
        return actualServiceEndDate;
    }

    public void setActualServiceEndDate(long actualServiceEndDate) {
        this.actualServiceEndDate = actualServiceEndDate;
    }

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    public int getComplaintTechMapId() {
        return complaintTechMapId;
    }

    public void setComplaintTechMapId(int complaintTechMapId) {
        this.complaintTechMapId = complaintTechMapId;
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "complaintId=" + complaintId +
                ", userRoleId=" + userRoleId +
                ", liftCustoMapId=" + liftCustoMapId +
                ", complaintNumber='" + complaintNumber + '\'' +
                ", registrationDate=" + registrationDate +
                ", registrationDateStr=" + registrationDateStr +
                ", serviceStartDate=" + serviceStartDate +
                ", serviceStartDateStr=" + serviceStartDateStr +
                ", actualServiceEndDate=" + actualServiceEndDate +
                ", actualServiceEndDateStr=" + actualServiceEndDateStr +
                ", liftNumber='" + liftNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", liftAddress='" + liftAddress + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", registrationType='" + registrationType + '\'' +
                ", registrationTypeStr='" + registrationTypeStr + '\'' +
                ", remark='" + remark + '\'' +
                ", status='" + status + '\'' +
                ", title='" + title + '\'' +
                ", technicianDtls='" + technicianDtls + '\'' +
                ", complaintent='" + complaintent + '\'' +
                ", regType='" + regType + '\'' +
                ", complaintTechMapId=" + complaintTechMapId +
                '}';
    }
}
