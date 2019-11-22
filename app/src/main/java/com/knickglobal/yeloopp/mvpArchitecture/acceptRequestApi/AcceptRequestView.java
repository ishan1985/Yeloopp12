package com.knickglobal.yeloopp.mvpArchitecture.acceptRequestApi;

import com.knickglobal.yeloopp.models.CommonPojo;

public interface AcceptRequestView {

    void showAcceptRequestProgress();

    void hideAcceptRequestProgress();

    void onAcceptRequestSuccess(CommonPojo body);

    void onAcceptRequestError(String error);

    void noNetAcceptRequest(String error);

}
