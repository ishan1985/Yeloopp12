package com.knickglobal.yeloopp.mvpArchitecture.loginApi;

import com.knickglobal.yeloopp.models.LoginPojo;

import java.util.HashMap;

public interface LoginPresenter {

    void validateLogin(HashMap<String, String> map);

    void goLogin(HashMap<String, String> map);

    interface OnSuccess {

        void onLoginSuccess(LoginPojo body);

        void onFailLogin(String error);

        void onRetrofitFailed(String error);

    }

}
