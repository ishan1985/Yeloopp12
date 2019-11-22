package com.knickglobal.yeloopp.mvpArchitecture.logoutApi;

import android.app.Activity;

import java.util.HashMap;

public interface LogoutInteracter {

    void onLogout(Activity activity, HashMap<String, String> map,
                  LogoutPresenter.OnSuccess onSuccess);

}
