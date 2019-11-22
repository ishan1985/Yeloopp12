package com.knickglobal.yeloopp.fragments;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knickglobal.yeloopp.R;
import com.knickglobal.yeloopp.adapters.FollowingPostAdapter;
import com.knickglobal.yeloopp.adapters.NearByUserAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingPostsFragment extends Fragment {

    private Activity activity;

    private RecyclerView followingPostsRC, nearByRC;

    public FollowingPostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_following_posts, container, false);

        inits(view);

        return view;
    }

    private void inits(View view) {

        activity = getActivity();

        followingPostsRC = view.findViewById(R.id.followingPostsRC);
        followingPostsRC.setLayoutManager(new LinearLayoutManager(activity));
        followingPostsRC.setAdapter(new FollowingPostAdapter(activity));

        nearByRC = view.findViewById(R.id.nearByRC);
        nearByRC.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        nearByRC.setAdapter(new NearByUserAdapter(activity));

    }

}
