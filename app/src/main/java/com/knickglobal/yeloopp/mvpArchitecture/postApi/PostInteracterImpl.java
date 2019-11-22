package com.knickglobal.yeloopp.mvpArchitecture.postApi;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.knickglobal.yeloopp.models.CommonPojo;
import com.knickglobal.yeloopp.retrofit.ApiClient;
import com.knickglobal.yeloopp.retrofit.ApiInterface;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostInteracterImpl implements PostInteracter {

    private static final String TAG = "Beant PostItrImpl";


    @Override
    public void onPost(Activity activity, HashMap<String, RequestBody> map,
                       MultipartBody.Part text_bg_image, MultipartBody.Part[] post_images,
                       MultipartBody.Part post_video, final PostPresenter.OnSuccess onSuccess) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<CommonPojo> call = apiInterface.postApi(map, text_bg_image, post_images, post_video);

        Log.d(TAG, "onPost: " + call.request().url());

        call.enqueue(new Callback<CommonPojo>() {
            @Override
            public void onResponse(@NonNull Call<CommonPojo> call, @NonNull Response<CommonPojo> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("1")) {
                        onSuccess.onPostSuccess(response.body());
                    } else {
                        onSuccess.onFailPost(response.body().getMessage());
                    }
                } else {
                    onSuccess.onFailPost(AppConstants.SOMETHING_WENT_WRONG);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonPojo> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                onSuccess.onRetrofitFailedPost(AppConstants.SOMETHING_WENT_WRONG);
            }
        });

    }
}