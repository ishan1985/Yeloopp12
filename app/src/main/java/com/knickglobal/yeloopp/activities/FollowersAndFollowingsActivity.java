package com.knickglobal.yeloopp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.knickglobal.yeloopp.R;
import com.knickglobal.yeloopp.fragments.FollowersFragment;
import com.knickglobal.yeloopp.fragments.FollowingsFragment;

public class FollowersAndFollowingsActivity extends AppCompatActivity {

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers_and_followings);

        inits();

    }

    private void inits() {

        activity = FollowersAndFollowingsActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setVisibility(View.VISIBLE);


        // @@@@@@@@@@ get intent to open selected fragment @@@@@@@@@@
        Intent intent = getIntent();
        String screenName = intent.getStringExtra("screenName");

        if (screenName.equalsIgnoreCase("Followers")) {
            mTitle.setText(screenName);
            loadHomeFragment(new FollowersFragment());
        } else if (screenName.equalsIgnoreCase("Followings")) {
            mTitle.setText(screenName);
            loadHomeFragment(new FollowingsFragment());
        }

    }

    private void loadHomeFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.containerFollow, fragment);
        transaction.commit();
    }

}
