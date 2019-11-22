package com.knickglobal.yeloopp.adapters;

import android.app.Activity;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.knickglobal.yeloopp.R;
import com.knickglobal.yeloopp.models.CommonPojo;
import com.knickglobal.yeloopp.models.GetFollowRequestPojo;
import com.knickglobal.yeloopp.mvpArchitecture.acceptRequestApi.AcceptRequestPresenter;
import com.knickglobal.yeloopp.mvpArchitecture.acceptRequestApi.AcceptRequestPresenterImpl;
import com.knickglobal.yeloopp.mvpArchitecture.acceptRequestApi.AcceptRequestView;
import com.knickglobal.yeloopp.sharedPreferences.App;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;
import com.knickglobal.yeloopp.utils.Common;
import com.knickglobal.yeloopp.utils.CustomProgressDialog;

import java.util.HashMap;
import java.util.List;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.FriendRequestViewHolder> {

    private Activity activity;
    private List<GetFollowRequestPojo.Detail> details;
    private String acceptRejectS;

    public FriendRequestAdapter(Activity activity, List<GetFollowRequestPojo.Detail> details) {
        this.activity = activity;
        this.details = details;
    }

    @NonNull
    @Override
    public FriendRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity)
                .inflate(R.layout.item_friend_request, parent, false);

        return new FriendRequestViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FriendRequestViewHolder holder, int position) {

        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    class FriendRequestViewHolder extends RecyclerView.ViewHolder implements AcceptRequestView {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        TextView descriptionRequest;
        ImageView imageRequest;
        Button acceptRequest, removeRequest;

        CustomProgressDialog dialog;
        AcceptRequestPresenter presenter;

        FriendRequestViewHolder(@NonNull View itemView) {
            super(itemView);

            dialog = new CustomProgressDialog(activity);
            presenter = new AcceptRequestPresenterImpl(activity, this);

            descriptionRequest = itemView.findViewById(R.id.descriptionRequest);
            imageRequest = itemView.findViewById(R.id.imageRequest);

            acceptRequest = itemView.findViewById(R.id.acceptRequest);
            acceptRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    acceptRejectApi("accept");
                }
            });
            removeRequest = itemView.findViewById(R.id.removeRequest);
            removeRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    acceptRejectApi("remove");
                }
            });
        }

        public void bind(int position) {

            String nameRequest = details.get(position).getUserDetails().getName();
            String imageRequestS = details.get(position).getUserDetails().getName();

            String white = "  wants to be friend with you.";
            doColor(nameRequest, activity.getResources().getColor(R.color.blueColor));
            doColor(white, activity.getResources().getColor(R.color.grayColor));

            descriptionRequest.setText(builder, TextView.BufferType.SPANNABLE);

            Glide.with(activity).load(imageRequestS)
                    .placeholder(R.drawable.ic_placeholder).into(imageRequest);

        }

        private void doColor(String string, int color) {
            SpannableString redSpannable = new SpannableString(string);
            redSpannable.setSpan(new ForegroundColorSpan(color),
                    0, string.length(), 0);
            builder.append(redSpannable);
        }

        private void acceptRejectApi(String acceptReject) {

            acceptRejectS = acceptReject;

            String id = App.getAppPreference().getString(AppConstants.USER_ID);

            HashMap<String, String> map = new HashMap<>();
            map.put("accepting_user_id", id);
            map.put("requesting_user_id", details.get(getAdapterPosition()).getUid());
            map.put("accept_or_remove", acceptReject);

            presenter.goAcceptRequest(map);

        }

        @Override
        public void showAcceptRequestProgress() {
            if (dialog != null)
                dialog.show();
        }

        @Override
        public void hideAcceptRequestProgress() {
            if (dialog.isShowing())
                dialog.dismiss();
        }

        @Override
        public void onAcceptRequestSuccess(CommonPojo body) {
            Common.showToast(activity, body.getMessage());
            if (acceptRejectS.equalsIgnoreCase("accept")||
                    acceptRejectS.equalsIgnoreCase("remove")) {
                details.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
            }
        }

        @Override
        public void onAcceptRequestError(String error) {
            Common.showToast(activity, error);
        }

        @Override
        public void noNetAcceptRequest(String error) {
            Common.showToast(activity, error);
        }

    }

}
