package com.knickglobal.yeloopp.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;


public class Common {

    public static boolean InternetCheck(Activity activity) {

        ConnectivityManager connectivityMgr = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityMgr != null;
        NetworkInfo wifi = connectivityMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        // Check if wifi or mobile network is available or not. If any of them is
        // available or connected then it will return true, otherwise false;
        if (wifi != null) {
            if (wifi.isConnected()) {
                return true;
            }
        }
        if (mobile != null) {
            return mobile.isConnected();
        }
        return false;
    }

    public static void showToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    // @@@@@@@@@@@@@@ get device media @@@@@@@@@@@@@@
    public static ArrayList<String> getAllMedia(Activity activity, String[] projection, Uri uri
            , String data) {
        HashSet<String> videoItemHashSet = new HashSet<>();
        Cursor cursor = activity.getContentResolver().query(uri,
                projection, null, null, null);
        try {
            cursor.moveToFirst();
            do {
                videoItemHashSet.add((cursor.getString(cursor.
                        getColumnIndexOrThrow(data))));

            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(videoItemHashSet);
    }

}