package com.rlms.model;

public class VisitDetails {

//    ":[{"userRoleId":null,"complaintTechMapId":3,"fromDate":null,"toDate":null," +
//            ""fromDateDtr":"31-Mar-2017 11:03:00 AM"," +
//            ""toDateStr":"31-Mar-2017 05:03:00 PM","totalTime":"6 hours 0 mins","remark":null}

    String userRoleId = "";
    int complaintTechMapId = 0;
    String fromDate = "";
    String toDate = "";
    String fromDateDtr = "";
    String toDateStr = "";
    String totalTime = "";
    String remark  = "";

    public VisitDetails() {
    }

    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    public int getComplaintTechMapId() {
        return complaintTechMapId;
    }

    public void setComplaintTechMapId(int complaintTechMapId) {
        this.complaintTechMapId = complaintTechMapId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFromDateDtr() {
        return fromDateDtr;
    }

    public void setFromDateDtr(String fromDateDtr) {
        this.fromDateDtr = fromDateDtr;
    }

    public String getToDateStr() {
        return toDateStr;
    }

    public void setToDateStr(String toDateStr) {
        this.toDateStr = toDateStr;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "VisitDetails{" +
                "userRoleId='" + userRoleId + '\'' +
                ", complaintTechMapId=" + complaintTechMapId +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", fromDateDtr='" + fromDateDtr + '\'' +
                ", toDateStr='" + toDateStr + '\'' +
                ", totalTime='" + totalTime + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
