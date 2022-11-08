package com.example.demo.hr.user.api;

import com.example.demo.hr.user.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {

    @GET("/user/all")
    Call<List<User>> getAllUsers();

    @POST("/user/save")
    Call<User> createUser(@Body User user);

    @POST("/user/login")
    Call<User> loginUser(@Body User user);

}
