package com.knickglobal.yeloopp.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.knickglobal.yeloopp.R;

import java.util.ArrayList;
import java.util.List;

public class DeviceVideosAdapter extends RecyclerView.Adapter<DeviceVideosAdapter.VideosViewHolder> {

    private Activity activity;
    private ArrayList<String> mediaList;

    private VideoClickListener listener;

    public DeviceVideosAdapter(Activity activity, ArrayList<String> mediaList,
                               VideoClickListener listener) {
        this.activity = activity;
        this.mediaList = mediaList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.item_videos, parent, false);

        return new VideosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideosViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    public interface VideoClickListener {
        void videoClick(String videoPath);
    }

    class VideosViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;

        VideosViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);

            imgIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.videoClick(mediaList.get(getAdapterPosition()));
                }
            });

            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;

            int dividedWidths = width / 3;

            ViewGroup.LayoutParams layoutParams = imgIcon.getLayoutParams();
            layoutParams.height = dividedWidths;
            imgIcon.setLayoutParams(layoutParams);

        }

        private void bind(int position) {

            Glide.with(activity).load("file://" + mediaList.get(position))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(imgIcon);

        }
    }
}