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
import com.knickglobal.yeloopp.adapters.MyFollowersAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowersFragment extends Fragment {

    private Activity activity;

    private RecyclerView myFollowersRC;

    public FollowersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_followers, container, false);

        inits(view);

        return view;
    }

    private void inits(View view) {

        activity = getActivity();

        myFollowersRC = view.findViewById(R.id.myFollowersRC);
        myFollowersRC.setLayoutManager(new LinearLayoutManager(activity));
        myFollowersRC.setAdapter(new MyFollowersAdapter(activity));

    }

}
