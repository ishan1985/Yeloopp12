package com.knickglobal.yeloopp.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.knickglobal.yeloopp.fragments.NotificationFragment;
import com.knickglobal.yeloopp.fragments.SavedFragment;

public class LikeTabAdapter extends FragmentPagerAdapter {

    public LikeTabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();

        switch (position) {

            case 0:
                fragment = new NotificationFragment();
                break;

            case 1:
                fragment = new SavedFragment();
                break;

        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";

        switch (position) {

            case 0:
                title = "Notifications";
                break;

            case 1:
                title = "Saved";
                break;

        }

        return title;
    }

}
