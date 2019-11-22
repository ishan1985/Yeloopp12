package com.knickglobal.yeloopp.mvpArchitecture.acceptRequestApi;

import android.app.Activity;

import com.knickglobal.yeloopp.models.CommonPojo;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;
import com.knickglobal.yeloopp.utils.Common;

import java.util.HashMap;

public class AcceptRequestPresenterImpl implements AcceptRequestPresenter, AcceptRequestPresenter.OnSuccess {

    private Activity activity;
    private AcceptRequestView view;
    private AcceptRequestInteracter interacter;

    public AcceptRequestPresenterImpl(Activity activity, AcceptRequestView view) {
        this.activity = activity;
        this.view = view;
        interacter = new AcceptRequestInteracterImpl();
    }

    @Override
    public void goAcceptRequest(HashMap<String, String> map) {
        if (Common.InternetCheck(activity)) {
            view.showAcceptRequestProgress();
            interacter.onAcceptRequest(activity, map, this);
        } else {
            view.noNetAcceptRequest(AppConstants.INTERNET_CHECK);
        }
    }

    @Override
    public void onAcceptRequestSuccess(CommonPojo body) {
        view.hideAcceptRequestProgress();
        view.onAcceptRequestSuccess(body);
    }

    @Override
    public void onFailAcceptRequest(String error) {
        view.hideAcceptRequestProgress();
        view.onAcceptRequestError(error);
    }

    @Override
    public void onRetrofitFailed(String error) {
        view.hideAcceptRequestProgress();
        if (Common.InternetCheck(activity)) {
            view.onAcceptRequestError(error);
        } else {
            view.noNetAcceptRequest(AppConstants.INTERNET_CHECK);
        }
    }
}
