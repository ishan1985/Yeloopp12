package com.knickglobal.yeloopp.mvpArchitecture.nearByUsersApi;

import com.knickglobal.yeloopp.models.NearbyUsersPojo;

public interface NearbyUsersView {

    void showNearbyUsersProgress();

    void hideNearbyUsersProgress();

    void onNearbyUsersSuccess(NearbyUsersPojo body);

    void onNearbyUsersError(String error);

    void noNetNearbyUsers(String error);

}
