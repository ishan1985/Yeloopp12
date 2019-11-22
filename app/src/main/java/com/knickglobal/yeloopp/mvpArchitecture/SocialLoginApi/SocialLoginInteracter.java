package com.knickglobal.yeloopp.mvpArchitecture.SocialLoginApi;

import android.app.Activity;

import java.util.HashMap;

public interface SocialLoginInteracter {

    void onSocialLogin(Activity activity, HashMap<String, String> map,
                       SocialLoginPresenter.OnSuccess onSuccess);

}
