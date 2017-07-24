package com.rlms.model;

public class GetVisitDetails {

//    {"complaintTechMapId":"3","userRoleId":"2","remark":"not done yet"}

    String complaintTechMapId = "";
    String userRoleId = "";
    String remark = "";

    public GetVisitDetails() {
    }

    public String getComplaintTechMapId() {
        return complaintTechMapId;
    }

    public void setComplaintTechMapId(String complaintTechMapId) {
        this.complaintTechMapId = complaintTechMapId;
    }

    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "GetVisitDetails{" +
                "complaintTechMapId='" + complaintTechMapId + '\'' +
                ", userRoleId='" + userRoleId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
