package com.rlms.model;

public class RegisterComplaint {

    private int liftCustoMapId = 0;
    private String complaintsTitle = "";
    private String registrationType = "";
    private String complaintsRemark = "";

    public RegisterComplaint() {
    }

    public int getLiftCustoMapId() {
        return liftCustoMapId;
    }

    public void setLiftCustoMapId(int liftCustoMapId) {
        this.liftCustoMapId = liftCustoMapId;
    }

    public String getComplaintsTitle() {
        return complaintsTitle;
    }

    public void setComplaintsTitle(String complaintsTitle) {
        this.complaintsTitle = complaintsTitle;
    }

    public String getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public String getComplaintsRemark() {
        return complaintsRemark;
    }

    public void setComplaintsRemark(String complaintsRemark) {
        this.complaintsRemark = complaintsRemark;
    }

    @Override
    public String toString() {
        return "RegisterComplaint{" +
                "liftCustoMapId=" + liftCustoMapId +
                ", complaintsTitle='" + complaintsTitle + '\'' +
                ", registrationType='" + registrationType + '\'' +
                ", complaintsRemark='" + complaintsRemark + '\'' +
                '}';
    }
}
