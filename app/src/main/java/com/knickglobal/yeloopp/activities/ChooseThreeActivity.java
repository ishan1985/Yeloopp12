package com.knickglobal.yeloopp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.knickglobal.yeloopp.R;
import com.knickglobal.yeloopp.adapters.UserSuggestAdapter;
import com.knickglobal.yeloopp.models.NearbyUsersPojo;
import com.knickglobal.yeloopp.mvpArchitecture.nearByUsersApi.NearbyUsersPresenter;
import com.knickglobal.yeloopp.mvpArchitecture.nearByUsersApi.NearbyUsersPresenterImpl;
import com.knickglobal.yeloopp.mvpArchitecture.nearByUsersApi.NearbyUsersView;
import com.knickglobal.yeloopp.sharedPreferences.App;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;
import com.knickglobal.yeloopp.utils.Common;
import com.knickglobal.yeloopp.utils.CustomProgressDialog;
import com.knickglobal.yeloopp.utils.LocationService;

import java.util.HashMap;

public class ChooseThreeActivity extends AppCompatActivity implements NearbyUsersView,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, com.google.android.gms.location.LocationListener, View.OnClickListener {

    private Activity activity;

    private int REQUEST_GPS_ENABLE = 101;
    private static final int settingPermissionCode = 104;
    private static final int LOCATION_PERMISSION = 100;

    public static final int LOCATION_INTERVAL = 1000;
    public static final int FASTEST_LOCATION_INTERVAL = 1000;
    private static final String TAG = LocationService.class.getSimpleName();
    private LocationRequest mLocationRequest = new LocationRequest();
    private GoogleApiClient mLocationClient;

    private RecyclerView userSuggestRC;
    private TextView nextChooseThree;

    private CustomProgressDialog dialog;
    private NearbyUsersPresenter presenter;

    private String latitude = "", longitude = "";

