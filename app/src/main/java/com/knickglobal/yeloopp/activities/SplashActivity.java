package com.knickglobal.yeloopp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.knickglobal.yeloopp.R;
import com.knickglobal.yeloopp.firebase.FirebaseMessaging;
import com.knickglobal.yeloopp.sharedPreferences.App;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity {

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        inits();

    }

    private void inits() {

        activity = SplashActivity.this;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isLogin = App.getAppPreference().getBoolean(AppConstants.IS_LOGIN);
                if (isLogin)
                    startActivity(new Intent(activity, HomeActivity.class));
                else
                    startActivity(new Intent(activity, LoginActivity.class));
                finish();
            }
        }, 2000);

    }

}