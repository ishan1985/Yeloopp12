package com.knickglobal.yeloopp.mvpArchitecture.getFollowRequests;

import android.app.Activity;

import java.util.HashMap;

public interface GetFollowRequestInteracter {

    void getFollowRequestApi(Activity activity, HashMap<String, String> map,
                                    GetFollowRequestPresenter.OnSuccess onSuccess);

}
