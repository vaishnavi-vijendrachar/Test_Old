package com.example.vish.test.service.repository.service;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;


import com.example.vish.test.service.Database;
import com.example.vish.test.service.model.DataModel;
import com.example.vish.test.service.model.Response;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class RetrofitRepository {
    List<DataModel> row;
    Database database;
    IApiClient api;
    String title;


    public RetrofitRepository() {
        //create retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(IApiClient.class);
    }

    public LiveData<List<DataModel>> getDataList() {
        //add observer to monitor remote data
        final MutableLiveData<List<DataModel>> data = new MutableLiveData<>();
        Call<Response> call = api.getDetails();

        call.enqueue(new Callback<Response>() {

            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.isSuccessful()) {
                    simulateDelay();
                    Response responseBody = response.body();
                    title = responseBody.title.toString();
                    row = responseBody.rows;
                    data.setValue(row);

                    Log.d("vish", "title: "+title);
                }else{
                    Log.d("vish", "onResponse: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d("vish", "onFailure: "+ t.fillInStackTrace());
                data.setValue(null);
            }
        });

        return data;
    }

    private void simulateDelay() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
