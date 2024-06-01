package com.example.todobuddy.api;

import com.example.todobuddy.model.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiService {
    @GET("api/v1/todos")
    Call<List<Task>> getTasks ();

}
