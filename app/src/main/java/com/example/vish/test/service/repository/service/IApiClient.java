package com.example.vish.test.service.repository.service;

import com.example.vish.test.service.model.Response;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IApiClient {
    String BASE_URL = "https://dl.dropboxusercontent.com/s/";
    @GET("2iodh4vg0eortkl/facts.json")
    Call<Response> getDetails();
}
