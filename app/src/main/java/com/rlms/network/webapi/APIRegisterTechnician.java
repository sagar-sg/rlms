package com.rlms.network.webapi;

import com.rlms.model.RLMSAPIResponse;
import com.rlms.model.RegisterTechnician;
import com.rlms.model.Technician;
import com.rlms.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface APIRegisterTechnician {

    @POST("/RLMS/API/register/registerTechnicianDeviceByMblNo")
    Call<RLMSAPIResponse> callLoginUser(
            @Body RegisterTechnician registerTechnician);

}
