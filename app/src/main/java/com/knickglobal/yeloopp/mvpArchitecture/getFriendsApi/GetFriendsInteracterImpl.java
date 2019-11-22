package com.knickglobal.yeloopp.mvpArchitecture.getFriendsApi;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.knickglobal.yeloopp.models.FriendsListPojo;
import com.knickglobal.yeloopp.retrofit.ApiClient;
import com.knickglobal.yeloopp.retrofit.ApiInterface;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetFriendsInteracterImpl implements GetFriendsInteracter {

    private static final String TAG = "Beant FriendsItrImp";

    @Override
    public void getGetFriendsApi(Activity activity, HashMap<String, String> map,
                                 final GetFriendsPresenter.OnSuccess onSuccess) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<FriendsListPojo> call = apiInterface.getFriendsApi(map);

        Log.d(TAG, "getFollowRequestApi: " + call.request().url());

        call.enqueue(new Callback<FriendsListPojo>() {
            @Override
            public void onResponse(@NonNull Call<FriendsListPojo> call,
                                   @NonNull Response<FriendsListPojo> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("1")) {
                        onSuccess.onGetFriendsSuccess(response.body());
                    } else {
                        onSuccess.onFailGetFriends(response.body().getMessage());
                    }
                } else {
                    onSuccess.onFailGetFriends(AppConstants.SOMETHING_WENT_WRONG);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FriendsListPojo> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                onSuccess.onRetrofitFailedGetFriends(AppConstants.SOMETHING_WENT_WRONG);
            }
        });

    }

}
