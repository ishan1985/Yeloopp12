package com.knickglobal.yeloopp.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://knickglobal.co.in/yeloopp/";

    public static Retrofit retrofit = null;

    public static Retrofit getClient() {

//        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
//                .connectTimeout(20, TimeUnit.SECONDS)
//                .readTimeout(40, TimeUnit.SECONDS)
//                .writeTimeout(40, TimeUnit.SECONDS)
//                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
