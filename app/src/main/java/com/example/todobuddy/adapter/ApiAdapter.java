package com.example.todobuddy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todobuddy.R;
import com.example.todobuddy.model.Task;

import java.util.List;

public class ApiAdapter extends RecyclerView.Adapter<ApiAdapter.TaskViewHolder> {

    public static List<Task> taskList;

    public ApiAdapter(List<Task> allTasks) {
        this.taskList = allTasks;
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView title_tv, description_tv;
        private CheckBox completed_cb;
        private TextView monthApi, dayApi, yearApi;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            title_tv = itemView.findViewById(R.id.title_tv);
            description_tv = itemView.findViewById(R.id.description_tv);
            completed_cb = itemView.findViewById(R.id.completed_cb);
            monthApi = itemView.findViewById(R.id.monthApi);
            dayApi = itemView.findViewById(R.id.dayApi);
            yearApi = itemView.findViewById(R.id.yearApi);
        }

        public void bind(Task task) {
            String[] date = task.getDueDate().split("-");
            String day = date[2];
            String month = date[1];
            String year = date[0];

            yearApi.setText(year);
            monthApi.setText(month);
            dayApi.setText(day);

            title_tv.setText(task.getTitle());
            description_tv.setText(task.getDescription());
            completed_cb.setChecked(task.isCompleted());
        }
    }
}
