package com.knickglobal.yeloopp.mvpArchitecture.followUsersApi;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.knickglobal.yeloopp.models.FollowUsersPojo;
import com.knickglobal.yeloopp.retrofit.ApiClient;
import com.knickglobal.yeloopp.retrofit.ApiInterface;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowUsersInteracterImpl implements FollowUsersInteracter {

    private static final String TAG = " Beant FollowUsIntrIm";

    @Override
    public void onFollowUsers(Activity activity, HashMap<String, String> map,
                              final FollowUsersPresenter.OnSuccess onSuccess) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<FollowUsersPojo> call = apiInterface.followUsersApi(map);

        Log.d(TAG, "onFollowUsers: " + call.request().url());

        call.enqueue(new Callback<FollowUsersPojo>() {
            @Override
            public void onResponse(@NonNull Call<FollowUsersPojo> call, @NonNull Response<FollowUsersPojo> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("1")) {
                        onSuccess.onFollowUsersSuccess(response.body());
                    } else if (response.body().getStatus().equalsIgnoreCase("2")) {
                        onSuccess.onFollowUsersSuccess(response.body());
                    } else if (response.body().getStatus().equalsIgnoreCase("3")) {
                        onSuccess.onFollowUsersSuccess(response.body());
                    } else{
                        onSuccess.onFailFollowUsers(response.body().getMessage());
                    }
                } else {
                    onSuccess.onFailFollowUsers(AppConstants.SOMETHING_WENT_WRONG);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FollowUsersPojo> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                onSuccess.onRetrofitFailed(AppConstants.SOMETHING_WENT_WRONG);
            }
        });

    }

}
