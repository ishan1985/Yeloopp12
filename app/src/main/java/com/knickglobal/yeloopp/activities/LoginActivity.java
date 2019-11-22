package com.knickglobal.yeloopp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.hbb20.CountryCodePicker;
import com.knickglobal.yeloopp.R;
import com.knickglobal.yeloopp.models.LoginPojo;
import com.knickglobal.yeloopp.mvpArchitecture.SocialLoginApi.SocialLoginPresenter;
import com.knickglobal.yeloopp.mvpArchitecture.SocialLoginApi.SocialLoginPresenterImpl;
import com.knickglobal.yeloopp.mvpArchitecture.SocialLoginApi.SocialLoginView;
import com.knickglobal.yeloopp.mvpArchitecture.loginApi.LoginPresenter;
import com.knickglobal.yeloopp.mvpArchitecture.loginApi.LoginPresenterImpl;
import com.knickglobal.yeloopp.mvpArchitecture.loginApi.LoginView;
import com.knickglobal.yeloopp.sharedPreferences.App;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;
import com.knickglobal.yeloopp.utils.Common;
import com.knickglobal.yeloopp.utils.CustomProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginView,
        SocialLoginView, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, com.google.android.gms.location.LocationListener {

    private static final String TAG = "Beant LoginActivity";

    private int REQUEST_GPS_ENABLE = 101;
    private static final int settingPermissionCode = 104;
    private static final int LOCATION_PERMISSION = 100;

    public static final int RC_SIGN_IN = 1001;

    public static final int LOCATION_INTERVAL = 1000;
    public static final int FASTEST_LOCATION_INTERVAL = 1000;
    private LocationRequest mLocationRequest = new LocationRequest();
    private GoogleApiClient mLocationClient;


    private Activity activity;

    private EditText numberET;
    private CountryCodePicker countryCodeLogin;

    private String countryCodeS = "+91", deviceId = "", loginType = "";

    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;

    private CustomProgressDialog dialog;
    private LoginPresenter presenter;
    private SocialLoginPresenter socialPresenter;


    private String latitude = "", longitude = "";


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activity = LoginActivity.this;
        dialog = new CustomProgressDialog(activity);

        getDeviceToken();

        // @@@@@@@@@@@@@@@@@@@@@@ Check Permission Result @@@@@@@@@@@@@@@@@@@@@@
        getPermissions();


    }

    private void inits() {

        presenter = new LoginPresenterImpl(activity, this);
        socialPresenter = new SocialLoginPresenterImpl(activity, this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("Login");

        Button requestOtpBtn = findViewById(R.id.requestOtpBtn);
        requestOtpBtn.setOnClickListener(this);

        numberET = findViewById(R.id.numberET);

        countryCodeLogin = findViewById(R.id.ccp);
        countryCodeLogin.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCodeS = countryCodeLogin.getSelectedCountryCodeWithPlus();
            }
        });


        ImageButton button_google = findViewById(R.id.button_google);
        button_google.setOnClickListener(this);

        ImageButton button_facebook = findViewById(R.id.button_facebook);
        button_facebook.setOnClickListener(this);


        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());

        callbackManager = CallbackManager.Factory.create();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.requestOtpBtn:
                goLogin();
                break;

            case R.id.button_google:
                googleLogin();
                break;

            case R.id.button_facebook:
                facebookLogin();
                break;
        }
    }

    private void goLogin() {

        String number = countryCodeS + numberET.getText().toString();

//        String latitude = App.getAppPreference().getString(AppConstants.CURRENT_LAT);
//        String longitude = App.getAppPreference().getString(AppConstants.CURRENT_LONG);

        Toast.makeText(activity, latitude + "\n" + longitude, Toast.LENGTH_SHORT).show();

        HashMap<String, String> map = new HashMap<>();
        map.put("mobile_number", number);
        map.put("device_token", deviceId);
        map.put("latitude", latitude);
        map.put("longitude", longitude);

        App.getAppPreference().saveString(AppConstants.CURRENT_LAT, latitude);
        App.getAppPreference().saveString(AppConstants.CURRENT_LONG, longitude);

        presenter.validateLogin(map);

    }

    @Override
    public void showLoginProgress() {
        if (dialog != null)
            dialog.show();
    }

    @Override
    public void hideLoginProgress() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public void onLoginValidateSuccess(HashMap<String, String> map) {
        presenter.goLogin(map);
    }

    @Override
    public void onLoginSuccess(LoginPojo body) {

        App.getAppPreference().saveString(AppConstants.USER_ID, body.getDetails().getUid());
        if (body.getDetails().getStatus().equals("1")) {
            startActivity(new Intent(activity, VerifyNumberActivity.class)
                    .putExtra("number", body.getDetails().getMobile())
                    .putExtra("status", body.getDetails().getStatus()));
        } else {
            startActivity(new Intent(activity, VerifyNumberActivity.class)
                    .putExtra("number", body.getDetails().getMobile())
                    .putExtra("status", body.getDetails().getStatus()));
        }
//        if (body.getDetails().getStatus().equals("1")) {
//            startActivity(new Intent(activity, HomeActivity.class));
//        } else if (body.getDetails().getStatus().equals("0")) {
//        startActivity(new Intent(activity, VerifyNumberActivity.class)
//                .putExtra("number", body.getDetails().getMobile()));
//        }
    }

    @Override
    public void onLoginError(String error) {
        Common.showToast(activity, error);
    }

    @Override
    public void noNetLogin(String error) {
        Common.showToast(activity, error);
    }

    private void getDeviceToken() {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        deviceId = task.getResult().getToken();
                    }
                });

    }


    private void googleLogin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    public void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            String userName = account.getDisplayName();
            String userEmail = account.getEmail();
            String socialId = account.getId();
            loginType = "google";
            String userImage;
            if (account.getPhotoUrl() != null) {
                userImage = String.valueOf(account.getPhotoUrl());
            } else {
                userImage = "";
            }

            Log.d(TAG, "\n\n\n updateUI: userName : " + userName
                    + "\n userStringEmail : " + userEmail
                    + "\n socialId : " + socialId
                    + "\n userImage : " + userImage + "\n\n\n\n");

            socialLoginApi(userName, userEmail, socialId, loginType,
                    deviceId, userImage);

            mLocationClient.clearDefaultAccountAndReconnect();
        }

    }

    private void facebookLogin() {
        if (Common.InternetCheck(activity)) {
            if (dialog != null)
                dialog.show();
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    if (dialog.isShowing())
                        dialog.dismiss();
                    Log.d("onSuccess: ", loginResult.getAccessToken().getToken());
                    getFacebookData(loginResult);
                }

                @Override
                public void onCancel() {
                    if (dialog.isShowing())
                        dialog.dismiss();
                    Toast.makeText(activity, "Cancel", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException error) {
                    if (dialog.isShowing())
                        dialog.dismiss();
                    Log.d(TAG, error.toString());
                }
            });
        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }
    }

    private void getFacebookData(LoginResult loginResult) {

        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                String social_id = "", fbFirstName = "", fbLastName = "", userEmail = null, fbSocialUserName, fbProfilePicture;
                try {
                    if (object.has("id")) {
                        social_id = object.getString("id");
                    }
                    if (object.has("first_name")) {
                        fbFirstName = object.getString("first_name");
                    }
                    if (object.has("last_name")) {
                        fbLastName = object.getString("last_name");
                    }
                    if (object.has("email")) {
                        userEmail = object.getString("email");
                    }
                    fbSocialUserName = fbFirstName + " " + fbLastName;

                    fbProfilePicture = String.valueOf(new URL("https://graph.facebook.com/"
                            + social_id + "/picture?width=500&height=500"));

                    if (userEmail == null || userEmail.equalsIgnoreCase("")) {
                        Toast.makeText(activity, "Email not found in your facebook account",
                                Toast.LENGTH_SHORT).show();
                        facebookSignOut();
                    } else {
                        loginType = "facebook";
                        String userImage;
                        if (fbProfilePicture != null) {
                            userImage = fbProfilePicture;
                        } else {
                            userImage = "";
                        }

                        socialLoginApi(fbSocialUserName, userEmail, social_id, loginType,
                                deviceId, userImage);

                        Log.d(TAG, "\n\n\n updateUI: userName : " + fbSocialUserName
                                + "\n userStringEmail : " + userEmail
                                + "\n socialId : " + social_id
                                + "\n userImage : " + userImage + "\n\n\n\n");

                    }
                } catch (MalformedURLException e) {
                    e.getLocalizedMessage();
                } catch (JSONException e) {
                    e.getLocalizedMessage();
                }
            }

        });
        Bundle bundle = new Bundle();
        Log.e(TAG, "bundle set");
        bundle.putString("fields", "id, first_name, last_name,email,picture,gender,location");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }

    private void facebookSignOut() {
        LoginManager.getInstance().logOut();
    }


    // @@@@@@@@@@@@@@@@@@@@@@ Social Login Api @@@@@@@@@@@@@@@@@@@@@@
    private void socialLoginApi(String fbSocialUserName, String userEmail, String social_id,
                                String loginType, String deviceId, String userImage) {

        //social Login
        HashMap<String, String> map = new HashMap<>();
        map.put("name", fbSocialUserName);
        map.put("email", userEmail);
        map.put("social_id", social_id);
        map.put("login_type", loginType);
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        map.put("device_token", deviceId);
        map.put("image_url", userImage);

        App.getAppPreference().saveString(AppConstants.CURRENT_LAT, latitude);
        App.getAppPreference().saveString(AppConstants.CURRENT_LONG, longitude);

        socialPresenter.goSocialLogin(map);
    }

    @Override
    public void showSocialLoginProgress() {
        if (dialog != null)
            dialog.show();
    }

    @Override
    public void hideSocialLoginProgress() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public void onSocialLoginSuccess(LoginPojo body) {
        App.getAppPreference().saveString(AppConstants.USER_ID, body.getDetails().getUid());
        if (body.getDetails().getStatus().equals("1")) {
            App.getAppPreference().saveBoolean(AppConstants.IS_LOGIN, true);
            App.getAppPreference().saveString(AppConstants.LOGIN_TYPE, loginType);
            startActivity(new Intent(activity, HomeActivity.class)
                    .putExtra("number", body.getDetails().getMobile()));
        } else if (body.getDetails().getStatus().equals("0")) {
            startActivity(new Intent(activity, ChooseThreeActivity.class)
                    .putExtra("loginType", loginType));
        }
        finish();
    }

    @Override
    public void onSocialLoginError(String error) {
        Common.showToast(activity, error);
    }

    @Override
    public void noNetSocialLogin(String error) {
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
        } else if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
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
        }, 2000);

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
            LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient,
                    mLocationRequest, this);
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