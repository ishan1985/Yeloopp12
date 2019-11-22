package com.knickglobal.yeloopp.mvpArchitecture.followUsersApi;

import android.app.Activity;

import java.util.HashMap;

public interface FollowUsersInteracter {

    void onFollowUsers(Activity activity, HashMap<String, String> map,
                       FollowUsersPresenter.OnSuccess onSuccess);

}
