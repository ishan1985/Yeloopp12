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

public class PopularPostAdapter extends RecyclerView.Adapter<PopularPostAdapter.PopularPostViewHolder> {

    private Activity activity;

    public PopularPostAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public PopularPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity)
                .inflate(R.layout.item_following_posts,parent,false);

        return new PopularPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularPostViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class PopularPostViewHolder extends RecyclerView.ViewHolder {

        ImageView imagePost;

        PopularPostViewHolder(@NonNull View itemView) {
            super(itemView);

            imagePost=itemView.findViewById(R.id.imagePost);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;

            int dividedWidths = width -140;

            ViewGroup.LayoutParams layoutParams = imagePost.getLayoutParams();
            layoutParams.height = dividedWidths;
            imagePost.setLayoutParams(layoutParams);
        }
    }

}
