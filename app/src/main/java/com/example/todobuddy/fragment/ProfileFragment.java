package com.example.todobuddy.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.todobuddy.R;
import com.example.todobuddy.activity.LoginActivity;
import com.example.todobuddy.database.DatabaseHelper;
import com.example.todobuddy.model.User;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileFragment extends Fragment {
    private final static String PREF_LOGIN = "login";
    private final static String PREF_USERNAME = "username";

    SharedPreferences preferencesLogin;
    private TextView tv_name, tv_username;
    private ImageView civ_profile;
    private Button btn_logout;
    private LottieAnimationView progressBar;
//    private ProgressBar progressBar;
    DatabaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences preferencesUsername = getActivity().getSharedPreferences("username", Context.MODE_PRIVATE);
        String username = preferencesUsername.getString(PREF_USERNAME, "");
        db = new DatabaseHelper(getActivity());

        tv_name = view.findViewById(R.id.tv_name);
        tv_username = view.findViewById(R.id.tv_username);
        civ_profile = view.findViewById(R.id.civ_profile);
        btn_logout = view.findViewById(R.id.btn_logout);
        progressBar = view.findViewById(R.id.progressBar);

        tv_name.setVisibility(View.GONE);
        tv_username.setVisibility(View.GONE);
        civ_profile.setVisibility(View.GONE);
        btn_logout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_name.setVisibility(View.VISIBLE);
                        tv_username.setVisibility(View.VISIBLE);
                        civ_profile.setVisibility(View.VISIBLE);
                        btn_logout.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);

                        if (!username.isEmpty()) {
                            User user = db.getUserInfo(username);
                            tv_name.setText(user.getName());
                            tv_username.setText(user.getUsername());
                        }

                        btn_logout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                preferencesLogin = getContext().getSharedPreferences("logincheck", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferencesLogin.edit();
                                editor.putBoolean(PREF_LOGIN, false);
                                editor.apply();

                                Intent toLogin = new Intent(getContext(), LoginActivity.class);
                                startActivity(toLogin);
                                getActivity().finish();
                            }
                        });
                    }
                });
            }
        });
    }
}
