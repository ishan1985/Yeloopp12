package com.knickglobal.yeloopp.mvpArchitecture.postApi;

import com.knickglobal.yeloopp.models.CommonPojo;

public interface PostView {

    void showPostProgress();

    void hidePostProgress();

    void onPostSuccess(CommonPojo body);

    void onPostError(String error);

    void noNetPost(String error);

}