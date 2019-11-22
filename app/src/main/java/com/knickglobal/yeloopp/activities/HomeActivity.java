package com.knickglobal.yeloopp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.knickglobal.yeloopp.R;
import com.knickglobal.yeloopp.fragments.HomeFragment;
import com.knickglobal.yeloopp.fragments.LikeFragment;
import com.knickglobal.yeloopp.fragments.ProfileFragment;
import com.knickglobal.yeloopp.models.CommonPojo;
import com.knickglobal.yeloopp.mvpArchitecture.logoutApi.LogoutPresenter;
import com.knickglobal.yeloopp.mvpArchitecture.logoutApi.LogoutPresenterImpl;
import com.knickglobal.yeloopp.mvpArchitecture.logoutApi.LogoutView;
import com.knickglobal.yeloopp.sharedPreferences.App;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;
import com.knickglobal.yeloopp.utils.Common;
import com.knickglobal.yeloopp.utils.CustomProgressDialog;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements LogoutView {

    private Activity activity;

    private Menu menu;
    private BottomNavigationView navigation;

    private int newPostClick = 1, openedScreen = 1;

    private GoogleSignInClient mGoogleSignInClient;

    private CustomProgressDialog dialog;
    private LogoutPresenter logoutPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        inits();

    }

    private void inits() {

        activity = HomeActivity.this;

        dialog = new CustomProgressDialog(activity);
        logoutPresenter = new LogoutPresenterImpl(activity, this);

        navigation = findViewById(R.id.bottomView);
        navigation.setOnNavigationItemSelectedListener(navigationListener);
        navigation.setItemIconTintList(null);
        menu = navigation.getMenu();

        menu.findItem(R.id.homeIcon).setIcon(R.drawable.ic_home_selected);
        menu.findItem(R.id.searchIcon).setIcon(R.drawable.ic_logout);
        menu.findItem(R.id.addIcon).setIcon(R.drawable.ic_new_post_unselected);
        menu.findItem(R.id.likeIcon).setIcon(R.drawable.ic_likes_unselected);
        menu.findItem(R.id.userIcon).setIcon(R.drawable.ic_profile_unselected);

        //set home fragment
        loadHomeFragment(new HomeFragment());
        openedScreen = 1;

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.homeIcon:
                    if (openedScreen != 1) {
                        openedScreen = 1;
                        navigation.setItemIconTintList(null);
                        menu.findItem(R.id.homeIcon).setIcon(R.drawable.ic_home_selected);

                        menu.findItem(R.id.searchIcon).setIcon(R.drawable.ic_logout);
                        menu.findItem(R.id.addIcon).setIcon(R.drawable.ic_new_post_unselected);
                        menu.findItem(R.id.likeIcon).setIcon(R.drawable.ic_likes_unselected);
                        menu.findItem(R.id.userIcon).setIcon(R.drawable.ic_profile_unselected);

                        loadHomeFragment(new HomeFragment());
                    }
                    return true;
                case R.id.searchIcon:
                    if (openedScreen != 2) {
                        openedScreen = 2;
                        navigation.setItemIconTintList(null);
                        menu.findItem(R.id.searchIcon).setIcon(R.drawable.ic_logout);

                        menu.findItem(R.id.homeIcon).setIcon(R.drawable.ic_home_unselected);
                        menu.findItem(R.id.addIcon).setIcon(R.drawable.ic_new_post_unselected);
                        menu.findItem(R.id.likeIcon).setIcon(R.drawable.ic_likes_unselected);
                        menu.findItem(R.id.userIcon).setIcon(R.drawable.ic_profile_unselected);
                    }
                    logout();
                    return true;
                case R.id.addIcon:
                    newPost();
                    return true;
                case R.id.likeIcon:
                    if (openedScreen != 3) {
                        openedScreen = 3;
                        navigation.setItemIconTintList(null);
                        menu.findItem(R.id.likeIcon).setIcon(R.drawable.ic_likes_selected);

                        menu.findItem(R.id.homeIcon).setIcon(R.drawable.ic_home_unselected);
                        menu.findItem(R.id.searchIcon).setIcon(R.drawable.ic_logout);
                        menu.findItem(R.id.addIcon).setIcon(R.drawable.ic_new_post_unselected);
                        menu.findItem(R.id.userIcon).setIcon(R.drawable.ic_profile_unselected);

                        loadFragment(new LikeFragment());
                    }
                    return true;
                case R.id.userIcon:
                    if (openedScreen != 4) {
                        openedScreen = 4;
                        navigation.setItemIconTintList(null);
                        menu.findItem(R.id.userIcon).setIcon(R.drawable.ic_profile_selected);

                        menu.findItem(R.id.homeIcon).setIcon(R.drawable.ic_home_unselected);
                        menu.findItem(R.id.searchIcon).setIcon(R.drawable.ic_logout);
                        menu.findItem(R.id.addIcon).setIcon(R.drawable.ic_new_post_unselected);
                        menu.findItem(R.id.likeIcon).setIcon(R.drawable.ic_likes_unselected);

                        loadFragment(new ProfileFragment());
                    }
                    return true;
            }
            return false;
        }

    };

    @Override
    public void onBackPressed() {
        onBackPress();
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.containerHome, fragment);
        transaction.addToBackStack("home");
        transaction.commit();
    }

    private void loadHomeFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.containerHome, fragment);
        transaction.commit();
    }

    private void onBackPress() {

        new AlertDialog.Builder(activity)
                .setTitle("Exit!!")
                .setMessage("Are you sure you want to exit?")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        finishAffinity();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();

    }

    private void newPost() {

        if (newPostClick == 1) {
            newPostClick = 0;
            menu.findItem(R.id.addIcon).setIcon(R.drawable.ic_cross_button);

            menu.findItem(R.id.homeIcon).setIcon(null);
            menu.findItem(R.id.searchIcon).setIcon(null);
            menu.findItem(R.id.likeIcon).setIcon(null);
            menu.findItem(R.id.userIcon).setIcon(null);

            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_new_post);

            ImageButton textWithBgPostL = dialog.findViewById(R.id.textWithBgPostL);
            ImageButton imagePostL = dialog.findViewById(R.id.imagePostL);
            ImageButton videoPostL = dialog.findViewById(R.id.videoPostL);

            textWithBgPostL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
//                    Common.showToast(activity,"This will done later.");
                    startActivity(new Intent(activity, NewPostActivity.class)
                    .putExtra("textWithBg", "textWithBg"));
                }
            });

            imagePostL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    startActivity(new Intent(activity, DeviceImagesActivity.class));
                }
            });

            videoPostL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    startActivity(new Intent(activity, DeviceVideosActivity.class));
                }
            });

            WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

            wmlp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            wmlp.y = 100;
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    newPostClick = 1;
                    menu.findItem(R.id.addIcon).setIcon(R.drawable.ic_new_post_unselected);

                    menu.findItem(R.id.homeIcon).setIcon(R.drawable.ic_home_unselected);
                    menu.findItem(R.id.searchIcon).setIcon(R.drawable.ic_logout);
                    menu.findItem(R.id.likeIcon).setIcon(R.drawable.ic_likes_unselected);
                    menu.findItem(R.id.userIcon).setIcon(R.drawable.ic_profile_unselected);
                }
            });
        } else {
            newPostClick = 1;
            menu.findItem(R.id.addIcon).setIcon(R.drawable.ic_new_post_unselected);

            menu.findItem(R.id.homeIcon).setIcon(R.drawable.ic_home_unselected);
            menu.findItem(R.id.searchIcon).setIcon(R.drawable.ic_logout);
            menu.findItem(R.id.likeIcon).setIcon(R.drawable.ic_likes_unselected);
            menu.findItem(R.id.userIcon).setIcon(R.drawable.ic_profile_unselected);
        }

    }

    @Override
    public void showLogoutProgress() {
        if (dialog != null)
            dialog.show();
    }

    @Override
    public void hideLogoutProgress() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public void onLogoutSuccess(CommonPojo body) {
        Common.showToast(activity, body.getMessage());
        if (body.getStatus().equalsIgnoreCase("1"))
            checkLoginTypeToLogout();
    }

    @Override
    public void onLogoutError(String error) {
        Common.showToast(activity, error);
    }

    @Override
    public void noNetLogout(String error) {
        Common.showToast(activity, error);
    }

    private void logout() {

        new AlertDialog.Builder(activity)
                .setTitle("Logout!!")
                .setMessage("Are you sure you want to logout?")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation

                        // @@@@@@@@@@@@@@@@@@ Get Follow Requests Api @@@@@@@@@@@@@@@@@@
                        String id = App.getAppPreference().getString(AppConstants.USER_ID);

                        HashMap<String, String> map = new HashMap<>();
                        map.put("uid", id);
                        logoutPresenter.goLogout(map);
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();

    }

    private void checkLoginTypeToLogout() {

        String loginType = App.getAppPreference().getString(AppConstants.LOGIN_TYPE);

        if (loginType.equalsIgnoreCase("number")) {
            clearSharedPref();
        } else if (loginType.equalsIgnoreCase("facebook")) {
            LoginManager.getInstance().logOut();
            clearSharedPref();
        } else if (loginType.equalsIgnoreCase("google")) {
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            clearSharedPref();
                        }
                    })
                    .addOnFailureListener(activity, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Common.showToast(activity, AppConstants.SOMETHING_WENT_WRONG);
                        }
                    });
        }
    }

    private void clearSharedPref() {
        SharedPreferences preferences = getSharedPreferences(AppConstants.SHARED_PREFERENCES_KEY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        startActivity(new Intent(activity, LoginActivity.class));
        finishAffinity();
    }

}