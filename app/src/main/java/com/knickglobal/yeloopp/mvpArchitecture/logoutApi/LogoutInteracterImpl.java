package com.knickglobal.yeloopp.mvpArchitecture.logoutApi;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.knickglobal.yeloopp.models.CommonPojo;
import com.knickglobal.yeloopp.retrofit.ApiClient;
import com.knickglobal.yeloopp.retrofit.ApiInterface;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogoutInteracterImpl implements LogoutInteracter {

    private static final String TAG = "Beant LogoutItrImpl";

    @Override
    public void onLogout(Activity activity, HashMap<String, String> map,
                         final LogoutPresenter.OnSuccess onSuccess) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<CommonPojo> call = apiInterface.logoutApi(map);

        Log.d(TAG, "onLogout: " + call.request().url());

        call.enqueue(new Callback<CommonPojo>() {
            @Override
            public void onResponse(@NonNull Call<CommonPojo> call, @NonNull Response<CommonPojo> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("1")) {
                        onSuccess.onLogoutSuccess(response.body());
                    } else {
                        onSuccess.onFailLogout(response.body().getMessage());
                    }
                } else {
                    onSuccess.onFailLogout(AppConstants.SOMETHING_WENT_WRONG);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonPojo> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                onSuccess.onRetrofitFailed(AppConstants.SOMETHING_WENT_WRONG);
            }
        });

    }

}
