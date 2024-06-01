package com.example.todobuddy.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.todobuddy.api.ApiService;
import com.example.todobuddy.R;
import com.example.todobuddy.api.RetrofitClient;
import com.example.todobuddy.adapter.ApiAdapter;
import com.example.todobuddy.model.Task;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiFragment extends Fragment {

    private ApiService apiService;
    private RecyclerView api_recyclerView;
    private ApiAdapter apiAdapter;
    private LinearLayout ll_lost_connection;
    private Button btnRetry;
    private LottieAnimationView progressBar;
//    private ProgressBar progressBar;
    private List<Task> allTasks = new ArrayList<>();
    private List<String> dueDates = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_api, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiService = RetrofitClient.getClient();
        progressBar = view.findViewById(R.id.progressBar);
        ll_lost_connection = view.findViewById(R.id.ll_lost_connection);
        btnRetry = view.findViewById(R.id.btnRetry);

        api_recyclerView = view.findViewById(R.id.api_recyclerView);
        api_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        apiAdapter = new ApiAdapter(allTasks);
        api_recyclerView.setAdapter(apiAdapter);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData();
            }
        });

        fetchData();
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null) {
                return activeNetwork.isConnected() || activeNetwork.isConnectedOrConnecting();
            }
        }
        return false;
    }

    private void fetchData() {
        if (isConnected()) {
            progressBar.setVisibility(View.VISIBLE);
            ll_lost_connection.setVisibility(View.GONE);
            api_recyclerView.setVisibility(View.GONE);

            Call<List<Task>> call = apiService.getTasks();
            call.enqueue(new Callback<List<Task>>() {
                @Override
                public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                    progressBar.setVisibility(View.GONE);
                    api_recyclerView.setVisibility(View.VISIBLE);
                    List<Task> tasks = response.body();
                    apiAdapter = new ApiAdapter(tasks);
                    api_recyclerView.setAdapter(apiAdapter);
                }

                @Override
                public void onFailure(Call<List<Task>> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    ll_lost_connection.setVisibility(View.VISIBLE);
                }
            });
        } else {
            progressBar.setVisibility(View.GONE);
            api_recyclerView.setVisibility(View.GONE);
            ll_lost_connection.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
}
