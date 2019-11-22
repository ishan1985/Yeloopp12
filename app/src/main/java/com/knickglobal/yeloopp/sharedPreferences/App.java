package com.knickglobal.yeloopp.sharedPreferences;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private Context context;

    private static AppPreferences appPreference;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        appPreference = AppPreferences.init(context);
    }

    public static AppPreferences getAppPreference() {
        return appPreference;
    }

}