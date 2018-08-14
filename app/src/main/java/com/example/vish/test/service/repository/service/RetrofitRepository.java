package com.example.vish.test.service.repository.service;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
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
    IApiClient api;
    String title;
    Context context;


    public RetrofitRepository(Context applicationContext) {
        //create retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(IApiClient.class);
        context = applicationContext;
    }

    public LiveData<List<DataModel>> getDataList() {
        //add observer to monitor remote data
        final MutableLiveData<List<DataModel>> data = new MutableLiveData<>();
        Call<Response> call = api.getDetails();

        call.enqueue(new Callback<Response>() {

            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                //check if response is a success
                if(response.isSuccessful()) {
                    simulateDelay();
                    Response responseBody = response.body();
                    title = responseBody.title.toString();
                    row = responseBody.rows;
                    data.setValue(row);
                    setTitleValue(title);
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    private void setTitleValue(String title) {
        //save title
        SharedPreferences pref = context.getSharedPreferences("TITLE_PREF",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("TITLE",title);
        edit.commit();

    }

    private void simulateDelay() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
