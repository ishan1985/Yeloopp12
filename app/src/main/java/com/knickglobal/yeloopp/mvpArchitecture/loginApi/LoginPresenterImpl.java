package com.knickglobal.yeloopp.mvpArchitecture.loginApi;

import android.app.Activity;
import android.util.Log;

import com.knickglobal.yeloopp.models.LoginPojo;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;
import com.knickglobal.yeloopp.utils.Common;

import java.util.HashMap;

public class LoginPresenterImpl implements LoginPresenter, LoginPresenter.OnSuccess {

    private static final String TAG = "Beant LoginPresImpl";

    private Activity activity;
    private LoginView view;
    private LoginInteracter interacter;

    public LoginPresenterImpl(Activity activity, LoginView view) {
        this.activity = activity;
        this.view = view;
        this.interacter = new LoginInteracterImpl();
    }

    @Override
    public void validateLogin(HashMap<String, String> map) {
        if (map.get("mobile_number").isEmpty() || map.get("mobile_number").length() != 13) {
            Common.showToast(activity, AppConstants.VALIDATE_MOBILE);
        } else {
            Log.d(TAG, "validateLogin: " + map.toString());
            view.onLoginValidateSuccess(map);
        }
    }

    @Override
    public void goLogin(HashMap<String, String> map) {
        view.showLoginProgress();
        if (Common.InternetCheck(activity)) {
            interacter.onLogin(activity, map, this);
        } else {
            view.hideLoginProgress();
            view.noNetLogin(AppConstants.INTERNET_CHECK);
        }
    }

    @Override
    public void onLoginSuccess(LoginPojo body) {
        view.hideLoginProgress();
        view.onLoginSuccess(body);
    }

    @Override
    public void onFailLogin(String error) {
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