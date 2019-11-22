package com.knickglobal.yeloopp.mvpArchitecture.followUsersApi;

import android.app.Activity;

import com.knickglobal.yeloopp.models.FollowUsersPojo;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;
import com.knickglobal.yeloopp.utils.Common;

import java.util.HashMap;

public class FollowUsersPresenterImpl implements FollowUsersPresenter,
        FollowUsersPresenter.OnSuccess{

    private Activity activity;
    private FollowUsersView view;
    private FollowUsersInteracter interacter;

    public FollowUsersPresenterImpl(Activity activity, FollowUsersView view) {
        this.activity = activity;
        this.view = view;
        interacter = new FollowUsersInteracterImpl();
    }

    @Override
    public void goFollowUsers(HashMap<String, String> map) {
        if (Common.InternetCheck(activity)) {
            view.showFollowUsersProgress();
            interacter.onFollowUsers(activity, map, this);
        } else {
            view.noNetFollowUsers(AppConstants.INTERNET_CHECK);
        }
    }

    @Override
    public void onFollowUsersSuccess(FollowUsersPojo body) {
        view.hideFollowUsersProgress();
        view.onFollowUsersSuccess(body);
    }

    @Override
    public void onFailFollowUsers(String error) {
        view.hideFollowUsersProgress();
        view.onFollowUsersError(error);
    }

    @Override
    public void onRetrofitFailed(String error) {
        view.hideFollowUsersProgress();
        if (Common.InternetCheck(activity)) {
            view.onFollowUsersError(error);
        } else {
            view.noNetFollowUsers(AppConstants.INTERNET_CHECK);
        }
    }
}
