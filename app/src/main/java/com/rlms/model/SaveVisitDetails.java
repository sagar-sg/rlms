package com.rlms.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Swapnil on 5/6/2017.
 */

public class SaveVisitDetails {

    @SerializedName("userRoleId")
    @Expose
    private String userRoleId;
    @SerializedName("complaintTechMapId")
    @Expose
    private String complaintTechMapId;

    @SerializedName("fromDateDtr")
    @Expose
    private String fromDateDtr;
    @SerializedName("toDateStr")
    @Expose
    private String toDateStr;
    @SerializedName("totalTime")
    @Expose
    private String totalTime;
    @SerializedName("remark")
    @Expose
    private String remark;

    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getComplaintTechMapId() {
        return complaintTechMapId;
    }

    public void setComplaintTechMapId(String complaintTechMapId) {
        this.complaintTechMapId = complaintTechMapId;
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
        return "SaveVisitDetails{" +
                "userRoleId=" + userRoleId +
                ", complaintTechMapId=" + complaintTechMapId +
                ", fromDateDtr='" + fromDateDtr + '\'' +
                ", toDateStr='" + toDateStr + '\'' +
                ", totalTime='" + totalTime + '\'' +
                ", remark=" + remark +
                '}';
    }
}
