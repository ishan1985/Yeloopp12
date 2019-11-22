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

public class SavedPostsAdapter extends RecyclerView.Adapter<SavedPostsAdapter.SavedPostsViewHolder> {

    private Activity activity;

    public SavedPostsAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public SavedPostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity)
                .inflate(R.layout.item_saved_posts, parent, false);

        return new SavedPostsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedPostsViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 30;
    }

    class SavedPostsViewHolder extends RecyclerView.ViewHolder {

        ImageView savedImages;

        SavedPostsViewHolder(@NonNull View itemView) {
            super(itemView);

            savedImages = itemView.findViewById(R.id.savedImages);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;

            int dividedWidths = width / 3;

            ViewGroup.LayoutParams layoutParams = savedImages.getLayoutParams();
            layoutParams.height = dividedWidths;
            savedImages.setLayoutParams(layoutParams);

        }
    }

}
