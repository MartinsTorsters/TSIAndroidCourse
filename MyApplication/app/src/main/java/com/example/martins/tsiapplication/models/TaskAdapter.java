package com.example.martins.tsiapplication.models;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.martins.tsiapplication.activities.AddNewTaskActivity;
import com.example.martins.tsiapplication.activities.EditTaskActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

public class TaskAdapter extends FirebaseRecyclerAdapter<TaskDO, TaskHolder> {
    private static final String TAG = TaskAdapter.class.getSimpleName();
    private final Context context;

    public TaskAdapter(Class<TaskDO> modelClass, int modelLayout, Class<TaskHolder> viewHolderClass, DatabaseReference ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(TaskHolder viewHolder, TaskDO model, final int position) {
        viewHolder.getTitleTextView().setText(model.getName());
        Glide.with(context).load(model.getUrl()).into(viewHolder.getThumbnailImageView());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TaskDO item = getItem(position);
                final Intent intent = new Intent(context, AddNewTaskActivity.class);
                intent.putExtra("title", item.getName());
                intent.putExtra("id", item.getId());
                intent.putExtra("description", item.getDetails());
                intent.putExtra("url", item.getUrl());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    context.startActivity(intent);
                } catch (Exception e) {
                    Log.w("rr", e.getLocalizedMessage());
                }
                //context.getApplicationContext().startActivity(intent);
            }
        });
    }
}
