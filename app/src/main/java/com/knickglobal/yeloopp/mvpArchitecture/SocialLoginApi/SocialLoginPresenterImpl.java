package com.knickglobal.yeloopp.mvpArchitecture.SocialLoginApi;

import android.app.Activity;

import com.knickglobal.yeloopp.models.LoginPojo;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;
import com.knickglobal.yeloopp.utils.Common;

import java.util.HashMap;

public class SocialLoginPresenterImpl implements SocialLoginPresenter, SocialLoginPresenter.OnSuccess {

    private static final String TAG = "Beant SocialLoginPresImpl";

    private Activity activity;
    private SocialLoginView view;
    private SocialLoginInteracter interacter;

    public SocialLoginPresenterImpl(Activity activity, SocialLoginView view) {
        this.activity = activity;
        this.view = view;
        this.interacter = new SocialLoginInteracterImpl();
    }

    @Override
    public void goSocialLogin(HashMap<String, String> map) {
        view.showSocialLoginProgress();
        if (Common.InternetCheck(activity)) {
            interacter.onSocialLogin(activity, map, this);
        } else {
            view.hideSocialLoginProgress();
            view.noNetSocialLogin(AppConstants.INTERNET_CHECK);
        }
    }

    @Override
    public void onSocialLoginSuccess(LoginPojo body) {
        view.hideSocialLoginProgress();
        view.onSocialLoginSuccess(body);
    }

    @Override
    public void onFailSocialLogin(String error) {
        view.hideSocialLoginProgress();
        view.onSocialLoginError(error);
    }

    @Override
    public void onRetrofitFailed(String error) {
        view.hideSocialLoginProgress();
        if (Common.InternetCheck(activity)) {
            view.onSocialLoginError(error);
        } else {
            view.noNetSocialLogin(AppConstants.INTERNET_CHECK);
        }
    }

}