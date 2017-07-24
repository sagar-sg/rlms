package com.rlms.model;

public class VisitDetailsResponse {

    boolean status = false;
    String response = "";

    public VisitDetailsResponse() {
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "VisitDetailsResponse{" +
                "status=" + status +
                ", response='" + response + '\'' +
                '}';
    }
}
