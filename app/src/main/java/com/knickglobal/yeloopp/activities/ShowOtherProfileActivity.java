package com.knickglobal.yeloopp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.knickglobal.yeloopp.R;
import com.knickglobal.yeloopp.adapters.PostedImagesAdapter;
import com.knickglobal.yeloopp.adapters.UserPostedImagesAdapter;

public class ShowOtherProfileActivity extends AppCompatActivity {

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_other_profile);

        inits();

    }

    private void inits() {

        activity = ShowOtherProfileActivity.this;

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
        mTitle.setText("Profile");


        ImageView coverImageUserProfile=findViewById(R.id.coverImageUserProfile);

//        // display image according to width of devices
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int width = displayMetrics.widthPixels;
//        int dividedWidths = width - 450;
//        ViewGroup.LayoutParams layoutParams = coverImageUserProfile.getLayoutParams();
//        layoutParams.height = dividedWidths;
//        coverImageUserProfile.setLayoutParams(layoutParams);
//
        RecyclerView userPostedImagesRC=findViewById(R.id.userPostedImagesRC);
        userPostedImagesRC.setLayoutManager(new GridLayoutManager(activity,3));
        userPostedImagesRC.setAdapter(new UserPostedImagesAdapter(activity));

    }

}
