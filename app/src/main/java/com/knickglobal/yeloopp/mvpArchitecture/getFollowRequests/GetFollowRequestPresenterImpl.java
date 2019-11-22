package com.knickglobal.yeloopp.mvpArchitecture.getFollowRequests;

import android.app.Activity;

import com.knickglobal.yeloopp.models.GetFollowRequestPojo;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;
import com.knickglobal.yeloopp.utils.Common;

import java.util.HashMap;

public class GetFollowRequestPresenterImpl implements GetFollowRequestPresenter,
        GetFollowRequestPresenter.OnSuccess {

    private Activity activity;
    private GetFollowRequestView view;
    private GetFollowRequestInteracter interacter;

    public GetFollowRequestPresenterImpl(Activity activity, GetFollowRequestView view) {
        this.activity = activity;
        this.view = view;
        interacter = new GetFollowRequestInteracterImpl();
    }

    @Override
    public void goGetFollowRequest(HashMap<String, String> map) {
        if (Common.InternetCheck(activity)) {
            view.showLoginProgress();
            interacter.getFollowRequestApi(activity, map, this);
        } else {
            view.noNetLogin(AppConstants.INTERNET_CHECK);
        }
    }

    @Override
    public void onGetFollowRequestSuccess(GetFollowRequestPojo body) {
        view.hideLoginProgress();
        view.onLoginSuccess(body);
    }

    @Override
    public void onFailGetFollowRequest(String error) {
        view.hideLoginProgress();
        view.onLoginError(error);
    }

    @Override
    public void onRetrofitFailed(String error) {
        view.hideLoginProgress();
        if (Common.InternetCheck(activity)) {
            view.onLoginError(error);
        } else {
            view.noNetLogin(AppConstants.INTERNET_CHECK);
        }
    }
}