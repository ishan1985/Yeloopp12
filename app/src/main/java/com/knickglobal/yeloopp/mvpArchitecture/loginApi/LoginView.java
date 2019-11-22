package com.knickglobal.yeloopp.mvpArchitecture.loginApi;

import com.knickglobal.yeloopp.models.LoginPojo;

import java.util.HashMap;

public interface LoginView {

    void showLoginProgress();

    void hideLoginProgress();

    void onLoginValidateSuccess(HashMap<String, String> map);

    void onLoginSuccess(LoginPojo body);

    void onLoginError(String error);

    void noNetLogin(String error);

}