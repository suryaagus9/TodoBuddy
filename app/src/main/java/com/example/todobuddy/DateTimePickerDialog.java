package com.example.todobuddy;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;

import java.util.Calendar;

public class DateTimePickerDialog {
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    public DateTimePickerDialog(Context context, OnDateTimeSetListener listener, int year, int month, int day, int hour, int minute) {
        datePickerDialog = new DatePickerDialog(context, (view, selectedYear, selectedMonth, selectedDay) -> {
            timePickerDialog = new TimePickerDialog(context, (view1, selectedHour, selectedMinute) -> {
                listener.onDateTimeSet(view1, selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute);
            }, hour, minute, true);
            timePickerDialog.show();
        }, year, month, day);
    }

    public void show() {
        datePickerDialog.show();
    }

    public interface OnDateTimeSetListener {
        void onDateTimeSet(View view, int year, int month, int day, int hour, int minute);
    }
}
