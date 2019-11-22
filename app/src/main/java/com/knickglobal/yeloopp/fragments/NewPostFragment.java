package com.knickglobal.yeloopp.fragments;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knickglobal.yeloopp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewPostFragment extends Fragment {

    private Activity activity;

    public NewPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_post, container, false);

        inits(view);

        return view;
    }

    private void inits(View view) {

        activity = getActivity();



    }

}
