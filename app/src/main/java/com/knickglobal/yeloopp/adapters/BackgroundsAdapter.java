package com.knickglobal.yeloopp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.knickglobal.yeloopp.R;

public class BackgroundsAdapter extends RecyclerView.Adapter<BackgroundsAdapter.BackgroundsViewHolder> {

    private Activity activity;
    private int[] arrImages;

    private SelectBgListener listener;

    public BackgroundsAdapter(Activity activity, int[] arrImages, SelectBgListener listener) {
        this.activity = activity;
        this.arrImages = arrImages;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BackgroundsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity)
                .inflate(R.layout.item_backgrounds, parent, false);

        return new BackgroundsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BackgroundsViewHolder holder, final int position) {

        holder.imageBg.setImageResource(arrImages[position]);
        holder.imageBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onPositionSelected(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrImages.length;
    }

    public interface SelectBgListener {
        void onPositionSelected(int position);
    }

    class BackgroundsViewHolder extends RecyclerView.ViewHolder {
        ImageView imageBg;

        BackgroundsViewHolder(@NonNull View itemView) {
            super(itemView);

            imageBg = itemView.findViewById(R.id.imageBg);
        }
    }

}