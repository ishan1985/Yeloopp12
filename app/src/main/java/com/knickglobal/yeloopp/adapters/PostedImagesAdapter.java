package com.knickglobal.yeloopp.adapters;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.knickglobal.yeloopp.R;

public class PostedImagesAdapter extends RecyclerView.Adapter<PostedImagesAdapter.PostedImagesViewHolder> {

    private Activity activity;

    public PostedImagesAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public PostedImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity)
                .inflate(R.layout.item_posted_images, parent, false);

        return new PostedImagesViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PostedImagesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class PostedImagesViewHolder extends RecyclerView.ViewHolder {

        ImageView postedImages;

        PostedImagesViewHolder(@NonNull View itemView) {
            super(itemView);

            postedImages = itemView.findViewById(R.id.postedImages);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;

            int dividedWidths = width / 3;

            ViewGroup.LayoutParams layoutParams = postedImages.getLayoutParams();
            layoutParams.height = dividedWidths;
            postedImages.setLayoutParams(layoutParams);

        }

    }

}
