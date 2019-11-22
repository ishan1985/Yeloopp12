package com.knickglobal.yeloopp.fragments;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.knickglobal.yeloopp.R;
import com.knickglobal.yeloopp.adapters.HomeTabAdapter;
import com.knickglobal.yeloopp.adapters.LikeTabAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikeFragment extends Fragment {

    private Activity activity;

    private TabLayout likeTL;
    private ViewPager likeVP;

    public LikeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_like, container, false);

        inits(view);

        return view;
    }

    private void inits(View view) {

        activity = getActivity();

        likeTL = view.findViewById(R.id.likeTL);
        likeVP = view.findViewById(R.id.likeVP);

        likeVP.setAdapter(new LikeTabAdapter(getChildFragmentManager()));
        likeTL.setupWithViewPager(likeVP);

        //add separator line into tab
        LinearLayout linearLayout = (LinearLayout) likeTL.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.GRAY);
        drawable.setSize(1, 1);
        linearLayout.setDividerDrawable(drawable);

    }

}
