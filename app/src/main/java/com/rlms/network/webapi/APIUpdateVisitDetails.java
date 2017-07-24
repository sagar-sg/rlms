package com.rlms.network.webapi;

import com.rlms.model.RLMSAPIResponse;
import com.rlms.model.RegisterComplaint;
import com.rlms.model.SaveVisitDetails;
import com.rlms.model.VisitDetailsResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIUpdateVisitDetails {

    @POST("/RLMS/API/complaint/validateAndSaveSiteVisitDtls")
    Call<VisitDetailsResponse> validateAndSaveSiteVisitDetails(
            @Body SaveVisitDetails saveVisitDetails);

}
