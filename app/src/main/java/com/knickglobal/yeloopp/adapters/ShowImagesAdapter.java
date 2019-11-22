package com.knickglobal.yeloopp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.knickglobal.yeloopp.R;

import java.util.List;

public class ShowImagesAdapter extends PagerAdapter {

    private Activity activity;
    private List<String> imagesLists;

    public ShowImagesAdapter(Activity activity, List<String> imagesLists) {
        this.activity = activity;
        this.imagesLists = imagesLists;
    }

    @Override
    public int getCount() {
        return imagesLists.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_show_image, container, false);

        ImageView imageShow = view.findViewById(R.id.imageShow);

        Glide.with(activity).load("file://" + imagesLists.get(position))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageShow);

        container.addView(view);
        return view;
    }
}