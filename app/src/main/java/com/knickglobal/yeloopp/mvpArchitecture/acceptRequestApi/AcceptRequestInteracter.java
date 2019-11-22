package com.knickglobal.yeloopp.mvpArchitecture.acceptRequestApi;

import android.app.Activity;

import java.util.HashMap;

public interface AcceptRequestInteracter {

    void onAcceptRequest(Activity activity, HashMap<String, String> map,
                       AcceptRequestPresenter.OnSuccess onSuccess);

}