    private String loginType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_three);

        activity = ChooseThreeActivity.this;
        dialog = new CustomProgressDialog(activity);

        // @@@@@@@@@@@@@@@@@@ Check Permission Result @@@@@@@@@@@@@@@@@@
        getPermissions();

    }

    private void inits() {

        presenter = new NearbyUsersPresenterImpl(activity, this);

        SpannableStringBuilder builder = new SpannableStringBuilder();
        String red = "Follow minimum of";
        SpannableString redSpannable = new SpannableString(red);
        redSpannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.grayColor)), 0, red.length(), 0);
        builder.append(redSpannable);

        String white = " 3 User ";
        SpannableString whiteSpannable = new SpannableString(white);
        whiteSpannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blueColor)), 0, white.length(), 0);
        builder.append(whiteSpannable);

        String blue = "to start.";
        SpannableString blueSpannable = new SpannableString(blue);
        blueSpannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.grayColor)), 0, blue.length(), 0);
        builder.append(blueSpannable);

        TextView threeUserText = findViewById(R.id.threeUserText);
        threeUserText.setText(builder, TextView.BufferType.SPANNABLE);

        nextChooseThree = findViewById(R.id.nextChooseThree);
        nextChooseThree.setOnClickListener(this);

        userSuggestRC = findViewById(R.id.userSuggestRC);
        userSuggestRC.setLayoutManager(new GridLayoutManager(activity, 2));

        Intent intent = getIntent();
        if (intent != null) {
            loginType = intent.getStringExtra("loginType");
        }

        // @@@@@@@@@@@@@@@@@@@@@@  get near by users @@@@@@@@@@@@@@@@@@@@@@
        String id = App.getAppPreference().getString(AppConstants.USER_ID);

        if (latitude.isEmpty()) {
            latitude = App.getAppPreference().getString(AppConstants.CURRENT_LAT);
            longitude = App.getAppPreference().getString(AppConstants.CURRENT_LONG);
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", id);
        map.put("latitude", latitude);
        map.put("longitude", longitude);

        Toast.makeText(activity, latitude + "\n" + longitude, Toast.LENGTH_SHORT).show();

        presenter.goNearbyUsers(map);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nextChooseThree:
                goToDashboard();
                break;
        }
    }

    private void goToDashboard() {

        if (loginType.equalsIgnoreCase("mobile")) {
            App.getAppPreference().saveBoolean(AppConstants.IS_LOGIN, true);
            App.getAppPreference().saveString(AppConstants.LOGIN_TYPE, "number");
        } else if (loginType.equalsIgnoreCase("facebook")) {
            App.getAppPreference().saveBoolean(AppConstants.IS_LOGIN, true);
            App.getAppPreference().saveString(AppConstants.LOGIN_TYPE, "facebook");
        } else if (loginType.equalsIgnoreCase("google")) {
            App.getAppPreference().saveBoolean(AppConstants.IS_LOGIN, true);
            App.getAppPreference().saveString(AppConstants.LOGIN_TYPE, "google");
        }
        startActivity(new Intent(activity, HomeActivity.class));
        finishAffinity();

    }

    @Override
    public void showNearbyUsersProgress() {
        if (dialog != null)
            dialog.show();
    }

    @Override
    public void hideNearbyUsersProgress() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public void onNearbyUsersSuccess(NearbyUsersPojo body) {
        if (body.getDetails() != null) {
            userSuggestRC.setAdapter(new UserSuggestAdapter(activity, body.getDetails(),
                    new UserSuggestAdapter.FollowThreeListener() {
                        @Override
                        public void onThreeFollowed() {
                            nextChooseThree.setVisibility(View.VISIBLE);
                        }
                    }));
        } else
            Common.showToast(activity, "Unable To find Nearby users.");
    }

    @Override
    public void onNearbyUsersError(String error) {
        Common.showToast(activity, error);
    }

    @Override
    public void noNetNearbyUsers(String error) {
        Common.showToast(activity, error);
    }

    void getPermissions() {

        try {
            if (ActivityCompat.checkSelfPermission(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                            + Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION);
            } else if ((ActivityCompat.checkSelfPermission(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION +
                            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                if (checkGPS()) {
                    startLocation();
                } else {
                    //show gps enable dialog here
                    gps();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PERMISSION:
                boolean locationFine = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean locationCourse = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (locationFine && locationCourse) {
                    if (checkGPS()) {
                        startLocation();
                    } else {
                        //show gps enable dialog here
                        gps();
                    }
                } else if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Permissions");
                    builder.setMessage("Camera, External Storage And Location Permissions are Required");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //send to settings
                            Toast.makeText(activity, "Go to Settings to Grant the Storage Permissions and restart application", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, settingPermissionCode);
                        }
                    });
                    builder.create();
                    builder.show();
                } else {
                    Toast.makeText(activity, "Permission Denied", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.SYSTEM_ALERT_WINDOW}, 100);
                }
                break;
        }
    }

    private boolean checkGPS() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        assert locationManager != null;
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        } else {
            return false;
        }
    }

    private void gps() {

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(activity).checkLocationSettings(builder.build());
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    task.getResult(ApiException.class);
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                resolvable.startResolutionForResult(activity, REQUEST_GPS_ENABLE);
                            } catch (IntentSender.SendIntentException | ClassCastException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GPS_ENABLE) {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            assert locationManager != null;
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                startLocation();
            } else {
                finish();
            }
        } else if (requestCode == settingPermissionCode) {
            getPermissions();
        }
    }

    private void startLocation() {

        mLocationClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest.setInterval(LOCATION_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_LOCATION_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationClient.connect();

        if (dialog != null)
            dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog.isShowing())
                    dialog.dismiss();
                inits();
            }
        }, 3000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null)
            mLocationClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Log.d(TAG, "== Error On onConnected() Permission not granted");
            //Permission not granted by user so cancel the further execution.
            return;
        }

        if (mLocationClient.isConnected()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mLocationClient, mLocationRequest, this);
            Log.d(TAG, "Connected to Google API");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connection suspended");
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.v("location_service", "location != null");
            Log.v("location_service", location.getLatitude() +
                    "," + location.getLongitude());

            latitude = String.valueOf(location.getLatitude());
            longitude = String.valueOf(location.getLongitude());
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Failed to connect to Google API");
    }

}