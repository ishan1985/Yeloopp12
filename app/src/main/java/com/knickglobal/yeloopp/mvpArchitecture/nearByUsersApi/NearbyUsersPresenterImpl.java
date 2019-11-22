package com.knickglobal.yeloopp.mvpArchitecture.nearByUsersApi;

import android.app.Activity;

import com.knickglobal.yeloopp.models.NearbyUsersPojo;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;
import com.knickglobal.yeloopp.utils.Common;

import java.util.HashMap;

public class NearbyUsersPresenterImpl implements NearbyUsersPresenter, NearbyUsersPresenter.OnSuccess {

    private Activity activity;
    private NearbyUsersView view;
    private NearbyUsersInteracter interacter;

    public NearbyUsersPresenterImpl(Activity activity, NearbyUsersView view) {
        interacter = new NearbyUsersInteracterImpl();
        this.activity = activity;
        this.view = view;
    }

    @Override
    public void goNearbyUsers(HashMap<String, String> map) {
        if (Common.InternetCheck(activity)) {
            view.showNearbyUsersProgress();
            interacter.onNearbyUsers(activity, map, this);
        } else {
            view.noNetNearbyUsers(AppConstants.INTERNET_CHECK);
        }
    }

    @Override
    public void onNearbyUsersSuccess(NearbyUsersPojo body) {
        view.hideNearbyUsersProgress();
        view.onNearbyUsersSuccess(body);
    }

    @Override
    public void onFailNearbyUsers(String error) {
        view.hideNearbyUsersProgress();
        view.onNearbyUsersError(error);
    }

    @Override
    public void onRetrofitFailedNearbyUsers(String error) {
        view.hideNearbyUsersProgress();
        if (Common.InternetCheck(activity)) {
            view.onNearbyUsersError(error);
        } else {
            view.noNetNearbyUsers(AppConstants.INTERNET_CHECK);
        }
    }

}
