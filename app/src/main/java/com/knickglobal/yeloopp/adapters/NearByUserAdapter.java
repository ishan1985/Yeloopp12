package com.knickglobal.yeloopp.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.knickglobal.yeloopp.R;
import com.knickglobal.yeloopp.activities.ShowOtherProfileActivity;

public class NearByUserAdapter extends RecyclerView.Adapter<NearByUserAdapter.NearbyUserViewHolder> {

    private Activity activity;

    public NearByUserAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public NearbyUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(activity)
                .inflate(R.layout.item_near_by_users,parent,false);

        return new NearbyUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NearbyUserViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class NearbyUserViewHolder extends RecyclerView.ViewHolder {

        NearbyUserViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(activity, ShowOtherProfileActivity.class));
                }
            });
        }
    }

}
