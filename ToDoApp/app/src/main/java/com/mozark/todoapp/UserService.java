package com.mozark.todoapp;

import com.mozark.todoapp.model.Response;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {

    @GET("/api/?results=15")
    public Call<Response> getAllUsers();
}
