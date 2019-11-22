package com.knickglobal.yeloopp.mvpArchitecture.nearByUsersApi;

import com.knickglobal.yeloopp.models.NearbyUsersPojo;

import java.util.HashMap;

public interface NearbyUsersPresenter {

    void goNearbyUsers(HashMap<String, String> map);

    interface OnSuccess {

        void onNearbyUsersSuccess(NearbyUsersPojo body);

        void onFailNearbyUsers(String error);

        void onRetrofitFailedNearbyUsers(String error);

    }

}
