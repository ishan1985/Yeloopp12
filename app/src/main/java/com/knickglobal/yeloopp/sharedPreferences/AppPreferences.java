package com.knickglobal.yeloopp.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    private SharedPreferences sharedPreferences;
    private static AppPreferences appPreference;

    private AppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(AppConstants.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    public static AppPreferences init(Context context) {
        if (appPreference == null) {
            appPreference = new AppPreferences(context);
        }
        return appPreference;
    }

    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }
}