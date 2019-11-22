package com.knickglobal.yeloopp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.knickglobal.yeloopp.R;

public class MyFollowingsAdapter extends RecyclerView.Adapter<MyFollowingsAdapter.MyFollowingsViewHolder> {

    private Activity activity;

    public MyFollowingsAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyFollowingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(activity)
                .inflate(R.layout.item_my_followings, parent,false);

        return new MyFollowingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyFollowingsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class MyFollowingsViewHolder extends RecyclerView.ViewHolder {

        MyFollowingsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
