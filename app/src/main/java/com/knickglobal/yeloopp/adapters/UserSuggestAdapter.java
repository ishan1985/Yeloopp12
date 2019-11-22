package com.knickglobal.yeloopp.adapters;

import android.app.Activity;

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
import com.knickglobal.yeloopp.models.FollowUsersPojo;
import com.knickglobal.yeloopp.models.NearbyUsersPojo;
import com.knickglobal.yeloopp.mvpArchitecture.followUsersApi.FollowUsersPresenter;
import com.knickglobal.yeloopp.mvpArchitecture.followUsersApi.FollowUsersPresenterImpl;
import com.knickglobal.yeloopp.mvpArchitecture.followUsersApi.FollowUsersView;
import com.knickglobal.yeloopp.sharedPreferences.App;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;
import com.knickglobal.yeloopp.utils.Common;
import com.knickglobal.yeloopp.utils.CustomProgressDialog;

import java.util.HashMap;
import java.util.List;

public class UserSuggestAdapter extends RecyclerView.Adapter<UserSuggestAdapter.UserSuggestViewHolder> {

    private Activity activity;
    private List<NearbyUsersPojo.Detail> details;
    private FollowThreeListener listener;

    public UserSuggestAdapter(Activity activity, List<NearbyUsersPojo.Detail> details,
                              FollowThreeListener listener) {
        this.activity = activity;
        this.details = details;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserSuggestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity)
                .inflate(R.layout.item_user_suggest, parent, false);

        return new UserSuggestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserSuggestViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public interface FollowThreeListener {
        void onThreeFollowed();
    }

    class UserSuggestViewHolder extends RecyclerView.ViewHolder implements FollowUsersView {

        ImageView imageChooseThree;
        TextView nameChooseThree, tagChooseThree;
        Button followChooseThree;

        CustomProgressDialog dialog;
        FollowUsersPresenter presenter;

        UserSuggestViewHolder(@NonNull View itemView) {
            super(itemView);

            dialog = new CustomProgressDialog(activity);
            presenter = new FollowUsersPresenterImpl(activity, this);

            imageChooseThree = itemView.findViewById(R.id.imageChooseThree);
            nameChooseThree = itemView.findViewById(R.id.nameChooseThree);
            tagChooseThree = itemView.findViewById(R.id.tagChooseThree);
            followChooseThree = itemView.findViewById(R.id.followChooseThree);
            followChooseThree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = App.getAppPreference().getString(AppConstants.USER_ID);

                    HashMap<String, String> map = new HashMap<>();
                    map.put("uid", id);
                    map.put("requested_user_id", details.get(getAdapterPosition()).getUid());
                    presenter.goFollowUsers(map);
                }
            });
        }

        public void bind(int position) {

            Glide.with(activity).load(details.get(position).getImageUrl())
                    .placeholder(R.drawable.ic_placeholder).into(imageChooseThree);

            if (!details.get(position).getName().isEmpty())
                nameChooseThree.setText(details.get(position).getName());

//            if (!details.get(position).getName().tag())
//                nameChooseThree.setText(details.get(position).getName());

        }

        @Override
        public void showFollowUsersProgress() {
            if (dialog != null)
                dialog.show();
        }

        @Override
        public void hideFollowUsersProgress() {
            if (dialog.isShowing())
                dialog.dismiss();
        }

        @Override
        public void onFollowUsersSuccess(FollowUsersPojo body) {
            Common.showToast(activity, body.getMessage());
            switch (body.getStatus()) {
                case "1":
                    followChooseThree.setText(R.string.following);
                    followChooseThree.setBackground(activity.getResources().getDrawable(R.drawable.button_bg_light_blue));
                    break;
                case "2":
                    followChooseThree.setText(R.string.follow);
                    followChooseThree.setBackground(activity.getResources().getDrawable(R.drawable.button_bg_blue));
                    break;
                case "3":
                    followChooseThree.setText(R.string.following);
                    followChooseThree.setBackground(activity.getResources().getDrawable(R.drawable.button_bg_light_blue));
                    listener.onThreeFollowed();
                    break;
            }
        }

        @Override
        public void onFollowUsersError(String error) {
            Common.showToast(activity, error);
        }

        @Override
        public void noNetFollowUsers(String error) {
            Common.showToast(activity, error);
        }

    }

}