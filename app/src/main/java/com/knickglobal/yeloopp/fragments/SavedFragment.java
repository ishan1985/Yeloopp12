package com.knickglobal.yeloopp.fragments;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knickglobal.yeloopp.R;
import com.knickglobal.yeloopp.adapters.SavedPostsAdapter;
import com.knickglobal.yeloopp.utils.SpacesItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class SavedFragment extends Fragment {

    private Activity activity;

    public SavedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved, container, false);

        inits(view);

        return view;
    }

    private void inits(View view) {

        activity = getActivity();

        RecyclerView savedPostRC = view.findViewById(R.id.savedPostRC);
        savedPostRC.setLayoutManager(new GridLayoutManager(activity,3));
        savedPostRC.setAdapter(new SavedPostsAdapter(activity));
        savedPostRC.addItemDecoration(new SpacesItemDecoration(3, 2, false));

    }

}
