package com.rlms.model;


public class RLMSAPIResponse {

    boolean status = false;
    String response = "";
    String jsonElement = null;
    String jsonArray = null;

    public RLMSAPIResponse() {
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

    public String getJsonElement() {
        return jsonElement;
    }

    public void setJsonElement(String jsonElement) {
        this.jsonElement = jsonElement;
    }

    public String getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(String jsonArray) {
        this.jsonArray = jsonArray;
    }

    @Override
    public String toString() {
        return "RLMSAPIResponse{" +
                "status=" + status +
                ", response='" + response + '\'' +
                ", jsonElement='" + jsonElement + '\'' +
                ", jsonArray='" + jsonArray + '\'' +
                '}';
    }
}
