package com.knickglobal.yeloopp.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.knickglobal.yeloopp.R;

public class MyFollowersAdapter extends RecyclerView.Adapter<MyFollowersAdapter.MyFollowersViewHolder> {

    private Activity activity;

    public MyFollowersAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyFollowersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity)
                .inflate(R.layout.item_my_followers, parent, false);

        return new MyFollowersViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyFollowersViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class MyFollowersViewHolder extends RecyclerView.ViewHolder {

        ImageView menuBtnMyFollowers;

        MyFollowersViewHolder(@NonNull View itemView) {
            super(itemView);

            menuBtnMyFollowers=itemView.findViewById(R.id.menuBtnMyFollowers);
            menuBtnMyFollowers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogMyFollower();
                }
            });
        }
    }

    private void dialogMyFollower() {

        final Dialog dialog=new Dialog(activity);
        dialog.setContentView(R.layout.dialog_remove_follower);

        final Button keepFollowerBtn=dialog.findViewById(R.id.keepFollowerBtn);
        final Button removeFollowerBtn=dialog.findViewById(R.id.removeFollowerBtn);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        keepFollowerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keepFollowerBtn.setBackground(activity.getResources().getDrawable(R.drawable.button_bg_blue));
                keepFollowerBtn.setTextColor(activity.getResources().getColor(R.color.whiteColor));

                removeFollowerBtn.setBackground(activity.getResources().getDrawable(R.drawable.stroke_black));
                removeFollowerBtn.setTextColor(activity.getResources().getColor(R.color.black80));

                dialog.dismiss();
            }
        });

        removeFollowerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFollowerBtn.setBackground(activity.getResources().getDrawable(R.drawable.button_bg_blue));
                removeFollowerBtn.setTextColor(activity.getResources().getColor(R.color.whiteColor));

                keepFollowerBtn.setBackground(activity.getResources().getDrawable(R.drawable.stroke_black));
                keepFollowerBtn.setTextColor(activity.getResources().getColor(R.color.black80));

                dialog.dismiss();
            }
        });

        dialog.show();
    }

}