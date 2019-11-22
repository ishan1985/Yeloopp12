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
import com.knickglobal.yeloopp.adapters.MyFollowingsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingsFragment extends Fragment {

    private Activity activity;

    private RecyclerView followingsRC;

    public FollowingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_followings, container, false);

        inits(view);

        return view;
    }

    private void inits(View view) {

        activity = getActivity();

        followingsRC = view.findViewById(R.id.followingsRC);
        followingsRC.setLayoutManager(new LinearLayoutManager(activity));
        followingsRC.setAdapter(new MyFollowingsAdapter(activity));

    }

}
