package com.knickglobal.yeloopp.adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.knickglobal.yeloopp.R;
import com.knickglobal.yeloopp.activities.ShowOtherProfileActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowingPostAdapter extends RecyclerView.Adapter<FollowingPostAdapter.FollowingPostViewHolder> {

    private Activity activity;

    public FollowingPostAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public FollowingPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity)
                .inflate(R.layout.item_following_posts, parent, false);

        return new FollowingPostViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FollowingPostViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class FollowingPostViewHolder extends RecyclerView.ViewHolder {

        ImageView imagePost;
        CircleImageView imageUserPost;


        FollowingPostViewHolder(@NonNull View itemView) {
            super(itemView);

            imagePost = itemView.findViewById(R.id.imagePost);
            imageUserPost = itemView.findViewById(R.id.imageUserPost);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;

            int dividedWidths = width - 140;

            ViewGroup.LayoutParams layoutParams = imagePost.getLayoutParams();
            layoutParams.height = dividedWidths;
            imagePost.setLayoutParams(layoutParams);

            imageUserPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(activity, ShowOtherProfileActivity.class));
                }
            });
        }
    }

}
