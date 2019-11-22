package com.knickglobal.yeloopp.mvpArchitecture.getFriendsApi;

import android.app.Activity;

import java.util.HashMap;

public interface GetFriendsInteracter {

    void getGetFriendsApi(Activity activity, HashMap<String, String> map,
                          GetFriendsPresenter.OnSuccess onSuccess);

}