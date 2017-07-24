package com.rlms.network.webapi;

import com.rlms.model.GetVisitDetails;
import com.rlms.model.RLMSAPIResponse;
import com.rlms.model.UserRole;
import com.rlms.model.VisitDetailsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIGetSiteVisitDetails {

    @Headers("Accept: application/json")
    @POST("RLMS/API/complaint/getAllVisitsForComplaint")
    Call<VisitDetailsResponse> getAllComplaintsFor(
            @Body GetVisitDetails getVisitDetails);

}
