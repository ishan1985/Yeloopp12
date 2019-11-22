package com.knickglobal.yeloopp.mvpArchitecture.logoutApi;

import com.knickglobal.yeloopp.models.CommonPojo;

import java.util.HashMap;

public interface LogoutPresenter {

    void goLogout(HashMap<String, String> map);

    interface OnSuccess {

        void onLogoutSuccess(CommonPojo body);

        void onFailLogout(String error);

        void onRetrofitFailed(String error);

    }

}
