package com.akhilcacharya.melanorml.Models;

import com.akhilcacharya.melanorml.Models.MelanormlQuery;
import com.akhilcacharya.melanorml.Models.MelanormlResult;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;


public interface MelanormlService {
@Headers({"Authorization: /* METAMIND_API_KEY_HERE */ })
    @POST("/vision/classify")
    void result(@Body MelanormlQuery query, Callback<MelanormlResult> cb);
}
