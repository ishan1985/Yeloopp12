package com.knickglobal.yeloopp.adapters;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.knickglobal.yeloopp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DeviceImagesAdapter extends RecyclerView.Adapter
        <DeviceImagesAdapter.DeviceImagesViewHolder> {

    private Activity activity;
    private ArrayList<String> mediaList;

    private ImagesListener listener;
    private List<String> selectPosList = new ArrayList<>();

    public DeviceImagesAdapter(Activity activity, ArrayList<String> mediaList,
                               ImagesListener listener) {
        this.activity = activity;
        this.mediaList = mediaList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DeviceImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.item_images, parent, false);

        return new DeviceImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceImagesViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    public interface ImagesListener {
        void onImageSelect(String path);

        void onImageUnSelect(String path);
    }

    class DeviceImagesViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon, checkImages;

        DeviceImagesViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            checkImages=itemView.findViewById(R.id.checkImages);

            imgIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectPosList.contains(String.valueOf(getAdapterPosition()))) {
                        selectPosList.remove(String.valueOf(getAdapterPosition()));
                        checkImages.setVisibility(View.GONE);
                        listener.onImageUnSelect(mediaList.get(getAdapterPosition()));
                    } else {
                        selectPosList.add(String.valueOf(getAdapterPosition()));
                        checkImages.setVisibility(View.VISIBLE);
                        listener.onImageSelect(mediaList.get(getAdapterPosition()));
                    }
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
