package com.knickglobal.yeloopp.mvpArchitecture.logoutApi;

import android.app.Activity;

import com.knickglobal.yeloopp.models.CommonPojo;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;
import com.knickglobal.yeloopp.utils.Common;

import java.util.HashMap;

public class LogoutPresenterImpl implements LogoutPresenter, LogoutPresenter.OnSuccess {

    private Activity activity;
    private LogoutView view;
    private LogoutInteracter interacter;

    public LogoutPresenterImpl(Activity activity, LogoutView view) {
        this.activity = activity;
        this.view = view;
        interacter = new LogoutInteracterImpl();
    }

    @Override
    public void goLogout(HashMap<String, String> map) {
        if (Common.InternetCheck(activity)) {
            view.showLogoutProgress();
            interacter.onLogout(activity, map, this);
        } else {
            view.noNetLogout(AppConstants.INTERNET_CHECK);
        }
    }

    @Override
    public void onLogoutSuccess(CommonPojo body) {
        view.hideLogoutProgress();
        view.onLogoutSuccess(body);
    }

    @Override
    public void onFailLogout(String error) {
        view.hideLogoutProgress();
        view.onLogoutError(error);
    }

    @Override
    public void onRetrofitFailed(String error) {
        view.hideLogoutProgress();
        if (Common.InternetCheck(activity)) {
            view.onLogoutError(error);
        } else {
            view.noNetLogout(AppConstants.INTERNET_CHECK);
        }
    }
}