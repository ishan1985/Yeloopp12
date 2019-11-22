package com.knickglobal.yeloopp.mvpArchitecture.nearByUsersApi;

import android.app.Activity;

import java.util.HashMap;

public interface NearbyUsersInteracter {

    void onNearbyUsers(Activity activity, HashMap<String, String> map,
                       NearbyUsersPresenter.OnSuccess onSuccess);

}
