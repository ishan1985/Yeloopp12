package com.knickglobal.yeloopp.mvpArchitecture.acceptRequestApi;

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

public class AcceptRequestInteracterImpl implements AcceptRequestInteracter {

    private static final String TAG = "Beant AcptReqItrImpl";

    @Override
    public void onAcceptRequest(Activity activity, HashMap<String, String> map,
                                final AcceptRequestPresenter.OnSuccess onSuccess) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<CommonPojo> call = apiInterface.acceptRequestsApi(map);

        Log.d(TAG, "onAcceptRequest: " + call.request().url());

        call.enqueue(new Callback<CommonPojo>() {
            @Override
            public void onResponse(@NonNull Call<CommonPojo> call, @NonNull Response<CommonPojo> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("1")) {
                        onSuccess.onAcceptRequestSuccess(response.body());
                    } else {
                        onSuccess.onFailAcceptRequest(response.body().getMessage());
                    }
                } else {
                    onSuccess.onFailAcceptRequest(AppConstants.SOMETHING_WENT_WRONG);
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