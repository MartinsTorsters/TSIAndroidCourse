package com.example.martins.tsiapplication.models;

import android.content.Context;

import com.bumptech.glide.Glide;
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
    protected void populateViewHolder(TaskHolder viewHolder, TaskDO model, int position) {
        viewHolder.getTitleTextView().setText(model.getName());
        Glide.with(context).load(model.getUrl()).into(viewHolder.getThumbnailImageView());
    }

}
