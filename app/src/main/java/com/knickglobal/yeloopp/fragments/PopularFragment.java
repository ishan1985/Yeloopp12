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
import com.knickglobal.yeloopp.adapters.PopularPostAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class PopularFragment extends Fragment {

    private Activity activity;

    private RecyclerView popularPostsRC;

    public PopularFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_popular, container, false);

        inits(view);

        return view;
    }

    private void inits(View view) {

        activity = getActivity();

        popularPostsRC = view.findViewById(R.id.popularPostsRC);
        popularPostsRC.setLayoutManager(new LinearLayoutManager(activity));
        popularPostsRC.setAdapter(new PopularPostAdapter(activity));

    }

}
