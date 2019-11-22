package com.knickglobal.yeloopp.mvpArchitecture.SocialLoginApi;

import com.knickglobal.yeloopp.models.LoginPojo;

public interface SocialLoginView {

    void showSocialLoginProgress();

    void hideSocialLoginProgress();

    void onSocialLoginSuccess(LoginPojo body);

    void onSocialLoginError(String error);

    void noNetSocialLogin(String error);

}
