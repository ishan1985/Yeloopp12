package com.knickglobal.yeloopp.mvpArchitecture.getFollowRequests;

import com.knickglobal.yeloopp.models.GetFollowRequestPojo;

import java.util.HashMap;

public interface GetFollowRequestPresenter {

    void goGetFollowRequest(HashMap<String, String> map);

    interface OnSuccess {

        void onGetFollowRequestSuccess(GetFollowRequestPojo body);

        void onFailGetFollowRequest(String error);

        void onRetrofitFailed(String error);

    }

}
