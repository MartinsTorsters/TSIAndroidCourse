package com.example.martins.tsiapplication.models;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.martins.tsiapplication.R;
import com.example.martins.tsiapplication.Utils;

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

    /* @Override
    protected void populateView(final View view, final TaskDO model, final int position) {
        final TextView titleTextView = (TextView) view.findViewById(R.id.to_do_title);
        titleTextView.setText(model.getName());
        final TextView subtitleTextView = (TextView) view.findViewById(R.id.to_do_description);
        subtitleTextView.setText(model.getDetails());
        final TextView detailTextView = (TextView) view.findViewById(R.id.to_do_detail);
        detailTextView.setText(model.getUrl());
        final ImageView thumbnailImageView = (ImageView) view.findViewById(R.id.to_do_thumbnail);
        thumbnailImageView.setImageDrawable(Utils.LoadImageFromWebOperations(model.getUrl()));
    }*/
}
