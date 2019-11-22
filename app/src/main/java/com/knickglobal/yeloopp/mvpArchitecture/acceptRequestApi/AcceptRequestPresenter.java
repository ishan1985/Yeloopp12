package com.knickglobal.yeloopp.mvpArchitecture.acceptRequestApi;

import com.knickglobal.yeloopp.models.CommonPojo;

import java.util.HashMap;

public interface AcceptRequestPresenter {

    void goAcceptRequest(HashMap<String, String> map);

    interface OnSuccess {

        void onAcceptRequestSuccess(CommonPojo body);

        void onFailAcceptRequest(String error);

        void onRetrofitFailed(String error);

    }

}
