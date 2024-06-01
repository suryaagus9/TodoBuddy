package com.example.todobuddy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.todobuddy.R;
import com.example.todobuddy.database.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {
    EditText register_et_username, register_et_password, register_et_nama;
    Button register_btn_register;
    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        register_et_nama = findViewById(R.id.register_et_name);
        register_et_username = findViewById(R.id.register_et_username);
        register_et_password = findViewById(R.id.register_et_password);
        register_btn_register = findViewById(R.id.register_btn_register);
        btn_back = findViewById(R.id.btn_back);

        register_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = register_et_nama.getText().toString().trim();
                String username = register_et_username.getText().toString().trim();
                String password = register_et_password.getText().toString().trim();

                if (nama.isEmpty()) {
                    register_et_nama.setError("Isi Nama");
                } else if (username.isEmpty()) {
                    register_et_username.setError("Isi Username");
                } else if (password.isEmpty()) {
                    register_et_password.setError("Isi Password");
                } else {
                    DatabaseHelper db = new DatabaseHelper(RegisterActivity.this);
                    db.insertUserRecord(nama, username, password);

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
