package com.rlms.network;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by sagar on 7/16/2017.
 */

public interface APIUploadPhoto {
    @Multipart
    @POST("/upload")
    Call<ResponseBody> getDetails(@Part("liftCustomerMapId") RequestBody empsno,
                                  @Part("photoType ")RequestBody deliveryTime,
                                  @Part("uploadFile") MultipartBody.Part part,
                                  @Part("remarks")RequestBody remarks,
                                  @Part("receiver")RequestBody receivedBy,
                                  @Part("Address") RequestBody ipAddress
    );
}
