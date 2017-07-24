package com.rlms.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Complaints implements Parcelable {
    int id = 0;
    String complaintTitle = "";
    String complaintDesc = "";
    String complaintDate ="";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {

        dest.writeInt(id);
        dest.writeString(complaintTitle);
        dest.writeString(complaintDesc);
        dest.writeString(complaintDate);
    }

    public Complaints() {
    }

    public Complaints(int id, String complaintTitle, String complaintDesc, String complaintDate) {
        setId(id);
        setComplaintTitle(complaintTitle);
        setComplaintDesc(complaintDesc);
        setComplaintDate(complaintDate);
    }
    protected Complaints(Parcel in) {
        setId(in.readInt());
        setComplaintTitle(in.readString());
        setComplaintDesc(in.readString());
        setComplaintDate(in.readString());
    }

    public static final Creator<Complaints> CREATOR = new Creator<Complaints>() {
        @Override
        public Complaints createFromParcel(Parcel in) {
            return new Complaints(in);
        }

        @Override
        public Complaints[] newArray(int size) {
            return new Complaints[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComplaintTitle() {
        return complaintTitle;
    }

    public void setComplaintTitle(String complaintTitle) {
        this.complaintTitle = complaintTitle;
    }

    public String getComplaintDesc() {
        return complaintDesc;
    }

    public void setComplaintDesc(String complaintDesc) {
        this.complaintDesc = complaintDesc;
    }

    public String getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(String complaintDate) {
        this.complaintDate = complaintDate;
    }

    @Override
    public String toString() {
        return "Complaints{" +
                "id=" + id +
                ", complaintTitle='" + complaintTitle + '\'' +
                ", complaintDesc='" + complaintDesc + '\'' +
                ", complaintDate='" + complaintDate + '\'' +
                '}';
    }
}
