package com.example.todobuddy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.todobuddy.R;
import com.example.todobuddy.database.DatabaseHelper;
import com.example.todobuddy.fragment.HomeFragment;

public class LoginActivity extends AppCompatActivity {
    private final static String PREFERENCES = "preferences";
    private final static String PREF_LOGIN = "login";
    private final static String PREF_USERNAME = "username";

    SharedPreferences preferences;
    EditText login_et_username, login_et_password;
    Button login_btn_login, login_btn_register;
    DatabaseHelper databaseHelper;
    String username, getUsername;
    String password, getPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // menangani hasil dari register
        ActivityResultLauncher<Intent> intentResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result ->{
                    if (result.getResultCode() == RESULT_OK){

                    }
                }
        );

        preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);

        databaseHelper = new DatabaseHelper(this);
        login_et_username = findViewById(R.id.login_et_username);
        login_et_password = findViewById(R.id.login_et_password);
        login_btn_login = findViewById(R.id.login_btn_login);
        login_btn_register = findViewById(R.id.login_btn_register);

        login_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = login_et_username.getText().toString().trim();
                password = login_et_password.getText().toString().trim();

                if (username.isEmpty()) {
                    login_et_username.setError("Isi Username");
                } else if (password.isEmpty()) {
                    login_et_password.setError("Isi Password");
                } else if (databaseHelper.checkUser(username, password)) {
                    SharedPreferences preferencesLogin = LoginActivity.this.getSharedPreferences("logincheck", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferencesLogin.edit();
                    editor.putBoolean(PREF_LOGIN, true);
                    editor.apply();

                    SharedPreferences preferencesUsername = LoginActivity.this.getSharedPreferences("username", MODE_PRIVATE);
                    SharedPreferences.Editor editorUsername = preferencesUsername.edit();
                    editorUsername.putString(PREF_USERNAME, username);
                    editorUsername.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Username atau Password Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });

        login_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intentResult.launch(intent);
                login_et_username.setText("");
                login_et_password.setText("");
            }
        });


    }
}