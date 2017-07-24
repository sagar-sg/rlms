package com.rlms.network.webapi;

import com.rlms.model.RegisterComplaint;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIRegisterComplaint {

    @Headers("Accept: application/json")
    @POST("/RLMS/API/complaint/validateAndRegisterNewComplaint")
    Call<ResponseBody> validateAndRegisterComplaint(
            @Body RegisterComplaint registerComplaint);

}
