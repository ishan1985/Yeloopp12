package com.knickglobal.yeloopp.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.knickglobal.yeloopp.R;
import com.knickglobal.yeloopp.activities.EditProfileActivity;
import com.knickglobal.yeloopp.activities.FollowersAndFollowingsActivity;
import com.knickglobal.yeloopp.adapters.PostedImagesAdapter;
import com.knickglobal.yeloopp.utils.SpacesItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private Activity activity;
    private RecyclerView postedImagesRC;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        inits(view);

        return view;
    }

    private void inits(View view) {

        activity = getActivity();

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                activity.onBackPressed();
//            }
//        });

        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("Profile");

        ImageView imageAppbar = toolbar.findViewById(R.id.imageAppbar);
        imageAppbar.setVisibility(View.VISIBLE);
        imageAppbar.setImageResource(R.drawable.ic_settings);
        imageAppbar.setOnClickListener(this);

        Button editProfileBtn = view.findViewById(R.id.editProfileBtn);
        editProfileBtn.setOnClickListener(this);

        ImageView coverImageProfile = view.findViewById(R.id.coverImageProfile);
//        // display image according to width of devices
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int width = displayMetrics.widthPixels;
//        int dividedWidths = width - 450;
//        ViewGroup.LayoutParams layoutParams = coverImageProfile.getLayoutParams();
//        layoutParams.height = dividedWidths;
//        coverImageProfile.setLayoutParams(layoutParams);

        postedImagesRC = view.findViewById(R.id.postedImagesRC);
        postedImagesRC.setLayoutManager(new GridLayoutManager(activity, 3));
        postedImagesRC.addItemDecoration(new SpacesItemDecoration(3, 2, false));
        postedImagesRC.setAdapter(new PostedImagesAdapter(activity));

        LinearLayout myFollowerBtn = view.findViewById(R.id.myFollowerBtn);
        myFollowerBtn.setOnClickListener(this);

        LinearLayout myFollowingBtn = view.findViewById(R.id.myFollowingBtn);
        myFollowingBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageAppbar:

                break;

            case R.id.myFollowerBtn:
                openScreen("Followers");
                break;

            case R.id.myFollowingBtn:
                openScreen("Followings");
                break;

            case R.id.editProfileBtn:
                startActivity(new Intent(activity, EditProfileActivity.class));
                break;
        }
    }

    private void openScreen(String screenName) {
        startActivity(new Intent(activity, FollowersAndFollowingsActivity.class)
                .putExtra("screenName", screenName));
    }

}