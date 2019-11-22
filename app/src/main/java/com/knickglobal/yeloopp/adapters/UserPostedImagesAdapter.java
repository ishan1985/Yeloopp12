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

public class UserPostedImagesAdapter extends RecyclerView.Adapter<UserPostedImagesAdapter.UserPostedImagesViewHolder> {

    private Activity activity;

    public UserPostedImagesAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public UserPostedImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity)
                .inflate(R.layout.item_posted_images, parent, false);

        return new UserPostedImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPostedImagesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 30;
    }

    class UserPostedImagesViewHolder extends RecyclerView.ViewHolder {
        ImageView postedImages;
        UserPostedImagesViewHolder(@NonNull View itemView) {
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
