package com.example.martins.tsiapplication.models;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.martins.tsiapplication.R;
import com.example.martins.tsiapplication.Utils;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Martins on 08/06/2017.
 */

public class TaskHolder extends RecyclerView.ViewHolder{
    private static final String TAG = TaskHolder.class.getSimpleName();
    private final TextView titleTextView;
    private final TextView subtitleTextView;
    private final TextView detailTextView;
    private final ImageView thumbnailImageView;

    public TaskHolder(View itemView) {
        super(itemView);
        titleTextView = (TextView)itemView.findViewById(R.id.to_do_title);
        subtitleTextView = (TextView)itemView.findViewById(R.id.to_do_detail);
        detailTextView = (TextView)itemView.findViewById(R.id.to_do_description);
        thumbnailImageView = (ImageView)itemView.findViewById(R.id.to_do_thumbnail);
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public TextView getSubtitleTextView() {
        return subtitleTextView;
    }

    public TextView getDetailTextView() {
        return detailTextView;
    }

    public ImageView getThumbnailImageView() {
        return thumbnailImageView;
    }

}
