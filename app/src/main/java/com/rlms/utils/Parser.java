package com.rlms.utils;

import com.rlms.constants.Params;
import com.rlms.model.Complaint;
import com.rlms.model.Technician;
import com.rlms.model.VisitDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Parser {

    static Log Log = new Log();
    static String TAG = "Parser";

    public static ArrayList<Complaint> getParsedComplaintsList(String stringToParse) {

        ArrayList<Complaint> complaintArrayList = new ArrayList<Complaint>();

        try {
            JSONArray jsonArray = new JSONArray(stringToParse);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Complaint complaint = new Complaint();
//                complaint.setTitle(jsonObject.getString(Params.TITLE));
//                complaint.setUserRoleId(jsonObject.getInt(Params.USER_ROLE_ID));
                complaint.setRegistrationDate(jsonObject.optLong(Params.REGISTRATION_DATE));
                complaint.setStatus(jsonObject.optString(Params.STATUS));
                complaint.setCustomerName(jsonObject.optString(Params.CUSTOMER_NAME));
                complaint.setLiftNumber(jsonObject.optString(Params.LIFT_NUMBER));
                complaint.setRemark(jsonObject.optString(Params.REMARK));
                complaint.setComplaintNumber(jsonObject.optString(Params.COMPLAINT_NUMBER));
                complaint.setComplaintId(jsonObject.optInt(Params.COMPLAINT_ID));
                complaint.setComplaintTechMapId(jsonObject.optInt(Params.COMPLAINT_TECH_MAP_ID));
                complaint.setActualServiceEndDate(jsonObject.optLong(Params.ACTUAL_SERVICE_END_DATE));

                complaintArrayList.add(complaint);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "complaints parsed size = " + complaintArrayList.size());

        return complaintArrayList;

    }

    public static Technician getParsedTechnician(String stringToParse) {

        Technician technician = new Technician();

//            for(int i=0;i<jsonArray.length();i++){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(stringToParse);

            technician.setUserRoleName(jsonObject.getString(Params.USER_NAME_TECH));
            technician.setFirstName(jsonObject.getString(Params.FISRT_NAME_TECH));
            technician.setLastName(jsonObject.getString(Params.LAST_NAME_TECH));
            try {
                technician.setAddress(jsonObject.getString(Params.ADDRESS));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            try {
                technician.setAppRegId(jsonObject.getString(Params.ADD_REG_ID));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            try {
                technician.setLongitude(jsonObject.getDouble(Params.LONGITUDE));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            try {
                technician.setCompanyId(jsonObject.getString(Params.COMNY_ID));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            try {
                technician.setLatitude(jsonObject.getDouble(Params.LASTITUDE));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            technician.setBranchCompanyMapId(jsonObject.getString(Params.BRANCH_COMP_ID));
            technician.setBranchName(jsonObject.getString(Params.BRANCH_NAME_TECH));
            technician.setCompanyName(jsonObject.getString(Params.COMPANY_NAME_TECH));
            technician.setContactNumber(jsonObject.getString(Params.CONTACT_NO));

            try {
                technician.setPinCode(jsonObject.getString(Params.PINCODE));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            try {
                technician.setUserRoleId(jsonObject.getInt(Params.USER_ROLE_ID_TECH));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            technician.setUserId(jsonObject.getString(Params.USER_ID_TECH));

            technician.setUserRoleName(jsonObject.getString(Params.USER_ROLE_NO));
            try {
                technician.setCity(jsonObject.getString(Params.CITY));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            try {
                technician.setEmailId(jsonObject.getString(Params.EMAILID));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            try {
                technician.setArea(jsonObject.getString(Params.AREA));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }

        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        Log.d(TAG, "techician parsed = " + technician.toString());

        return technician;

    }

    public static ArrayList<VisitDetails> getParsedVisitDetailsList(String response) {

        ArrayList<VisitDetails> visitDetailsArrayList = new ArrayList<VisitDetails>();

        try {
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                VisitDetails visitDetails = new VisitDetails();
                try {
                    visitDetails.setRemark(jsonObject.getString("remark"));
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                try {
                    visitDetails.setUserRoleId(jsonObject.getString("userRoleId"));
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                try {
                    visitDetails.setToDateStr(jsonObject.getString("toDateStr"));
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                try {
                    visitDetails.setFromDateDtr(jsonObject.getString("fromDateDtr"));
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                try {
                    visitDetails.setToDate(jsonObject.getString("fromDate"));
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                try {
                    visitDetails.setFromDate(jsonObject.getString("toDate"));
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                try {
                    visitDetails.setTotalTime(jsonObject.getString("totalTime"));
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                visitDetails.setComplaintTechMapId(jsonObject.getInt("complaintTechMapId"));

                visitDetailsArrayList.add(visitDetails);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "visitDetailsArrayList parsed size = " + visitDetailsArrayList.size());

        return visitDetailsArrayList;

    }
}
