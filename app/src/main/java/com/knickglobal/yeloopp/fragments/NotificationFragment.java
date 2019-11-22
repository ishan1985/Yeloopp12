package com.knickglobal.yeloopp.fragments;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.knickglobal.yeloopp.R;
import com.knickglobal.yeloopp.adapters.FriendRequestAdapter;
import com.knickglobal.yeloopp.adapters.NotificationsAdapter;
import com.knickglobal.yeloopp.models.GetFollowRequestPojo;
import com.knickglobal.yeloopp.mvpArchitecture.getFollowRequests.GetFollowRequestPresenter;
import com.knickglobal.yeloopp.mvpArchitecture.getFollowRequests.GetFollowRequestPresenterImpl;
import com.knickglobal.yeloopp.mvpArchitecture.getFollowRequests.GetFollowRequestView;
import com.knickglobal.yeloopp.sharedPreferences.App;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;
import com.knickglobal.yeloopp.utils.Common;
import com.knickglobal.yeloopp.utils.CustomProgressDialog;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment implements GetFollowRequestView {

    private Activity activity;

    private RecyclerView friendReqRC, notificationsRC;

    private GetFollowRequestPresenter presenter;
    private CustomProgressDialog dialog;

    private TextView textFriendRequests;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        inits(view);

        return view;
    }

    private void inits(View view) {

        activity = getActivity();

        dialog = new CustomProgressDialog(activity);
        presenter = new GetFollowRequestPresenterImpl(activity, this);

        textFriendRequests = view.findViewById(R.id.textFriendRequests);

        friendReqRC = view.findViewById(R.id.friendReqRC);
        friendReqRC.setLayoutManager(new LinearLayoutManager(activity));

        notificationsRC = view.findViewById(R.id.notificationsRC);
        notificationsRC.setLayoutManager(new LinearLayoutManager(activity));
        notificationsRC.setAdapter(new NotificationsAdapter(activity));


        // @@@@@@@@@@@@@@@@@@ Get Follow Requests Api @@@@@@@@@@@@@@@@@@
        String id = App.getAppPreference().getString(AppConstants.USER_ID);

        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", id);

        presenter.goGetFollowRequest(map);
    }

    @Override
    public void showLoginProgress() {
        if (dialog != null)
            dialog.show();
    }

    @Override
    public void hideLoginProgress() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public void onLoginSuccess(GetFollowRequestPojo body) {
        Common.showToast(activity, body.getMessage());
        if (body.getDetails() != null) {
            friendReqRC.setVisibility(View.VISIBLE);
            textFriendRequests.setVisibility(View.VISIBLE);
            friendReqRC.setAdapter(new FriendRequestAdapter(activity, body.getDetails()));
        } else {
            textFriendRequests.setVisibility(View.GONE);
            friendReqRC.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoginError(String error) {
        Common.showToast(activity, error);
    }

    @Override
    public void noNetLogin(String error) {
        Common.showToast(activity, error);
    }
}