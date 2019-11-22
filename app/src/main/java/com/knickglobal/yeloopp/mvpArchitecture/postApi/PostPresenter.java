package com.knickglobal.yeloopp.mvpArchitecture.postApi;

import com.knickglobal.yeloopp.models.CommonPojo;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface PostPresenter {

    void goPost(HashMap<String, RequestBody> map, MultipartBody.Part text_bg_image,
                MultipartBody.Part[] post_images, MultipartBody.Part post_video);

    interface OnSuccess {

        void onPostSuccess(CommonPojo body);

        void onFailPost(String error);

        void onRetrofitFailedPost(String error);

    }

}
