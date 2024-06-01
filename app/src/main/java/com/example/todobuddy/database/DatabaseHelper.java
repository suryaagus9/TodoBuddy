package com.example.todobuddy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todobuddy.model.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todo_database";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TASK = "table_task";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DUEDATE = "duedate";
    private static final String COLUMN_COMPLETED = "completed";
    private static final String COLUMN_TASK_USER_ID = "user_task_id";

    private static final String TABLE_USER = "table_user";
    private static final String COLUMN_ID_USER = "id_user";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USER + " ("
                + COLUMN_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_PASSWORD + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_TASK + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_DUEDATE + " TEXT, "
                + COLUMN_COMPLETED + " INTEGER DEFAULT 0,"
                + COLUMN_TASK_USER_ID + " INTEGER, "
                + "FOREIGN KEY (" + COLUMN_TASK_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID_USER +"))");

    }

    public void insertTaskRecord(String title, String description, String duedate, int userId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_DUEDATE, duedate);
        values.put(COLUMN_COMPLETED, 0);
        values.put(COLUMN_TASK_USER_ID, userId);
        db.insert(TABLE_TASK, null, values);
    }

    public void updateTaskRecord(int id, String title, String description, String duedate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_DUEDATE, duedate);
        db.update(TABLE_TASK, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteTaskRecord(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_TASK, COLUMN_ID + " = " + id, null);
    }

    public void completeTaskRecord(int taskId, boolean completed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("completed", completed ? 1 : 0);

        String selection = "id = ?";
        String[] selectionArgs = { String.valueOf(taskId) };

        db.update(TABLE_TASK, values, selection, selectionArgs);
    }


    public Cursor getTaskRecords(int userId) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TASK + " WHERE " + COLUMN_TASK_USER_ID + " = ? ORDER BY " + COLUMN_COMPLETED + " DESC";
        String[] selectionArgs = new String[]{String.valueOf(userId)};
        return db.rawQuery(query, selectionArgs);
    }

    // Register
    public void insertUserRecord(String name, String username, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, name);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        db.insert(TABLE_USER, null, values);
    }

    public int getUserId (String username) {
        SQLiteDatabase db = getReadableDatabase();
        String[]projection = {COLUMN_ID_USER};
        String selection = COLUMN_USERNAME + " = ?";
        String[]selectionArgs = {username};
        Cursor cursor = db.query(TABLE_USER, projection, selection, selectionArgs, null, null, null);
        int userId = -1;
        if (cursor.moveToFirst()){
            userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_USER));
        }
        cursor.close();
        db.close();
        return userId;
    }


    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        String selection = COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        String[] columns = {COLUMN_USERNAME};

        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        boolean exists = (cursor != null && cursor.getCount() > 0);

        if(cursor != null) {
            cursor.close();
        }
        db.close();
        return exists;
    }


    // Username and name to profileFragment
    public User getUserInfo(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_USER, null, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            String Username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
            cursor.close();
            return new User(name, Username);
        } else {
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

