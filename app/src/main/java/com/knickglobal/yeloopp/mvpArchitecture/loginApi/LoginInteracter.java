package com.knickglobal.yeloopp.mvpArchitecture.loginApi;

import android.app.Activity;

import java.util.HashMap;

public interface LoginInteracter {

    void onLogin(Activity activity, HashMap<String, String> map,
                 LoginPresenter.OnSuccess onSuccess);
}
