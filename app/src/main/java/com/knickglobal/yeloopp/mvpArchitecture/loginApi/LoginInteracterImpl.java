package com.knickglobal.yeloopp.mvpArchitecture.loginApi;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.knickglobal.yeloopp.models.LoginPojo;
import com.knickglobal.yeloopp.retrofit.ApiClient;
import com.knickglobal.yeloopp.retrofit.ApiInterface;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginInteracterImpl implements LoginInteracter {

    private static final String TAG = "Beant LoginIntrImpl";

    @Override
    public void onLogin(Activity activity, HashMap<String, String> map,
                        final LoginPresenter.OnSuccess onSuccess) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<LoginPojo> call = apiInterface.loginApi(map);

        Log.d(TAG, "onLogin: " + call.request().url());

        call.enqueue(new Callback<LoginPojo>() {
            @Override
            public void onResponse(@NonNull Call<LoginPojo> call, @NonNull Response<LoginPojo> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("1")) {
                        onSuccess.onLoginSuccess(response.body());
                    } else {
                        onSuccess.onFailLogin(response.body().getMessage());
                    }
                } else {
                    onSuccess.onFailLogin(AppConstants.SOMETHING_WENT_WRONG);
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginPojo> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                onSuccess.onRetrofitFailed(AppConstants.SOMETHING_WENT_WRONG);
            }
        });

    }

}