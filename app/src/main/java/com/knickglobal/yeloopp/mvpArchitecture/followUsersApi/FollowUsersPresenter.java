package com.knickglobal.yeloopp.mvpArchitecture.followUsersApi;

import com.knickglobal.yeloopp.models.FollowUsersPojo;

import java.util.HashMap;

public interface FollowUsersPresenter {

    void goFollowUsers(HashMap<String, String> map);

    interface OnSuccess {

        void onFollowUsersSuccess(FollowUsersPojo body);

        void onFailFollowUsers(String error);

        void onRetrofitFailed(String error);

    }

}
