package com.rlms.network.webapi;

import com.rlms.model.RLMSAPIResponse;
import com.rlms.model.UserRole;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIGetComplaints {

    @Headers("Accept: application/json")
    @POST("/RLMS/API/getAllComplaintsAssigned")
    Call<RLMSAPIResponse> getAllComplaintsFor(
            @Body UserRole userRole);

}
