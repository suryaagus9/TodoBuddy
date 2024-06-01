package com.example.todobuddy.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.todobuddy.DateTimePickerDialog;
import com.example.todobuddy.R;
import com.example.todobuddy.database.DatabaseHelper;

import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {
    private static final String PREF_USERNAME = "username";
    private EditText aa_et_title, aa_et_description, aa_et_duedate;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private Button aa_btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Task");

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        aa_et_title = findViewById(R.id.aa_et_title);
        aa_et_description = findViewById(R.id.aa_et_description);
        aa_et_duedate = findViewById(R.id.aa_et_duedate);
        aa_btn_add = findViewById(R.id.aa_btn_add);

        aa_et_duedate.setOnClickListener(v -> showDateTimePicker());

        aa_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = aa_et_title.getText().toString();
                String description = aa_et_description.getText().toString();
                String dueDate = aa_et_duedate.getText().toString();

                if (aa_et_title.getText().toString().isEmpty()) {
                    aa_et_title.setError("Title harus di isi");
                } else if (aa_et_duedate.getText().toString().isEmpty()) {
                    aa_et_duedate.setError("DueDate harus di isi");
                } else {
                    SharedPreferences preferences = getSharedPreferences("username", Context.MODE_PRIVATE);
                    String username = preferences.getString(PREF_USERNAME, "");

                    DatabaseHelper db = new DatabaseHelper(AddActivity.this);
                    int userId = db.getUserId(username);

                    db.insertTaskRecord( title, description, dueDate, userId);
                    startActivity(new Intent(AddActivity.this, MainActivity.class));
                    finish();
                }
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDateTimePicker() {
        Calendar calendar = Calendar.getInstance();
        new DateTimePickerDialog(this, (view, year, month, day, hour, minute) -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year, month, day, hour, minute);
            String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d %02d:%02d",
                    day, month + 1, year, hour, minute);
            aa_et_duedate.setText(formattedDate);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE)).show();
    }

}
