package com.example.todobuddy.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todobuddy.R;
import com.example.todobuddy.activity.UpdateActivity;
import com.example.todobuddy.database.DatabaseHelper;
import com.example.todobuddy.model.Task;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<Task> taskList;
    private Context context;

    public HomeAdapter(List<Task> taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.bind(task);

        holder.itemView.setOnClickListener(v -> {
            Intent toUpdate = new Intent(holder.itemView.getContext(), UpdateActivity.class);
            toUpdate.putExtra("task", task);
            holder.itemView.getContext().startActivity(toUpdate);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title_tv, description_tv;
        private CheckBox completed_cb;
        private TextView month, day, year, hour, minute;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title_tv = itemView.findViewById(R.id.title_tv);
            description_tv = itemView.findViewById(R.id.description_tv);
            completed_cb = itemView.findViewById(R.id.completed_cb);
            month = itemView.findViewById(R.id.month);
            day = itemView.findViewById(R.id.day);
            year = itemView.findViewById(R.id.year);
            hour = itemView.findViewById(R.id.hour);
            minute = itemView.findViewById(R.id.minute);

            completed_cb.setOnClickListener(v -> {
                int position = getAdapterPosition();
                Task task = taskList.get(position);
                DatabaseHelper db = new DatabaseHelper(context);
                boolean newCompletedStatus = !task.isCompleted();
                task.setCompleted(newCompletedStatus);

                db.completeTaskRecord(task.getId(), newCompletedStatus);

                taskList.remove(position);
                if (newCompletedStatus) {
                    taskList.add(0, task);
                } else {
                    taskList.add(task);
                }

                notifyDataSetChanged();
            });
        }

        public void bind(Task task) {
            String[] dateTime = task.getDueDate().split(" ");
            String[] date = dateTime[0].split("/");
            String[] time = dateTime[1].split(":");

            int dayFix = Integer.parseInt(date[0]);
            int monthFix = Integer.parseInt(date[1]);
            int yearFix = Integer.parseInt(date[2]);

            int hourFix = Integer.parseInt(time[0]);
            int minuteFix = Integer.parseInt(time[1]);

            day.setText(String.format("%02d", dayFix));
            month.setText(String.format("%02d", monthFix));
            year.setText(String.format("%04d", yearFix));
            hour.setText(String.format("%02d:", hourFix));
            minute.setText(String.format("%02d", minuteFix));

            title_tv.setText(task.getTitle());
            description_tv.setText(task.getDescription());
            completed_cb.setChecked(task.isCompleted());
        }
    }
}
