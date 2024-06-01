package com.example.todobuddy.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {
    private int id;
    private String title;
    private String description;
    private String dueDate;
    private boolean completed;

    public Task(int id, String title, String description, String dueDate, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    protected Task(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        dueDate = in.readString();
        completed = in.readByte() != 0;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(dueDate);
        dest.writeByte((byte) (completed ? 1 : 0));
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
