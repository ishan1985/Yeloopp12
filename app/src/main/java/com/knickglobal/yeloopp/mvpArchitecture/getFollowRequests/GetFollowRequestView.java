package com.knickglobal.yeloopp.mvpArchitecture.getFollowRequests;

import com.knickglobal.yeloopp.models.GetFollowRequestPojo;

public interface GetFollowRequestView {

    void showLoginProgress();

    void hideLoginProgress();

    void onLoginSuccess(GetFollowRequestPojo body);

    void onLoginError(String error);

    void noNetLogin(String error);

}