package com.knickglobal.yeloopp.mvpArchitecture.logoutApi;

import com.knickglobal.yeloopp.models.CommonPojo;

public interface LogoutView {

    void showLogoutProgress();

    void hideLogoutProgress();

    void onLogoutSuccess(CommonPojo body);

    void onLogoutError(String error);

    void noNetLogout(String error);
}
