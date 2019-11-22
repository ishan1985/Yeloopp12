package com.knickglobal.yeloopp.mvpArchitecture.postApi;

import android.app.Activity;

import com.knickglobal.yeloopp.models.CommonPojo;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;
import com.knickglobal.yeloopp.utils.Common;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PostPresenterImpl implements PostPresenter, PostPresenter.OnSuccess {

    private Activity activity;
    private PostView view;
    private PostInteracter interacter;

    public PostPresenterImpl(Activity activity, PostView view) {
        this.activity = activity;
        this.view = view;
        interacter = new PostInteracterImpl();
    }

    @Override
    public void goPost(HashMap<String, RequestBody> map,
                       MultipartBody.Part text_bg_image,
                       MultipartBody.Part[] post_images,
                       MultipartBody.Part post_video) {

        if (Common.InternetCheck(activity)) {
            view.showPostProgress();
            interacter.onPost(activity, map, text_bg_image, post_images, post_video, this);
        } else {
            view.onPostError(AppConstants.INTERNET_CHECK);
        }

    }

    @Override
    public void onPostSuccess(CommonPojo body) {
        view.hidePostProgress();
        view.onPostSuccess(body);
    }

    @Override
    public void onFailPost(String error) {
        view.hidePostProgress();
        view.onPostError(error);
    }

    @Override
    public void onRetrofitFailedPost(String error) {
        view.hidePostProgress();
        if (Common.InternetCheck(activity)) {
            view.onPostError(error);
        } else {
            view.onPostError(AppConstants.INTERNET_CHECK);
        }
    }
}
