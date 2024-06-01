package com.example.todobuddy.activity;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.todobuddy.model.Task;

import java.util.Calendar;
import java.util.Locale;

public class UpdateActivity extends AppCompatActivity {

    EditText au_et_title, au_et_description, au_et_duedate;
    Button au_btn_update, au_btn_delete;
    Toolbar toolbar;
    ActionBar actionBar;
    Task getTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Task");

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        au_et_title = findViewById(R.id.au_et_title);
        au_et_description = findViewById(R.id.au_et_description);
        au_et_duedate = findViewById(R.id.au_et_duedate);
        au_btn_update = findViewById(R.id.au_btn_update);
        au_btn_delete = findViewById(R.id.au_btn_delete);

        getTask = getIntent().getParcelableExtra("task");

        au_et_title.setText(getTask.getTitle());
        au_et_description.setText(getTask.getDescription());
        au_et_duedate.setText(getTask.getDueDate());

        au_et_duedate.setOnClickListener(v -> showDateTimePicker());

        au_btn_update.setOnClickListener(v -> {
            int newId = getTask.getId();
            String newTitle = au_et_title.getText().toString();
            String newDescription = au_et_description.getText().toString();
            String newDueDate = au_et_duedate.getText().toString();

            if (au_et_title.getText().toString().isEmpty()) {
                au_et_title.setError("Title harus di isi");
            } else if (au_et_duedate.getText().toString().isEmpty()) {
                au_et_duedate.setError("DueDate harus di isi");
            } else {
                DatabaseHelper databaseHelper = new DatabaseHelper(UpdateActivity.this);
                databaseHelper.updateTaskRecord(newId, newTitle, newDescription, newDueDate);
                Intent toHome = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(toHome);
                finish();
            }
        });

        au_btn_delete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
            builder.setTitle("Hapus Todo")
                    .setMessage("Apakah anda yakin ingin menghapus item ini?")
                    .setCancelable(true)
                    .setPositiveButton("YA", (dialog, which) -> {
                        DatabaseHelper databaseHelper = new DatabaseHelper(UpdateActivity.this);
                        databaseHelper.deleteTaskRecord(getTask.getId());

                        Intent toMain = new Intent(UpdateActivity.this, MainActivity.class);
                        startActivity(toMain);
                        finish();
                    })
                    .setNegativeButton("TIDAK", (dialog, which) -> dialog.cancel())
                    .show();
        });
    }

    private void showDateTimePicker() {
        Calendar calendar = Calendar.getInstance();
        new DateTimePickerDialog(this, (view, year, month, day, hour, minute) -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year, month, day, hour, minute);
            String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d %02d:%02d",
                    day, month + 1, year, hour, minute);
            au_et_duedate.setText(formattedDate);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE)).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Batal")
                .setMessage("Apakah anda ingin membatalkan perubahan pada form?")
                .setCancelable(true)
                .setPositiveButton("YA", (dialog, which) -> {
                    Intent toMain = new Intent(UpdateActivity.this, MainActivity.class);
                    startActivity(toMain);
                    finish();
                })
                .setNegativeButton("TIDAK", (dialog, which) -> dialog.cancel())
                .show();

        return super.onOptionsItemSelected(item);
    }
}
