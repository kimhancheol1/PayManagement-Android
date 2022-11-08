package com.example.demo.common.retrofit;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private Retrofit retrofit;
    //private String myBaseURL = "http://192.168.0.94:8080/";
    //private String myBaseURL = "http://10.0.2.2:8080/";
    private String myBaseURL = "http://172.16.0.92:8080/";
    public RetrofitService(){
        initialzeRetrofit();
    }

    private void initialzeRetrofit(){

        retrofit = new Retrofit.Builder()
                .baseUrl(myBaseURL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }
    public Retrofit getRetrofit(){
        return retrofit;
    }
}
