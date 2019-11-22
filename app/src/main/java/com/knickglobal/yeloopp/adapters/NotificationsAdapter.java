package com.knickglobal.yeloopp.adapters;

import android.app.Activity;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.knickglobal.yeloopp.R;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder> {

    private Activity activity;

    public NotificationsAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity)
                .inflate(R.layout.item_notification, parent, false);

        return new NotificationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsViewHolder holder, int position) {

        SpannableStringBuilder builder = new SpannableStringBuilder();
        String red = "Sadie Hamilton";
        SpannableString redSpannable= new SpannableString(red);
        redSpannable.setSpan(new ForegroundColorSpan(activity.getResources().getColor(R.color.blueColor)), 0, red.length(), 0);
        builder.append(redSpannable);

        String white = " and ";
        SpannableString whiteSpannable= new SpannableString(white);
        whiteSpannable.setSpan(new ForegroundColorSpan(activity.getResources().getColor(R.color.grayColor)), 0, white.length(), 0);
        builder.append(whiteSpannable);

        String blue = "24 others ";
        SpannableString blueSpannable = new SpannableString(blue);
        blueSpannable.setSpan(new ForegroundColorSpan(activity.getResources().getColor(R.color.blueColor)), 0, blue.length(), 0);
        builder.append(blueSpannable);

        String blue1 = "liked your post.";
        SpannableString blueSpannable1 = new SpannableString(blue1);
        blueSpannable1.setSpan(new ForegroundColorSpan(activity.getResources().getColor(R.color.blueColor)), 0, blue.length(), 0);
        builder.append(blueSpannable1);

        holder.textNotify.setText(builder, TextView.BufferType.SPANNABLE);

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class NotificationsViewHolder extends RecyclerView.ViewHolder {
        TextView textNotify;
        NotificationsViewHolder(@NonNull View itemView) {
            super(itemView);

            textNotify=itemView.findViewById(R.id.textNotify);
        }
    }
}