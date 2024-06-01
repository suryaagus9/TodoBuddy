package com.example.todobuddy.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.todobuddy.R;
import com.example.todobuddy.activity.AddActivity;
import com.example.todobuddy.adapter.HomeAdapter;
import com.example.todobuddy.database.DatabaseHelper;
import com.example.todobuddy.model.Task;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String PREF_USERNAME = "username";
    private RecyclerView home_recyclerView;
    private HomeAdapter adapter;
    private ProgressBar progressBar;
    private ImageButton main_btn_add;
    private List<Task> taskList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        main_btn_add = view.findViewById(R.id.main_btn_add);
        home_recyclerView = view.findViewById(R.id.home_recyclerView);

        taskList = new ArrayList<>();
        loadData();

        adapter = new HomeAdapter(taskList, getContext());
        home_recyclerView.setAdapter(adapter);

        main_btn_add.setOnClickListener(v -> {
            Intent toAdd = new Intent(getContext(), AddActivity.class);
            startActivity(toAdd);
        });
    }

    public void loadData() {
        // Mengambil username dari SharedPreferences.
        //Mendapatkan userId dari database menggunakan username.
        //Mengambil data tugas dari database menggunakan userId.
        //Memproses data tugas dan menambahkannya ke taskList.

        // mengambil id user dari SQlite berdasarkan username yang tersimpan dalam SharedPreferences
        SharedPreferences preferencesUsername = getActivity().getSharedPreferences("username", Context.MODE_PRIVATE);
        String username = preferencesUsername.getString(PREF_USERNAME, "");
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        int userId = databaseHelper.getUserId(username);

        Cursor cursor = databaseHelper.getTaskRecords(userId);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String dueDate = cursor.getString(cursor.getColumnIndexOrThrow("duedate"));
                boolean completed = cursor.getInt(cursor.getColumnIndexOrThrow("completed")) == 1;

                taskList.add(new Task(id, title, description, dueDate, completed));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
