package com.knickglobal.yeloopp.mvpArchitecture.getFriendsApi;

import android.app.Activity;

import com.knickglobal.yeloopp.models.FriendsListPojo;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;
import com.knickglobal.yeloopp.utils.Common;

import java.util.HashMap;

public class GetFriendsPresenterImpl implements GetFriendsPresenter, GetFriendsPresenter.OnSuccess {

    private Activity activity;
    private GetFriendsView view;
    private GetFriendsInteracter interacter;

    public GetFriendsPresenterImpl(Activity activity, GetFriendsView view) {
        this.activity = activity;
        this.view = view;
        interacter = new GetFriendsInteracterImpl();
    }

    @Override
    public void goGetFriends(HashMap<String, String> map) {
        if (Common.InternetCheck(activity)) {
            view.showGetFriendsProgress();
            interacter.getGetFriendsApi(activity, map, this);
        } else {
            view.noNetGetFriends(AppConstants.INTERNET_CHECK);
        }
    }

    @Override
    public void onGetFriendsSuccess(FriendsListPojo body) {
        view.hideGetFriendsProgress();
        view.onGetFriendsSuccess(body);
    }

    @Override
    public void onFailGetFriends(String error) {
        view.hideGetFriendsProgress();
        view.onGetFriendsError(error);
    }

    @Override
    public void onRetrofitFailedGetFriends(String error) {
        view.showGetFriendsProgress();
        if (Common.InternetCheck(activity)) {
            view.onGetFriendsError(error);
        } else {
            view.noNetGetFriends(AppConstants.INTERNET_CHECK);
        }
    }
}