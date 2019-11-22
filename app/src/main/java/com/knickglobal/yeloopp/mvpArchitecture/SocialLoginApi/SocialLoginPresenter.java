package com.knickglobal.yeloopp.mvpArchitecture.SocialLoginApi;

import com.knickglobal.yeloopp.models.LoginPojo;

import java.util.HashMap;

public interface SocialLoginPresenter {

    void goSocialLogin(HashMap<String, String> map);

    interface OnSuccess {

        void onSocialLoginSuccess(LoginPojo body);

        void onFailSocialLogin(String error);

        void onRetrofitFailed(String error);

    }

}
