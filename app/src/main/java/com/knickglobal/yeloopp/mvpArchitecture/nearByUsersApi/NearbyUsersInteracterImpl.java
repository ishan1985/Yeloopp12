package com.knickglobal.yeloopp.mvpArchitecture.nearByUsersApi;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.knickglobal.yeloopp.models.NearbyUsersPojo;
import com.knickglobal.yeloopp.retrofit.ApiClient;
import com.knickglobal.yeloopp.retrofit.ApiInterface;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearbyUsersInteracterImpl implements NearbyUsersInteracter {

    private static final String TAG = "Beant NearbyUsersIntIm";

    @Override
    public void onNearbyUsers(Activity activity, HashMap<String, String> map,
                              final NearbyUsersPresenter.OnSuccess onSuccess) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<NearbyUsersPojo> call = apiInterface.nearbyUsersApi(map);

        Log.d(TAG, "onNearbyUsers: " + call.request().url());

        call.enqueue(new Callback<NearbyUsersPojo>() {
            @Override
            public void onResponse(@NonNull Call<NearbyUsersPojo> call, @NonNull Response<NearbyUsersPojo> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("1")) {
                        onSuccess.onNearbyUsersSuccess(response.body());
                    } else {
                        onSuccess.onFailNearbyUsers(response.body().getMessage());
                    }
                } else {
                    onSuccess.onFailNearbyUsers(AppConstants.SOMETHING_WENT_WRONG);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NearbyUsersPojo> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                onSuccess.onRetrofitFailedNearbyUsers(AppConstants.SOMETHING_WENT_WRONG);
            }
        });
    }
}