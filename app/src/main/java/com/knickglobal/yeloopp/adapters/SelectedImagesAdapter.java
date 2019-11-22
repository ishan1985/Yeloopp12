package com.knickglobal.yeloopp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.knickglobal.yeloopp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SelectedImagesAdapter extends RecyclerView.Adapter<SelectedImagesAdapter.SelectedImagesViewHolder> {

    private Activity activity;
    private List<String> images;

    public SelectedImagesAdapter(Activity activity, List<String> images) {
        this.activity = activity;
        this.images = images;
    }

    @NonNull
    @Override
    public SelectedImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.item_selected_images, parent, false);
        return new SelectedImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedImagesViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class SelectedImagesViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSelected;

        SelectedImagesViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSelected = itemView.findViewById(R.id.imgSelected);

        }

        public void bind(int position) {
            Glide.with(activity).load("file://" + images.get(position))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(imgSelected);
        }
    }

}