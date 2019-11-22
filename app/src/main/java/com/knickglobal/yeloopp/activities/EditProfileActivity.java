package com.knickglobal.yeloopp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.knickglobal.yeloopp.R;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity activity;

    private Button maleEditProfile, femaleEditProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        inits();

    }

    private void inits() {

        activity = EditProfileActivity.this;

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
        mTitle.setText("Edit Profile");

        maleEditProfile = findViewById(R.id.maleEditProfile);
        maleEditProfile.setOnClickListener(this);

        femaleEditProfile = findViewById(R.id.femaleEditProfile);
        femaleEditProfile.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.maleEditProfile:
                maleEditProfile.setBackground(getResources().getDrawable(R.drawable.button_bg_blue));
                femaleEditProfile.setBackground(getResources().getDrawable(R.drawable.stroke_black));
                maleEditProfile.setTextColor(getResources().getColor(R.color.whiteColor));
                femaleEditProfile.setTextColor(getResources().getColor(R.color.blackColor));
                break;

            case R.id.femaleEditProfile:
                maleEditProfile.setBackground(getResources().getDrawable(R.drawable.stroke_black));
                femaleEditProfile.setBackground(getResources().getDrawable(R.drawable.button_bg_blue));
                maleEditProfile.setTextColor(getResources().getColor(R.color.blackColor));
                femaleEditProfile.setTextColor(getResources().getColor(R.color.whiteColor));
                break;

        }
    }

}
