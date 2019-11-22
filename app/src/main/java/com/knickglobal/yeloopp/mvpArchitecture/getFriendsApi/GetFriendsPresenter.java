package com.knickglobal.yeloopp.mvpArchitecture.getFriendsApi;

import com.knickglobal.yeloopp.models.FriendsListPojo;

import java.util.HashMap;

public interface GetFriendsPresenter {

    void goGetFriends(HashMap<String, String> map);

    interface OnSuccess {

        void onGetFriendsSuccess(FriendsListPojo body);

        void onFailGetFriends(String error);

        void onRetrofitFailedGetFriends(String error);

    }
}
