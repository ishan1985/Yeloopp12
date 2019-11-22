package com.knickglobal.yeloopp.mvpArchitecture.getFollowRequests;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.knickglobal.yeloopp.models.GetFollowRequestPojo;
import com.knickglobal.yeloopp.retrofit.ApiClient;
import com.knickglobal.yeloopp.retrofit.ApiInterface;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetFollowRequestInteracterImpl implements GetFollowRequestInteracter {

    private static final String TAG = "Beant FollowReqItrImpl";

    @Override
    public void getFollowRequestApi(Activity activity, HashMap<String, String> map,
                                   final GetFollowRequestPresenter.OnSuccess onSuccess) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<GetFollowRequestPojo> call = apiInterface.getFollowRequestsApi(map);

        Log.d(TAG, "getFollowRequestApi: " + call.request().url());

        call.enqueue(new Callback<GetFollowRequestPojo>() {
            @Override
            public void onResponse(@NonNull Call<GetFollowRequestPojo> call,
                                   @NonNull Response<GetFollowRequestPojo> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("1")) {
                        onSuccess.onGetFollowRequestSuccess(response.body());
                    } else {
                        onSuccess.onFailGetFollowRequest(response.body().getMessage());
                    }
                } else {
                    onSuccess.onFailGetFollowRequest(AppConstants.SOMETHING_WENT_WRONG);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetFollowRequestPojo> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                onSuccess.onRetrofitFailed(AppConstants.SOMETHING_WENT_WRONG);
            }
        });
    }
}
