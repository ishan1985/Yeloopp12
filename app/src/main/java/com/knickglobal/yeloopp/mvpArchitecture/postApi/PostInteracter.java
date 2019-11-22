package com.knickglobal.yeloopp.mvpArchitecture.postApi;

import android.app.Activity;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface PostInteracter {

    void onPost(Activity activity, HashMap<String, RequestBody> map, MultipartBody.Part text_bg_image,
                MultipartBody.Part[] post_images, MultipartBody.Part post_video,
                PostPresenter.OnSuccess onSuccess);
}
