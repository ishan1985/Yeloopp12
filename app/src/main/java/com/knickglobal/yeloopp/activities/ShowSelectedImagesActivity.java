package com.knickglobal.yeloopp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.knickglobal.yeloopp.R;
import com.knickglobal.yeloopp.adapters.ShowImagesAdapter;

import java.util.List;

public class ShowSelectedImagesActivity extends AppCompatActivity {

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_selected_images);

        inits();

    }

    private void inits() {

        activity = ShowSelectedImagesActivity.this;
        ViewPager showImagesVP = findViewById(R.id.showImagesVP);


        // @@@@@@@@@@@@ get Intent @@@@@@@@@@@
        Intent intent = getIntent();
        List<String> imageList = intent.getStringArrayListExtra("imagesList");
        String position = intent.getStringExtra("position");

        showImagesVP.setAdapter(new ShowImagesAdapter(activity, imageList));

        showImagesVP.setCurrentItem(Integer.parseInt(position));
    }

}